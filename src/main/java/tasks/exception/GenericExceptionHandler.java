package tasks.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class GenericExceptionHandler {

    @ExceptionHandler({BadRequestException.class
            , IllegalArgumentException.class
            , MethodArgumentNotValidException.class
            ,HttpClientErrorException.class})
    public ResponseEntity<?> handleBadRequest(Exception ex) {
        return buildResponseEntity(ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleEntityNotFound(Exception ex) {
        return buildResponseEntity(ApiError.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build());
    }

    private ResponseEntity<?> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError,apiError.getStatus());
    }
}
