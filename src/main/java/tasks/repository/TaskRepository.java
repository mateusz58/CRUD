package tasks.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import tasks.domain.Task;

public interface TaskRepository extends CrudRepository<Task, Long> {

	@Override
	List<Task> findAll();

	@Override
	Task save(Task task);

	Optional<Task> findById(Long id);

	@Override
	long count();
}