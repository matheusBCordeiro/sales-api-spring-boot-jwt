package com.matheuscordeiro.salesapi.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException() {
        super("Order not found.");
    }
}
