package ru.panic.mainservice.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
