package tasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import tasks.domain.Task;
import tasks.domain.dto.TaskDto;
import tasks.domain.mapper.DtoEntityMapper;
import tasks.service.DbService;
import static org.hamcrest.Matchers.is;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DbService dbService;

    @Autowired
    ObjectMapper mapper;

    String originEndPoint = "/api/v1/tasks";

    @Test
    public void getTasksMethodShouldReturnObjectCollection() throws Exception {
        //Given
        List<TaskDto> listDto = Collections.singletonList(TaskDto.builder().id(1L).content("contentDto").title("titleDto").build());
        when(dbService.getTasks()).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get(originEndPoint)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapper.writeValueAsString(listDto)));
    }

    @Test
    public void getTaskMethodShouldReturnObjectById() throws Exception {
        //Given
        String endPoint = String.format("%s/%d",originEndPoint,1L);
        TaskDto listDto = TaskDto.builder().id(1L).content("contentDto").title("titleDto").build();
        when(dbService.getTask(listDto.getId())).thenReturn(listDto);

        //When & Then
        mockMvc.perform(get(endPoint)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapper.writeValueAsString(listDto)));
    }

    @Test
    public void updateTaskMethodShouldReturnUpdatedObject() throws Exception{
        //Given
        String endPoint = String.format("%s/%d",originEndPoint,1L);
        Task object = new Task(1L, "title", "content");
        TaskDto objectDto = new TaskDto(1L, "title1", "content1");
        when(dbService.updateTask(object)).thenReturn(objectDto);
        String jsonContent = mapper.writeValueAsString(object);

        //When & Then
        mockMvc.perform(put(endPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapper.writeValueAsString(objectDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void createTaskMethodShouldCreateAndReturnObject() throws Exception {
        //Given
        Task object = new Task(1L, "title", "content");
        TaskDto objectDto = new TaskDto(1L, "title", "content");
        when(dbService.createTask(object)).thenReturn(objectDto);
        String jsonContent = mapper.writeValueAsString(objectDto);

        //When & Then
        mockMvc.perform(post(originEndPoint)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(mapper.writeValueAsString(objectDto)))
                .andExpect(status().isCreated());
    }
}
