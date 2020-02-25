package tasks.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tasks.domain.TrelloBoardDto;
import tasks.domain.TrelloCardDto;
import tasks.service.TrelloService;

@RestController
@RequestMapping("api/v1/boards")
public class TrelloController {

	TrelloService service;

	public TrelloController(TrelloService trelloService) {
		this.service = trelloService;
	}

	@ApiOperation(value = "Get all boards from trello API", notes = "Retrieving the collection of all boards from trello api", response = TrelloBoardDto[].class)
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = TrelloBoardDto[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping(value = "boards")
	public ResponseEntity<?> getBoards() {
		return new ResponseEntity<>(service.getTrelloBoards(), HttpStatus.OK);
	}

	@ApiOperation(value = "Get all boards from trello API", notes = "Retrieving the collection of all boards containing kodilla from trello api", response = TrelloBoardDto[].class)
	@ApiResponses({
			@ApiResponse(code = 200, message = "OK", response = TrelloBoardDto[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping(value = "boards/kodilla")
	public ResponseEntity<?> getBoardKodilla() {
		Optional<TrelloBoardDto> trelloBoardDto = service.getTrelloBoards().stream().filter(s -> s.getName().contains("Kodilla")).findFirst();
		if (!trelloBoardDto.isPresent()) {
			return new ResponseEntity<>(trelloBoardDto.get(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(trelloBoardDto.get(), HttpStatus.OK);
	}

	@PostMapping(value = "cards")
	public ResponseEntity<?> createdTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
		return new ResponseEntity<>(service.createTrelloCard(trelloCardDto), HttpStatus.ACCEPTED);
	}
}
