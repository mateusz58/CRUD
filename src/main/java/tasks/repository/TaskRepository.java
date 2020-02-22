package tasks.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tasks.domain.TaskDto;

public interface TaskRepository extends JpaRepository<TaskDto, Long> {

}