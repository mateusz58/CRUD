package tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Badges implements Serializable {

	int votes;
	AttachmentsByType attachmentsByType;
	TrelloDto trelloDto;
}
