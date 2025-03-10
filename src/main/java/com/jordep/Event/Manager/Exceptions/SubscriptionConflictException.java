package com.jordep.Event.Manager.Exceptions;

public class SubscriptionConflictException extends RuntimeException{
    public SubscriptionConflictException(String message) {
        super(message);
    }
}
