package tasks.dao;

import org.springframework.stereotype.Repository;
import tasks.domain.entity.Task;

@Repository
public interface TaskDao extends BaseDao<Task> {
}