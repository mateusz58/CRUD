package tasks.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tasks.domain.ENTITY;
import tasks.domain.TrelloList;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public  class TrelloBoard extends ENTITY<String> {

    @JsonProperty("name")
    private String name;

    @JsonProperty("lists")
    private List<TrelloList> lists;

    @Builder
    public TrelloBoard(String name, List<TrelloList> lists) {
        this.name = name;
        this.lists = lists;
    }

    @Builder
    public TrelloBoard(String id, String name, List<TrelloList> lists) {
        super(id);
        this.name = name;
        this.lists = lists;
    }
}
