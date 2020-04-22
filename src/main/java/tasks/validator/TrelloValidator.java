package tasks.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.TrelloCard;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrelloValidator {
    private final static Logger log = LoggerFactory.getLogger(TrelloValidator.class);

    public void validateCard(final TrelloCard trelloCard) {
        if(trelloCard.getName().contains("test")) {
            log.info("Someone is testing my application");
        } else {
            log.info("Seems that my application is used in proper way.");
        }
    }

    public List<TrelloBoardDto> validateTrelloBoards(final List<TrelloBoardDto> trelloBoards) {
        log.info("Starting filtering boards...");
        List<TrelloBoardDto> filteredBoards = trelloBoards.stream()
                .filter(trelloBoard -> !trelloBoard.getName().equalsIgnoreCase("test"))
                .collect(Collectors.toList());
        log.info("Boards have been filtered. Current list size: " + filteredBoards.size());
        return filteredBoards;
    }
}
