package com.nisemup.bakerymanager.exception;

public class NoEntityException extends RuntimeException {
    public NoEntityException(String message) {
        super(message);
    }

    public NoEntityException(String message, Throwable throwable) {
        super(message, throwable);
        this.setStackTrace(throwable.getStackTrace());
    }

    public NoEntityException(Throwable throwable) {
        super(throwable);
        this.setStackTrace(throwable.getStackTrace());
    }
}
