package az.technical.task.technicaltask.handlers;

import az.technical.task.technicaltask.exceptions.ErrorInfo;
import az.technical.task.technicaltask.exceptions.NoSuchAccountException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchAccountException.class)
    public ErrorInfo noSuchAccountExceptionHandler
            (HttpServletRequest request, NoSuchAccountException exception) {
        String message = exception.getLocalizedMessage();
        ErrorInfo errorInfo = ErrorInfo
                .builder()
                .message(message)
                .build();
        return errorInfo;
    }


}
