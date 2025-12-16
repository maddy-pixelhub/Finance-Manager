package com.finance;
public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String c) {
        super("Invalid currency: " + c);
    }
}


