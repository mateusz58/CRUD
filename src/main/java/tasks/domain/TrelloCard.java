package tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@AllArgsConstructor
@Getter
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCard {
    private String name;
    private String description;
    private String pos;
    private String listId;
}