package com.company.exeption;

public class InsufficientFundsException extends GlobalException{
    public InsufficientFundsException(String message) {
        super(message);
    }
}
