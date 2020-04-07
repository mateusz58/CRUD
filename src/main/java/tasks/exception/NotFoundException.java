package tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends Exception {

    public static String notFoundMessage(String entityName) {
        return String.format("%s not found", entityName);
    }

    public NotFoundException (String message) {
        super(message);
    }
}