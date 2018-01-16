package edu.nju.test.exception;

public class TimeExceededException extends RuntimeException{
    public TimeExceededException(String message){
        super(message);
    }
}
