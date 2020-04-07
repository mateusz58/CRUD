package tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

    public static String badRequestMessage(String entityName) {
        return String.format("%s bad request", entityName);
    }

    public BadRequestException(String message) {
        super(message);
    }
}