package tasks.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public final class Mail {

	@NonNull
	private String mailTo;

	@NonNull
	private String subject;

	@NonNull
	private String message;

	private String toCc;
}
