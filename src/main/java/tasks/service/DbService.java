package tasks.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import tasks.domain.Task;
import tasks.domain.dto.TaskDto;
import tasks.domain.mapper.DtoEntityMapper;
import tasks.exception.BadRequestException;
import tasks.exception.NotFoundException;
import tasks.repository.TaskRepository;

@Service
public class DbService {

	private TaskRepository repository;

	private DtoEntityMapper dtoEntityMapper;

	public DbService(TaskRepository repository, @Qualifier("TaskMapper") DtoEntityMapper dtoEntityMapper) {
		this.repository = repository;
		this.dtoEntityMapper = dtoEntityMapper;
	}

	public TaskDto getTask(final Long id) throws NotFoundException {
		if(id == null) {
			throw new IllegalArgumentException("Passsed argument is equal null");
		}
		Task object = repository.findById(id).orElseThrow(() -> new NotFoundException(NotFoundException.notFoundMessage("Task")));
		return (TaskDto) dtoEntityMapper.toDto(object);
	}

	public Collection<TaskDto> getTasks() {
		return dtoEntityMapper.toDto(repository.findAll());
	}

	public void deleteTask(final Long id) throws NotFoundException {
		if(id == null) {
			throw new IllegalArgumentException("Passsed argument is equal null");
		}
		if(!repository.existsById(id)) {
					throw  new NotFoundException(NotFoundException.notFoundMessage("Task"));
		}
		repository.deleteById(id);
	}

	public TaskDto createTask(final Task task) throws BadRequestException {
		if(task == null) {
			throw new IllegalArgumentException("Passed argument is equal null");
		}
		if(repository.existsById(task.getId())) {
			throw new BadRequestException(BadRequestException.badRequestMessage(String.format("Task with Id %d already exists",task.getId())));
		}
		return (TaskDto) dtoEntityMapper.toDto(repository.save(task));
	}

	public TaskDto updateTask(final Task task) throws BadRequestException {
		if(task == null) {
			throw new IllegalArgumentException("Passed argument is equal null");
		}
		if(task.getId() == null) {
			throw new IllegalArgumentException("Passed argument is equal null");
		}
		if(!repository.existsById(task.getId())) {
			throw new BadRequestException(BadRequestException.badRequestMessage(String.format("Task with Id %d do not exists",task.getId())));
		}
		return (TaskDto) dtoEntityMapper.toDto(repository.save(task));
	}
}
