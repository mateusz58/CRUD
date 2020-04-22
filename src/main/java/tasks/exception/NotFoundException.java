package tasks.exception;

public class NotFoundException extends Exception {

    public NotFoundException(String message) {
        super(message);
    }

    public static String message(String className, Long id) {
        return String.format("Entity %s with id %d not found", className, id);
    }
}
