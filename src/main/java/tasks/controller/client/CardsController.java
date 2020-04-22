package tasks.controller.client;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tasks.domain.dto.TrelloBoardDto;
import tasks.domain.dto.TrelloCardDto;
import tasks.exception.BadRequestException;
import tasks.service.ServiceFacade;

import java.lang.invoke.MethodHandles;
import java.util.Collection;

//Example values
//{
//		"name": "string",
//		"description": "string",
//		"pos": "top",
//		"listId": "5e1e385f96e74908d66183cb"
//		}
@RestController
@RequestMapping("api/v1/trello/cards")
public class CardsController {

	ServiceFacade service;

	private static final Logger log = LoggerFactory.getLogger(CardsController.class);

	public CardsController(ServiceFacade service) {
		this.service = service;
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
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@Validated  @RequestBody final TrelloCardDto trelloCardDto) throws BadRequestException {
			if(trelloCardDto == null) {
				log.error("Passed arguments are equal null");
				throw new IllegalArgumentException("Passed arguments are equal null");
			}
			return new ResponseEntity<>(service.performCartCreationWithEmailNotification(trelloCardDto), HttpStatus.CREATED);
	}
}
