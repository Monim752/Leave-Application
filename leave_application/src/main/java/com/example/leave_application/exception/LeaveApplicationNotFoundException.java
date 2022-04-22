package com.example.leave_application.exception;

public class LeaveApplicationNotFoundException extends RuntimeException{
    public LeaveApplicationNotFoundException(String msg){
        super(msg);
    }
}
