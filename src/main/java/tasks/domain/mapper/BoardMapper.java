package tasks.domain.mapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.entity.TrelloBoard;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("BoardMapper")
public class BoardMapper implements DtoEntityMapper<TrelloBoard, TrelloBoardDto> {


    @Override
    public TrelloBoard toEntity(TrelloBoardDto object) {
        if (object == null) {
            return null;
        }
        TrelloBoard objectToReturn = new TrelloBoard();
        objectToReturn.setId(object.getId());
        objectToReturn.setName(object.getName());
        if(object.getLists() == null) {
            objectToReturn.setLists(null);
        }
        else {
            objectToReturn.setLists(object.getLists());
        }
        return objectToReturn;
    }

    @Override
    public TrelloBoardDto toDto(TrelloBoard object) {
        if (object == null) {
            return null;
        }
        TrelloBoardDto objectToReturn = new TrelloBoardDto();
        objectToReturn.setId(object.getId());
        objectToReturn.setName(object.getName());
        if(object.getLists() == null) {
            objectToReturn.setLists(null);
        }
        else {
            objectToReturn.setLists(object.getLists());
        }
        return objectToReturn;
    }

    @Override
    public List<TrelloBoardDto> toDto(List<TrelloBoard> objects) {
        return objects.stream().map(s -> toDto(s)).collect(Collectors.toList());
    }

    @Override
    public List<TrelloBoard> toEntity(List<TrelloBoardDto> objects) {
        return objects.stream().map(s -> toEntity(s)).collect(Collectors.toList());
    }
}
