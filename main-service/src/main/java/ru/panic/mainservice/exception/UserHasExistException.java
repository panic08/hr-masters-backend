package ru.panic.mainservice.exception;

public class UserHasExistException extends RuntimeException {
    public UserHasExistException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
