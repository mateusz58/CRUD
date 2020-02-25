package tasks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tasks.client.TrelloClient;
import tasks.domain.TaskDto;
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

	@GetMapping
	public ResponseEntity<?> getTasks() {
		return new ResponseEntity<>(service.getTasks(), HttpStatus.OK);
	}

	@DeleteMapping(params = {"id"})
	public ResponseEntity<?> deleteTask(@RequestParam(value = "id") Long taskId) {
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public TaskDto updateTask(@RequestBody TaskDto taskDto) {
		return new TaskDto();
	}

	@PostMapping
	public void createTask(@RequestBody TaskDto taskDto){
		service.createTask(taskDto);
	}

}