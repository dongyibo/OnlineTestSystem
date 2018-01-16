package edu.nju.test.exception;

public class WrongTestCodeException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public WrongTestCodeException(String message){
        super(message);
    }
}
