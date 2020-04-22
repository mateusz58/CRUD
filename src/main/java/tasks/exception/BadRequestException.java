package tasks.exception;

public class BadRequestException extends Exception {

    public BadRequestException(String message) {
        super(message);
    }

    public static String messageEntityExists(String className, Long id) {
        return String.format("Bad request -  Entity %s with id %d already exists in database", className, id);
    }
}
