package tasks.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasks.domain.TaskDto;
import tasks.repository.TaskRepository;

@Service
public class DbService {

	@Autowired
	private TaskRepository repository;

	public Optional<TaskDto> getTask(final Long id) {
		return repository.findById(id);
	}

	public void deleteTask(final Long id) {
		repository.deleteById(id);
	}
}
