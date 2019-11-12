package az.technical.task.technicaltask.exceptions;

public class WrongPaymentChoiceException extends RuntimeException {

    public WrongPaymentChoiceException() {
    }

    public WrongPaymentChoiceException(String message) {
        super(message);
    }
}