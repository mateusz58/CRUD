package tasks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tasks.domain.Mail;

@Service
public class SimpleEmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

	private JavaMailSender javaMailSender;

	public SimpleEmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Async
	public void send(final Mail mail) {
		LOGGER.info("Starting email preparation...");
		try {
			SimpleMailMessage mailMessage = createMailMessage(mail);
			javaMailSender.send(mailMessage);
			LOGGER.info("Mail has been sent.");
		}catch (MailException e) {
			LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
		}
	}

	private SimpleMailMessage createMailMessage(final Mail mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mail.getMailTo());
		mailMessage.setSubject((mail.getSubject()));
		mailMessage.setText(mail.getMessage());
		if(mail.getToCc()!= null) {
			mailMessage.setCc(mail.getToCc());
		}
		return mailMessage;
	}
}
