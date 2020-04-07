package tasks.domain.mapper;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DtoEntityMapper<Entity, Dto> {
    Entity toEntity(Dto object);

    Dto toDto(Entity object);

    List<Dto> toDto(List<Entity> objects);

    List<Entity> toEntity(List<Dto> objects);
}