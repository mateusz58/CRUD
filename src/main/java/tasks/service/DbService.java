package tasks.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;
import tasks.domain.TaskDto;
import tasks.repository.TaskRepository;

@Service
public class DbService {

	private TaskRepository repository;

	public DbService(TaskRepository repository) {
		this.repository = repository;
	}

	public Optional<TaskDto> getTask(final Long id) {
		return repository.findById(id);
	}

	public Collection<TaskDto> getTasks() {return repository.findAll();}

	public void deleteTask(final Long id) {
		repository.deleteById(id);
	}

	public void createTask(final TaskDto taskDto) {
		repository.save(taskDto);
	}
}
