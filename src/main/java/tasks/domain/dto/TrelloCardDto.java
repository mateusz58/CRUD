package tasks.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
//@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCardDto {
    private String name;
    private String description;
    private String pos;
    private String listId;
}