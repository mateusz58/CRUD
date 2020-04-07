package tasks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import tasks.client.ClientRestTemplateHelper;
import tasks.configuration.AdminConfig;
import tasks.configuration.TrelloConfig;
import tasks.domain.Mail;
import tasks.domain.dto.CreatedTrelloCardDto;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.dto.TrelloCardDto;


@Service
public class TrelloService {

	private static final String SUBJECT = "Task: Once a day email";

	AdminConfig adminConfig;
	ClientRestTemplateHelper restTemplateHelper;
	SimpleEmailService simpleEmailService;
	TrelloConfig trelloConfig;

	public TrelloService(ClientRestTemplateHelper restTemplateHelper, SimpleEmailService simpleEmailService, AdminConfig adminConfig, TrelloConfig trelloConfig) {
		this.restTemplateHelper = restTemplateHelper;
		this.simpleEmailService = simpleEmailService;
		this.adminConfig = adminConfig;
		this.trelloConfig = trelloConfig;
	}

	public List<TrelloBoardDto> fetchTrelloBoards() {
		return restTemplateHelper.getForList(TrelloBoardDto.class,trelloConfig.urlGetBoards().toString());
	}

	public CreatedTrelloCardDto createCartEntityAndNotifyViaEmail(final TrelloCardDto trelloCardDto) {
		CreatedTrelloCardDto createdDto = restTemplateHelper.postForEntity(CreatedTrelloCardDto.class, trelloConfig.urlCreateCard().toString(), trelloCardDto);
		Optional.ofNullable(createdDto).ifPresent(card -> simpleEmailService.send(
				Mail.builder()
						.mailTo(adminConfig.getAdminMail())
						.subject(SUBJECT)
						.message("New card" + createdDto.getName() + "has been created on Trello")
						.build()
		));
		return createdDto;
	}
}
