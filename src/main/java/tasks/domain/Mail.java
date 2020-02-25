package tasks.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mail {

	private String mailTo;
	private String subject;
	private String message;
	private String toCc;
}
