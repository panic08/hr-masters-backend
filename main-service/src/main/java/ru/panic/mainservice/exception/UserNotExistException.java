package ru.panic.mainservice.exception;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
