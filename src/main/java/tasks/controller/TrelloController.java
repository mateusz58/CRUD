package tasks.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.Collection;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.dto.TrelloCardDto;
import tasks.service.ServiceFacade;

@RestController
@RequestMapping("api/v1/trello/")
public class TrelloController {

	ServiceFacade service;

	public TrelloController(ServiceFacade service) {
		this.service = service;
	}

	@ApiOperation(value = "Get all boards from trello API", notes = "Retrieving the collection of all boards from trello api", response = TrelloBoardDto[].class)
	@ApiResponses( {
			@ApiResponse(code = 200, message = "OK", response = TrelloBoardDto[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping(value = "boards")
	public ResponseEntity<?> getBoards() {
		Collection<TrelloBoardDto> trelloBoardDtos = service.fetchAndValidateTrelloBoards();
		return new ResponseEntity<>(trelloBoardDtos, HttpStatus.OK);
	}

	@ApiOperation(value = "Get all boards from trello API", notes = "Retrieving the collection of all boards containing kodilla from trello api", response = TrelloBoardDto[].class)
	@ApiResponses( {
			@ApiResponse(code = 200, message = "OK", response = TrelloBoardDto[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping(value = "boards/kodilla")
	public ResponseEntity<?> getBoardKodilla() {
		Optional<TrelloBoardDto> trelloBoardDto = service.fetchAndValidateTrelloBoards().stream().filter(s -> s.getName().contains("Kodilla")).findFirst();
		if (!trelloBoardDto.isPresent()) {
			return new ResponseEntity<>(trelloBoardDto.get(), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(trelloBoardDto.get(), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Add new card", notes = "Add new card to trello", response = TrelloCardDto.class)
	@ApiResponses( {
			@ApiResponse(code = 201, message = "Created", response = TrelloCardDto.class),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 406, message = "Not acceptable format"),
			@ApiResponse(code = 409, message = "Card exists"),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@PostMapping(value = "cards")
	public ResponseEntity<?> createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
		try {
			if(trelloCardDto == null) {
				throw new IllegalArgumentException("Passed arguments are equal null");
			}
			return new ResponseEntity<>(service.postCartCreate(trelloCardDto), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
