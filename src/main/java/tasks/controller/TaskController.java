package tasks.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tasks.domain.TaskDto;
import tasks.service.DbService;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @Autowired
    DbService service;

    @GetMapping(value = "getTasks")
    public List<TaskDto> getTasks() {
        return new ArrayList<>(Arrays.asList(new TaskDto(1L,"title","content")));
    }

    @GetMapping(value = "getTask", params = {"id"})
    public TaskDto getTask(@RequestParam(value = "id") Long taskId)  {
        return new TaskDto(1L,"title","content");
    }

    @DeleteMapping(value = "deleteTask", params = {"id"})
    public void deleteTask(@RequestParam(value = "id") Long taskId) {
        service.deleteTask(taskId);
    }

    @PutMapping(value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return new TaskDto();
    }

    @PostMapping(value = "createTask", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto){
    }

}
