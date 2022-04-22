package com.example.leave_application.exception;

public class EmailNotFoundException extends RuntimeException{

    public EmailNotFoundException(String massage){
        super(massage);
    }

}
