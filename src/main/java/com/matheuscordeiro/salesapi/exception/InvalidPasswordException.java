package com.matheuscordeiro.salesapi.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Invalid password");
    }
}
