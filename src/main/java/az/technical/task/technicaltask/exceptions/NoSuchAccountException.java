package az.technical.task.technicaltask.exceptions;

public class NoSuchAccountException extends RuntimeException {
    public NoSuchAccountException() {
    }

    public NoSuchAccountException(String message) {
        super(message);
    }
}
