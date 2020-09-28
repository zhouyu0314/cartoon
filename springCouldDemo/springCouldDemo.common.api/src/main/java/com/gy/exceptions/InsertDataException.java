package com.gy.exceptions;

public class InsertDataException extends ServiceException{
    public InsertDataException() {
        super();
    }

    public InsertDataException(String message) {
        super(message);
    }

    public InsertDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public InsertDataException(Throwable cause) {
        super(cause);
    }

    protected InsertDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
