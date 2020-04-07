package tasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tasks.domain.Task;
import tasks.domain.dto.TaskDto;
import tasks.domain.mapper.DtoEntityMapper;
import tasks.domain.mapper.TaskMapper;
import tasks.service.DbService;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DbService dbService;

    DtoEntityMapper taskMapper;

    @Autowired
    ObjectMapper objectMapper;

    String endPoint = "/api/v1/tasks";

    @Test
    public void shouldGetTasks() throws Exception {
        //Given
        List<Task> taskList = new ArrayList<>();
        Task task = new Task(1L, "Test task", "Test description");
        taskList.add(task);
        List<TaskDto> taskDtoList = new ArrayList<>();
        TaskDto taskDto = new TaskDto(1L, "Test Dto Task", "Test DTO description");
        taskDtoList.add(taskDto);
        when(dbService.getTasks()).thenReturn(taskList);

        //When & Then
        mockMvc.perform(get(endPoint)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Test Dto Task")))
                .andExpect(jsonPath("$[0].content", is("Test DTO description")));
    }
}
//    @Test
//    public void shouldGetTask() throws Exception{
//        //Given
//        Task task = new Task(1L, "Test task", "Test description");
//        TaskDto taskDto = new TaskDto(1L, "Test Dto Task", "Test DTO description");
//        when(taskMapper.toDto(task)).thenReturn(taskDto);
//        when(dbService.getTask(task.getId())).thenReturn(Optional.ofNullable(task));
//
//        //When & Then
//        mockMvc.perform(get("/v1/task/getTask?id=1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.title", is("Test Dto Task")))
//                .andExpect(jsonPath("$.content", is("Test DTO description")));
//    }
//    @Test
//    public void shouldUpdateTask() throws Exception{
//        //Given
//        Task task = new Task(1L, "Test task", "Test description");
//        TaskDto taskDto = new TaskDto(1L, "Test Dto Task", "Test DTO description");
//        when(taskMapper.toDto(task)).thenReturn(taskDto);
//        when(taskMapper.toEntity(Matchers.any(TaskDto.class))).thenReturn(task);
//        when(dbService.createTask(task)).thenReturn(task);
//        String jsonContent = objectMapper.writeValueAsString(task);
//
//        //When & Then
//        mockMvc.perform(put("/v1/task/updateTask")
//                .contentType(MediaType.APPLICATION_JSON)
//                .characterEncoding("UTF-8")
//                .content(jsonContent))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.title", is("Test Dto Task")))
//                .andExpect(jsonPath("$.content", is("Test DTO description")));
//    }
//    @Test
//    public void shouldCreateTask() throws Exception{
//        //Given
//        Task task = new Task(1L, "Test task", "Test description");
//        TaskDto taskDto = new TaskDto(1L, "Test Dto Task", "Test DTO description");
//        when(taskMapper.toDto(task)).thenReturn(taskDto);
//        when(taskMapper.toEntity(Matchers.any(TaskDto.class))).thenReturn(task);
//        when(dbService.createTask(task)).thenReturn(task);
//        String jsonContent = objectMapper.writeValueAsString(task);
//
//        //When & Then
//        mockMvc.perform(post("/v1/task/createTask")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(jsonContent))
//                .andExpect(status().isOk());
//    }
//}
////