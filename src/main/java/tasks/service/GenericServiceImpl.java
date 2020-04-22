package tasks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tasks.dao.BaseDao;
import tasks.domain.DTO;
import tasks.domain.ENTITY;
import tasks.domain.mapper.DtoEntityMapper;
import tasks.exception.BadRequestException;
import tasks.exception.NotFoundException;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;


@Service
public abstract class GenericServiceImpl<Dto extends DTO<Long>, Entity extends ENTITY<Long>> implements BasicCrudServiceInterface<Dto> {

    protected static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected BaseDao<Entity> repository;

    protected DtoEntityMapper<Entity, Dto> mapper;

    public GenericServiceImpl(BaseDao<Entity> repository, DtoEntityMapper<Entity, Dto> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Dto> getAll() {
        log.debug(String.format("Fetching all %s entities from database", getGenericClassName()));
        return mapper.toDto(repository.findAll());
    }

    @Override
    @Transactional(readOnly = false)
    public Dto updateById(Dto object) throws BadRequestException {
        if (object == null) {
            String message = String.format("Passed argument for %s object type equals null", getGenericClassName());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        if (object.getId() == null) {
            String message = String.format("Attempt to update to update %s object type with null id", getGenericClassName());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        if (existsById(object.getId())) {
            log.debug(String.format("Updating %s entity from database with id %d", getGenericClassName(), object.getId()));
            return mapper.toDto(repository.save(mapper.toEntity(object)));
        }
        String message = String.format("Passed argument for %s object type equals null", getGenericClassName());
        throw new BadRequestException(BadRequestException.messageEntityExists(getGenericClassName(), object.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public Dto getById(Long id) throws NotFoundException {
        if (id == null) {
            String message = String.format("Passed id %d for %s object type equals null", id, getGenericClassName());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        Optional<Entity> dto = repository.findById(id);
        if (!dto.isPresent()) {
            String message = NotFoundException.message(getGenericClassName(), id);
            log.error(message);
            throw new NotFoundException(message);
        }
        log.debug(String.format("Fetching %s entity with id %d", getGenericClassName(), id));
        return mapper.toDto(dto.get());
    }

    @Override
    @Transactional(readOnly = false)
    public Dto create(Dto object) throws BadRequestException {
        if (object == null) {
            String message = String.format("Passed argument for %s object type equals null", getGenericClassName());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        Optional<Entity> dto = repository.findById(object.getId());
        if (dto.isPresent()) {
            String message = BadRequestException.messageEntityExists(getGenericClassName(), object.getId());
            log.error(message);
            throw new BadRequestException(BadRequestException.messageEntityExists(getGenericClassName(), object.getId()));
        }
        log.debug(String.format("Creating %s entity with id %d", getGenericClassName(), object.getId()));
        return mapper.toDto(repository.save(mapper.toEntity(object)));
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteById(Long id) throws NotFoundException {
        if (id == null) {
            String message = String.format("Passed id %d for %s object type equals null", id, getGenericClassName());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        Optional<Entity> dto = repository.findById(id);
        if (!dto.isPresent()) {
            throw new NotFoundException(NotFoundException.message(getGenericClassName(), id));
        }
        log.debug(String.format("Deleting %s entity with id %d", getGenericClassName(), id));
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        if (!repository.findById(id).isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAll() {
        log.debug(String.format("Deleting all %s entities from database", getGenericClassName()));
        repository.deleteAll();
    }

    protected String getGenericClassName() {
        return ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0].getTypeName().replaceAll(".*\\.", "").replaceAll("Dto", "");
    }
}
