package tasks.service;

import java.util.Collection;
import java.util.Optional;
import org.springframework.stereotype.Service;
import tasks.domain.Task;
import tasks.repository.TaskRepository;

@Service
public class DbService {

	private TaskRepository repository;

	public DbService(TaskRepository repository) {
		this.repository = repository;
	}

	public Optional<Task> getTask(final Long id) {
		return repository.findById(id);
	}

	public Collection<Task> getTasks() {
		return repository.findAll();
	}

	public void deleteTask(final Long id) {
		repository.deleteById(id);
	}

	public void createTask(final Task task) {
		repository.save(task);
	}
}
