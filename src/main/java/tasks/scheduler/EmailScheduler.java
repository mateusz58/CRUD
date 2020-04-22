package tasks.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tasks.configuration.AdminConfig;
import tasks.domain.Mail;
import tasks.dao.TaskDao;
import tasks.service.EmailTemplateSelector;
import tasks.service.SimpleEmailService;

@Component
public class EmailScheduler {

	private SimpleEmailService simpleEmailService;

	private TaskDao taskRepository;

	private AdminConfig adminConfig;

	private static final String SUBJECT = "Tasks: Once a day email";

	public EmailScheduler(SimpleEmailService simpleEmailService, TaskDao taskRepository, AdminConfig adminConfig) {
		this.simpleEmailService = simpleEmailService;
		this.taskRepository = taskRepository;
		this.adminConfig = adminConfig;
	}

	@Scheduled(cron = "0 0 10 * * *")
	public void sendInformationEmail() {
		long size = taskRepository.count();
		String taskOrTasks = size == 1 ? " task" : " tasks";
		simpleEmailService.send(new Mail(
				adminConfig.getAdminMail(),
				SUBJECT,
				"Currently in database you've got: " + size + taskOrTasks
		), EmailTemplateSelector.SCHEDULED_EMAIL);
	}
}
