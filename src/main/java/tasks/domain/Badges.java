package tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tasks.domain.dto.AttachmentsByType;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Builder
public final class Badges implements Serializable {

    @JsonProperty("votes")
    int votes;

    @JsonProperty("attachmentsByType")
    AttachmentsByType attachmentsByType;


    @Builder
    public Badges(int votes, AttachmentsByType attachmentsByType) {
        this.votes = votes;
        this.attachmentsByType = attachmentsByType;
    }
}
