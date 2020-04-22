package tasks.service;

import org.springframework.stereotype.Service;
import tasks.domain.entity.Task;
import tasks.domain.dto.TaskDto;
import tasks.domain.mapper.TaskMapper;
import tasks.dao.TaskDao;

@Service
public class DbService extends GenericServiceImpl<TaskDto, Task>  {

	private TaskDao repository;

	private TaskMapper mapper;

	public DbService(TaskDao repository, TaskMapper mapper) {
		super(repository, mapper);
	}
}
