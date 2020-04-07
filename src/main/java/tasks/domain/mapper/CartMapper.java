package tasks.domain.mapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tasks.domain.TrelloCard;
import tasks.domain.dto.TrelloCardDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("CartMapper")
public class CartMapper implements DtoEntityMapper<TrelloCard, TrelloCardDto> {

    @Override
    public TrelloCard toEntity(TrelloCardDto object) {
        return new TrelloCard(object.getName(), object.getDescription(), object.getPos(),
                object.getListId());
    }

    @Override
    public TrelloCardDto toDto(TrelloCard object) {
        return new TrelloCardDto(object.getName(), object.getDescription(), object.getPos(),
                object.getListId());
    }

    @Override
    public List<TrelloCardDto> toDto(List<TrelloCard> objects) {
        return objects.stream().map(o -> toDto(o)).collect(Collectors.toList());
    }

    @Override
    public List<TrelloCard> toEntity(List<TrelloCardDto> objects) {
        return objects.stream().map(o -> toEntity(o)).collect(Collectors.toList());
    }
}
