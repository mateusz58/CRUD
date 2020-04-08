package tasks.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import tasks.helper.ClientRestTemplateHelper;
import tasks.configuration.AdminConfig;
import tasks.configuration.TrelloConfig;
import tasks.domain.Mail;
import tasks.domain.dto.ResponseTrelloCartDto;
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

	public ResponseTrelloCartDto createTrelloCardAndSendEmailNotification(final TrelloCardDto trelloCardDto) {
		ResponseTrelloCartDto newCard = restTemplateHelper.postForEntity(ResponseTrelloCartDto.class, trelloConfig.urlCreateCard().toString(), trelloCardDto);
		Optional.ofNullable(newCard).ifPresent(card ->
				simpleEmailService.send(new Mail(
						adminConfig.getAdminMail(),
						SUBJECT,
						"New card: " + trelloCardDto.getName() + " has been created on your Trello account"
				), EmailTemplateSelector.TRELLO_CARD_EMAIL));
		return newCard;
	}
}
