package com.example.leave_application.exception;

public class BalanceNotAvailableException extends RuntimeException{
    public BalanceNotAvailableException(String massage){
        super(massage);
    }
}
