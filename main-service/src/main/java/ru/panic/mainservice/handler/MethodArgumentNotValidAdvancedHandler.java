package ru.panic.mainservice.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MethodArgumentNotValidAdvancedHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
        Map<String, String> body = new HashMap<>();

        body.put("error", "Bad Request");
        body.put("message", exception.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        body.put("path", request.getServletPath());

        return body;
    }
}
