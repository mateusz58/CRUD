package tasks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tasks.domain.Mail;

import java.util.Optional;

@Service
public class SimpleEmailService {

	private static final Logger log = LoggerFactory.getLogger(SimpleMailMessage.class);

	private JavaMailSender javaMailSender;

	private MailCreatorService mailCreatorService;

	public SimpleEmailService(JavaMailSender javaMailSender, MailCreatorService mailCreatorService) {
		this.javaMailSender = javaMailSender;
		this.mailCreatorService = mailCreatorService;
	}

	@Async
	public void send(final Mail mail, EmailTemplateSelector template) {
		log.info("Starting email preparation...");
		try {
			javaMailSender.send(createMimeMessage(mail, template));
			log.info("Email has been sent.");
		} catch (MailException e) {
			log.error("Failed to process email sending: ", e.getMessage(), e);
		}
	}

	private MimeMessagePreparator createMimeMessage(final Mail mail, EmailTemplateSelector template) {
		return mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setTo(mail.getMailTo());
			messageHelper.setSubject(mail.getSubject());
			messageHelper.setText(getMailHtmlTextForTemplateSelector(mail.getMessage(), template), true);
		};
	}

	private String getMailHtmlTextForTemplateSelector(String message, EmailTemplateSelector template) {
		if (template == EmailTemplateSelector.SCHEDULED_EMAIL) {
			return mailCreatorService.buildScheduledEmail(message);
		} else if (template == EmailTemplateSelector.TRELLO_CARD_EMAIL) {
			return mailCreatorService.buildTrelloCardEmail(message);
		}
		return "";
	}

	private SimpleMailMessage createMailMessage(final Mail mail) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(mail.getMailTo());
		Optional.ofNullable(mail.getToCc()).ifPresent(mailMessage::setCc);
		mailMessage.setSubject(mail.getSubject());
		mailMessage.setText(mail.getMessage());
		return mailMessage;
	}
}
