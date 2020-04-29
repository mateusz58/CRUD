package tasks.service;

import org.springframework.stereotype.Service;
import tasks.domain.DTO;
import tasks.exception.BadRequestException;
import tasks.exception.NotFoundException;

import java.util.List;

@Service
public interface BasicCrudServiceInterface<Dto extends DTO> {

    List<Dto> getAll();

    Dto updateById(Dto object) throws BadRequestException;

    Dto getById(Long id) throws NotFoundException;

    Dto create(Dto object) throws BadRequestException;

    void deleteById(Long id) throws NotFoundException;

    boolean existsById(Long id);

    void deleteAll();
}
