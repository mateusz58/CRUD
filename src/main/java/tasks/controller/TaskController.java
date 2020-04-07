package tasks.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasks.client.ClientRestTemplateHelper;
import tasks.domain.Task;
import tasks.service.DbService;

@RestController
@RequestMapping("api/v1/tasks")
@CrossOrigin(origins = "*")
@Slf4j
public class TaskController {

	DbService service;

	@Autowired
	public TaskController(DbService service) {
		this.service = service;
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

	@ApiOperation(value = "Get all tasks", notes = "Retrieving the collection of all tasks in database", response = Task[].class)
	@ApiResponses( {
			@ApiResponse(code = 200, message = "OK", response = Task[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> getTask(@PathVariable Long id) {
		try {
			if(id == null) {
				throw new IllegalArgumentException("Passed parameter is null");
			}
			return new ResponseEntity<>(service.getTask(id), HttpStatus.OK);
		} catch (Exception e) {
			log.error("An error occured while sending request", e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete by Id", notes = "Deletes task with specific Id")
	@ApiResponses( {
			@ApiResponse(code = 204, message = "Removed"),
			@ApiResponse(code = 404, message = "Task not found"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@DeleteMapping(params = {"id"})
	public ResponseEntity<?> deleteTask(@RequestParam(value = "id") Long id) {
		if(id == null) {
			throw new IllegalArgumentException("Passed parameter is null");
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "Get all tasks", notes = "Retrieving the collection of all invoices in database", response = Task[].class)
	@ApiResponses( {
			@ApiResponse(code = 200, message = "OK", response = Task[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@PutMapping("/{id}")
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
		if(task == null) {
			throw new IllegalArgumentException("Passed parameter is null");
		}
		try {
			service.createTask(task);
		} catch (Exception e) {
			log.error("An error occured during getting task {}", e.getCause());
		}
	}
}
