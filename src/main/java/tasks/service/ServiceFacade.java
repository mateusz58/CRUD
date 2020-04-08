package tasks.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import tasks.domain.TrelloBoard;
import tasks.domain.TrelloCard;
import tasks.domain.dto.ResponseTrelloCartDto;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.dto.TrelloCardDto;
import tasks.domain.mapper.DtoEntityMapper;
import tasks.validator.TrelloValidator;

import java.util.List;

@Service
public class ServiceFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceFacade.class);

	private TrelloService trelloService;

	private TrelloValidator trelloValidator;

	private DtoEntityMapper mapperBoards;

	private DtoEntityMapper mapperCarts;

	public ServiceFacade(TrelloService trelloService, TrelloValidator trelloValidator, @Qualifier("BoardMapper") DtoEntityMapper mapperBoards, @Qualifier("CartMapper") DtoEntityMapper mapperCarts) {
		this.trelloService = trelloService;
		this.trelloValidator = trelloValidator;
		this.mapperBoards = mapperBoards;
		this.mapperCarts = mapperCarts;
	}

	public List<TrelloBoardDto> fetchAndValidateTrelloBoards() {
		List<TrelloBoard> trelloBoards = mapperBoards.toDto(trelloService.fetchTrelloBoards());
		List<TrelloBoard> filteredBoards = trelloValidator.validateTrelloBoards(trelloBoards);
		return mapperBoards.toDto(filteredBoards);
	}

	public ResponseTrelloCartDto postCartCreate(final TrelloCardDto trelloCardDto) {
		TrelloCard trelloCard = (TrelloCard) mapperCarts.toEntity(trelloCardDto);
		trelloValidator.validateCard(trelloCard);
		return trelloService.createTrelloCardAndSendEmailNotification((TrelloCardDto) mapperCarts.toDto(trelloCard));
	}
}
