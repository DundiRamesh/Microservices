package com.bankapplication.cards.exception;

public class LoanInActiveException  extends RuntimeException{

    public LoanInActiveException(String message){
        super(message);
    }

}
