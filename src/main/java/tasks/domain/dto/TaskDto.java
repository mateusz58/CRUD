package tasks.domain.dto;

import lombok.*;
import tasks.domain.DTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class TaskDto extends DTO<Long> {

    private String title;

    private String content;

    @Builder
    public TaskDto(Long id, String title, String content) {
        super(id);
        this.title = title;
        this.content = content;
    }
}