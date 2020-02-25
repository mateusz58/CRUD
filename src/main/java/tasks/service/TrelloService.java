package tasks.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import tasks.client.TrelloClient;
import tasks.configuration.AdminConfig;
import tasks.domain.CreatedTrelloCardDto;
import tasks.domain.Mail;
import tasks.domain.TrelloBoardDto;
import tasks.domain.TrelloCardDto;

@Service
public class TrelloService {

	private static final String SUBJECT = "Task: Once a day email";

	AdminConfig adminConfig;
	TrelloClient trelloClient;
	SimpleEmailService simpleEmailService;

	public TrelloService(TrelloClient trelloClient, SimpleEmailService simpleEmailService, AdminConfig adminConfig) {
		this.trelloClient = trelloClient;
		this.simpleEmailService = simpleEmailService;
		this.adminConfig = adminConfig;
	}

	public List<TrelloBoardDto> getTrelloBoards() {
		return trelloClient.getTrelloBoards();
	}

	//5e1e385f96e74908d66183cb

	public CreatedTrelloCardDto createTrelloCard(TrelloCardDto cardDto) {
		CreatedTrelloCardDto createdTrelloCardDto = trelloClient.createNewCard(cardDto);
		Optional.ofNullable(createdTrelloCardDto).ifPresent(card -> simpleEmailService.send(
				Mail.builder()
						.mailTo(adminConfig.getAdminMail())
						.subject(SUBJECT)
						.message("New card" + card.getName() + "has been created on Trello")
						.build()
		));
		return trelloClient.createNewCard(cardDto);
	}
}
