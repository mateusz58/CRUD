package tasks.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
	}

	@Test
	public void shouldSendDailyEmail() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		//Given
		EmailTemplateSelector template = EmailTemplateSelector.SCHEDULED_EMAIL;

		Class<?>[] params1 = new Class<?>[]{Mail.class, EmailTemplateSelector.class};
		Method cmm = simpleEmailService.getClass().getDeclaredMethod("createMimeMessage", params1);
		cmm.setAccessible(true);

		//when & then
		assertNotNull(cmm.invoke(simpleEmailService, mail, template).equals(null));
	}

	@Test
	public void shouldSendTrelloCardEmail() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		//Given
		Mail mail = new Mail("test@test.com", "cctest@test.com", "Test", "Test message");

		EmailTemplateSelector template = EmailTemplateSelector.TRELLO_CARD_EMAIL;

		Class<?>[] params1 = new Class<?>[]{Mail.class, EmailTemplateSelector.class};
		Method cmm = simpleEmailService.getClass().getDeclaredMethod("createMimeMessage", params1);
		cmm.setAccessible(true);

		//when & then
		assertNotNull(cmm.invoke(simpleEmailService, mail, template).equals(null));
	}
}
