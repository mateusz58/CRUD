package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @GetMapping(value = "getTasks")
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @GetMapping(value = "getTask", params = {"id"})
    public TaskDto getTask(@RequestParam(value = "id") Long taskId)  {
        return new TaskDto();
    }

    @DeleteMapping(value = "deleteTask", params = {"id"})
    public void deleteTask(@RequestParam(value = "id") Long taskId) {

    }

    @PutMapping(value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return new TaskDto();
    }

    @PostMapping(value = "createTask", consumes = APPLICATION_JSON_VALUE)
    public void createTask(@RequestBody TaskDto taskDto){
    }

}