package tasks.domain.mapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tasks.domain.Task;
import tasks.domain.dto.TaskDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("TaskMapper")
public class TaskMapper implements DtoEntityMapper<Task, TaskDto> {

    @Override
    public Task toEntity(TaskDto object) {
        return new Task(
                object.getId(),
                object.getTitle(),
                object.getContent());
    }

    @Override
    public TaskDto toDto(Task object) {
        return new TaskDto(
                object.getId(),
                object.getTitle(),
                object.getContent());
    }

    @Override
    public List<TaskDto> toDto(List<Task> objects) {
        return objects.stream().map(s ->
                toDto(s)).collect(Collectors.toList());
    }

    @Override
    public List<Task> toEntity(List<TaskDto> objects) {
        return objects.stream().map(s ->
                toEntity(s)).collect(Collectors.toList());
    }
}
