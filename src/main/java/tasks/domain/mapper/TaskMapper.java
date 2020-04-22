package tasks.domain.mapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tasks.domain.entity.Task;
import tasks.domain.dto.TaskDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("TaskMapper")
public class TaskMapper implements DtoEntityMapper<Task, TaskDto> {

    @Override
    public Task toEntity(TaskDto object) {
        if (object == null) {
            return null;
        }
        Task objectToReturn = new Task();
        objectToReturn.setId(object.getId());
        objectToReturn.setContent(object.getContent());
        objectToReturn.setTitle(object.getTitle());
        return objectToReturn;
    }

    @Override
    public TaskDto toDto(Task object) {
        if (object == null) {
            return null;
        }
        TaskDto objectToReturn = new TaskDto();
        objectToReturn.setId(object.getId());
        objectToReturn.setContent(object.getContent());
        objectToReturn.setTitle(object.getTitle());
        return objectToReturn;
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
