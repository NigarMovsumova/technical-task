package az.technical.task.technicaltask.exceptions;

public class NoAccountOperationsException extends RuntimeException {
    public NoAccountOperationsException() {
    }

    public NoAccountOperationsException(String message) {
        super(message);
    }
}

