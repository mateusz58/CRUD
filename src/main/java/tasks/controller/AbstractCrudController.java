package tasks.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tasks.domain.DTO;
import tasks.exception.BadRequestException;
import tasks.exception.NotFoundException;
import tasks.service.BasicCrudServiceInterface;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractCrudController<Dto extends DTO> {

    protected static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    BasicCrudServiceInterface<Dto> service;

    public AbstractCrudController(BasicCrudServiceInterface service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll() {
        log.debug(String.format("Fetching all %s entites from database", getGenericName()));
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Validated @RequestBody Dto entity) throws BadRequestException {
        if (entity == null) {
            String message = String.format("Request for %s  is null", entity.getClass());
            log.error(message);
            throw new IllegalArgumentException(String.format(message, entity.getClass()));
        }
        log.debug(String.format("Creating %s entity in database", getGenericName()));
        return new ResponseEntity(service.create(entity), HttpStatus.CREATED);
    }

    @PutMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateById(@PathVariable(value = "id") Long id,@Validated @RequestBody Dto entity) throws BadRequestException {
        if (id == null) {
            String message = String.format("Request id for %s  is null", entity.getClass());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        if (entity == null) {
            String message = String.format("Request object for %s  is null", entity.getClass());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        if (entity.getId() != id && entity.getId()!=null) {
            String message = String.format("Request to change object entity %s with not matching passed id to path", entity.getClass());
            log.error(message);
            throw new BadRequestException(message);
        }
        log.debug("Updating entity %s in database with id %d", getGenericName(), id);
        return new ResponseEntity(service.updateById(entity), HttpStatus.OK);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable(value = "id") Long id) throws NotFoundException {
        if (id == null) {
            String message = String.format("Request to delete entity %s with null id", getGenericName());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        log.debug("Updating entity %s in database", getGenericName());
        service.deleteById(id);
    }

    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) throws NotFoundException {
        if (id == null) {
            String message = String.format("Attempt to get %s entity with null id", getGenericName());
            log.error(message);
            throw new IllegalArgumentException(message);
        }
        log.debug("Fetching entity %s in database with id", getGenericName(), id);
        return new ResponseEntity(service.getById(id), HttpStatus.OK);
    }

    protected String getGenericName() {
        return ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0].getTypeName().replaceAll(".*\\.", "").replaceAll("Dto", "");
    }
}
