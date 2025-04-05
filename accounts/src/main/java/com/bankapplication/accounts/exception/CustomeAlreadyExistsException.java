package com.bankapplication.accounts.exception;

public class CustomeAlreadyExistsException extends RuntimeException{

    public CustomeAlreadyExistsException(String message){
        super(message);
    }

}
