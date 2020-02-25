package tasks.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tasks.client.TrelloClient;
import tasks.domain.Task;
import tasks.service.DbService;

@RestController
@RequestMapping("api/v1/tasks")
public class TaskController {


	DbService service;
	TrelloClient trelloClient;

	public TaskController(DbService service, TrelloClient trelloClient) {
		this.service = service;
		this.trelloClient = trelloClient;
	}

	@ApiOperation(value = "Get all tasks", notes = "Retrieving the collection of all tasks in database", response = Task[].class)
	@ApiResponses( {
			@ApiResponse(code = 200, message = "OK", response = Task[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping
	public ResponseEntity<?> getTasks() {
		return new ResponseEntity<>(service.getTasks(), HttpStatus.OK);
	}


	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete by Id", notes = "Deletes task with specific Id")
	@ApiResponses( {
			@ApiResponse(code = 204, message = "Removed"),
			@ApiResponse(code = 404, message = "Task not found"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@DeleteMapping(params = {"id"})
	public ResponseEntity<?> deleteTask(@RequestParam(value = "id") Long taskId) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Get all tasks", notes = "Retrieving the collection of all invoices in database", response = Task[].class)
	@ApiResponses( {
			@ApiResponse(code = 200, message = "OK", response = Task[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@PutMapping
	public Task updateTask(@RequestBody Task task) {
		return new Task();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Add new task", notes = "Add new task to database", response = Task.class)
	@ApiResponses( {
			@ApiResponse(code = 201, message = "Created", response = Task[].class),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 406, message = "Not acceptable format"),
			@ApiResponse(code = 409, message = "Task exists"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping
	public void createTask(@RequestBody Task task) {
		service.createTask(task);
	}
}