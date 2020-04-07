package tasks.domain.mapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import tasks.domain.TrelloBoard;
import tasks.domain.TrelloList;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.dto.TrelloListDto;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
@Qualifier("BoardMapper")
public class BoardMapper implements DtoEntityMapper<TrelloBoard, TrelloBoardDto> {

    @Override
    public TrelloBoard toEntity(TrelloBoardDto object) {
                    return new TrelloBoard(
                                    object.getId()
                                    , object.getName()
                                    , mapToList(object.getLists()));
    }

    @Override
    public TrelloBoardDto toDto(TrelloBoard object) {
        return new TrelloBoardDto(
                object.getId()
                , object.getName()
                , mapToListDto(object.getLists()));
    }

    @Override
    public List<TrelloBoardDto> toDto(List<TrelloBoard> objects) {
        return objects.stream()
                .map(o -> toDto(o)).collect(Collectors.toList());
    }

    @Override
    public List<TrelloBoard> toEntity(List<TrelloBoardDto> objects) {
        return objects.stream()
                .map(o -> toEntity(o)).collect(Collectors.toList());
    }


    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListDto) {
        return trelloListDto.stream()
                .map(trelloList -> new TrelloList(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(toList());
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloLists) {
        return trelloLists.stream()
                .map(trelloList ->
                        new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(toList());
    }
}
