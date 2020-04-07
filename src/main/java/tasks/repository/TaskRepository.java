package tasks.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tasks.domain.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	@Override
	List<Task> findAll();

	@Override
	Task save(Task task);

	@Override
	Optional<Task> findById(Long id);

	@Override
	long count();

	@Override
	boolean existsById(Long aLong);
}