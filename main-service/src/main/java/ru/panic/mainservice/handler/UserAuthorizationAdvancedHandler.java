package ru.panic.mainservice.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.panic.mainservice.exception.InvalidPasswordException;
import ru.panic.mainservice.exception.UserHasExistException;
import ru.panic.mainservice.exception.UserNotExistException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class UserAuthorizationAdvancedHandler {
    @ExceptionHandler(value = UserHasExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserHasExistException(HttpServletRequest request, UserHasExistException exception) {
        Map<String, String> body = new HashMap<>();

        body.put("error", "Conflict");
        body.put("message", exception.getMessage());
        body.put("path", request.getServletPath());

        return body;
    }

    @ExceptionHandler(value = UserNotExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUserNotExistException(HttpServletRequest request, UserNotExistException exception) {
        Map<String, String> body = new HashMap<>();

        body.put("error", "Conflict");
        body.put("message", exception.getMessage());
        body.put("path", request.getServletPath());

        return body;
    }

    @ExceptionHandler(value = InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleInvalidPasswordException(HttpServletRequest request, InvalidPasswordException exception) {
        Map<String, String> body = new HashMap<>();

        body.put("error", "Conflict");
        body.put("message", exception.getMessage());
        body.put("path", request.getServletPath());

        return body;
    }
}
