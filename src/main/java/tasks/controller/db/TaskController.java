package tasks.controller.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tasks.controller.AbstractCrudController;
import tasks.domain.dto.TaskDto;
import tasks.service.DbService;

@RestController
@RequestMapping("api/v1/tasks")
@CrossOrigin(origins = "*")
@Slf4j
public class TaskController extends AbstractCrudController<TaskDto> {

	public TaskController(DbService service) {
		super(service);
	}
}
