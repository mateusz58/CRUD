package tasks.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tasks.configuration.AdminConfig;
import tasks.domain.Mail;
import tasks.repository.TaskRepository;
import tasks.service.SimpleEmailService;

@Component
public class EmailScheduler {

	private static final String SUBJECT = "Task: Once a day email";
	private SimpleEmailService simpleEmailService;
	private AdminConfig adminConfig;
	private TaskRepository taskRepository;

	public EmailScheduler(SimpleEmailService simpleEmailService, AdminConfig adminConfig, TaskRepository taskRepository) {
		this.simpleEmailService = simpleEmailService;
		this.adminConfig = adminConfig;
		this.taskRepository = taskRepository;
	}

	@Scheduled(cron = "0 0 10 * * *")
	public void sendInformationEmail() {
		long size = taskRepository.count();
		if (size == 1) {
			simpleEmailService.send(Mail.builder()
					.mailTo(adminConfig.getAdminMail())
					.subject(SUBJECT)
					.message("Currently in database you got 1 task")
					.build());
		} else {
			simpleEmailService.send(Mail.builder()
					.mailTo(adminConfig.getAdminMail())
					.subject(SUBJECT)
					.message(String.format("Currently in database you got %d tasks", "%d"))
					.build());
		}
	}
}
