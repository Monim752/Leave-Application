package com.example.leave_application.exception;

public class LeaveTypeNotFoundException extends NullPointerException{
    public LeaveTypeNotFoundException(String msg){
        super(msg);
    }
}
