package com.stefanpetkov.medical.exception;

public class UserNameExistsException extends RuntimeException {

    public UserNameExistsException() {
        super();
    }

    public UserNameExistsException(String message) {
        super(message);
    }

    public UserNameExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}