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

    public TrelloCardDto toDto(final TrelloCard trelloCard){
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(),
                trelloCard.getPos(), trelloCard.getListId());
    }

    public TrelloCard toEntity(final TrelloCardDto trelloCardDto){
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(),
                trelloCardDto.getPos(), trelloCardDto.getListId());
    }

    @Override
    public List<TrelloCardDto> toDto(List<TrelloCard> objects) {
        if(objects == null) {
            return null;
        }
        return objects.stream().map(o -> toDto(o)).collect(Collectors.toList());
    }

    @Override
    public List<TrelloCard> toEntity(List<TrelloCardDto> objects) {
        if(objects == null) {
            return null;
        }
        return objects.stream().map(o -> toEntity(o)).collect(Collectors.toList());
    }
}
