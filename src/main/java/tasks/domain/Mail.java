package tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Mail {

	private String mailTo;
	private String subject;
	private String message;
	private String toCc;
}
