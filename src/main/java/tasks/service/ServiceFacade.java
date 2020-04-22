package tasks.service;

import org.springframework.stereotype.Service;
import tasks.configuration.AdminConfig;
import tasks.domain.Mail;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.TrelloCard;
import tasks.domain.dto.CreatedTrelloCartDto;
import tasks.domain.dto.TrelloCardDto;
import tasks.domain.mapper.CartMapper;
import tasks.exception.BadRequestException;
import tasks.validator.TrelloValidator;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceFacade {

    private TrelloServiceClient trelloClientService;

    private TrelloValidator trelloValidator;

    private CartMapper mapperCarts;

    private  SimpleEmailService simpleEmailService;

    private AdminConfig adminConfig;


    public ServiceFacade(TrelloServiceClient trelloClientService, TrelloValidator trelloValidator, CartMapper mapperCarts, SimpleEmailService simpleEmailService, AdminConfig adminConfig) {
        this.trelloClientService = trelloClientService;
        this.trelloValidator = trelloValidator;
        this.mapperCarts = mapperCarts;
        this.simpleEmailService = simpleEmailService;
        this.adminConfig = adminConfig;
    }

    private void sendEmail(CreatedTrelloCartDto object, String subject, String email) {
        Optional.ofNullable(object).ifPresent(card ->
                simpleEmailService.send(new Mail(
                        email,
                        subject,
                        "New card: " + object.getName() + " has been created on your Trello account"
                ), EmailTemplateSelector.TRELLO_CARD_EMAIL));
    }

    public List<TrelloBoardDto> fetchAndValidateTrelloBoards() {
        return trelloValidator.validateTrelloBoards(trelloClientService.fetchAll());
    }

    public CreatedTrelloCartDto performCartCreationWithEmailNotification(final TrelloCardDto trelloCardDto) throws BadRequestException {
        TrelloCard trelloCard = mapperCarts.toEntity(trelloCardDto);
        trelloValidator.validateCard(trelloCard);
        CreatedTrelloCartDto response = trelloClientService.createCard(mapperCarts.toDto(trelloCard));
        sendEmail(response, "Trello task", adminConfig.getAdminMail());
        return response;
    }
}
