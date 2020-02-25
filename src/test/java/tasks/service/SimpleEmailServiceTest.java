package tasks.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import tasks.domain.Mail;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

	@InjectMocks
	private SimpleEmailService simpleEmailService;

	@Mock
	private JavaMailSender javaMailSender;

	private Mail mail;

	private SimpleMailMessage simpleMailMessage;

	@BeforeEach
	void init() {
		mail = Mail.builder().mailTo("test@test")
				.message("message")
				.subject("subject")
				.toCc("CC")
				.build();
		simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(mail.getMailTo());
		simpleMailMessage.setText(mail.getMessage());
		simpleMailMessage.setCc(mail.getToCc());
		simpleMailMessage.setSubject(mail.getSubject());
	}

	@Test
	void send() {
		//when
		simpleEmailService.send(mail);

		//then
		verify(javaMailSender,times(1)).send(simpleMailMessage);
	}
}