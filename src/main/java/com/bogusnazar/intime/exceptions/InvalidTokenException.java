package com.bogusnazar.intime.exceptions;

public class InvalidTokenException extends RuntimeException{
    private static final String generalMessage = "Invalid authentication token";

    public InvalidTokenException() {
        super(generalMessage);
    }

    public InvalidTokenException(String message) {
        super(generalMessage + ": " + message);
    }
}