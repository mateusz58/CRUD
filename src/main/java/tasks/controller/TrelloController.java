package tasks.controller;

import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tasks.domain.TrelloBoardDto;
import tasks.domain.TrelloCardDto;
import tasks.service.TrelloService;

@RestController
@RequestMapping("api/v1/")
public class TrelloController {

	TrelloService service;

	public TrelloController(TrelloService trelloService) {
		this.service = trelloService;
	}

	@GetMapping(value = "getTrelloBoards")
	public ResponseEntity<?> getBoards() {
		return new ResponseEntity<>(service.getTrelloBoards(), HttpStatus.OK);
	}

	@GetMapping(value = "getTrelloBoards/kodilla")
	public ResponseEntity<?> getBoardKodilla() {
		Optional<TrelloBoardDto> trelloBoardDto = service.getTrelloBoards().stream().filter(s -> s.getName().contains("Kodilla")).findFirst();
		if (!trelloBoardDto.isPresent()) {
			return new ResponseEntity<>(trelloBoardDto.get(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(trelloBoardDto.get(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
	public ResponseEntity<?> createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
		return new ResponseEntity<>(service.createTrelloCard(trelloCardDto), HttpStatus.ACCEPTED);
	}
}
