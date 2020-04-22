package tasks.controller.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.dto.TrelloCardDto;
import tasks.service.ServiceFacade;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/trello/boards")
public class BoardsController {

	ServiceFacade service;

	public BoardsController(ServiceFacade service) {
		this.service = service;
	}

	@ApiOperation(value = "Get all boards from trello API", notes = "Retrieving the collection of all boards from trello api", response = TrelloBoardDto[].class)
	@ApiResponses( {
			@ApiResponse(code = 200, message = "OK", response = TrelloBoardDto[].class),
			@ApiResponse(code = 500, message = "Internal server error")
	})
	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(service.fetchAndValidateTrelloBoards(), HttpStatus.OK);
	}
}
