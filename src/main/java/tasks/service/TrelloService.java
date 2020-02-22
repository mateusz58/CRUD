package tasks.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tasks.client.TrelloClient;
import tasks.domain.CreatedTrelloCardDto;
import tasks.domain.TrelloBoardDto;
import tasks.domain.TrelloCardDto;

@Service
public class TrelloService {

	@Autowired
	TrelloClient trelloClient;

	public List<TrelloBoardDto> getTrelloBoards() {
		return trelloClient.getTrelloBoards();
	}

	//5e1e385f96e74908d66183cb

	public CreatedTrelloCardDto createTrelloCard(TrelloCardDto cardDto) {
		return trelloClient.createNewCard(cardDto);
	}
}
