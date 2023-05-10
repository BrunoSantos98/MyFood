package com.MyFood.exceptions;

public class ObjectRequiredNotFoundException extends RuntimeException{
    public ObjectRequiredNotFoundException(String message) {
        super(message);
    }
}
