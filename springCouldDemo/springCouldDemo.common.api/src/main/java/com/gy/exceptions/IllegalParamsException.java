package com.gy.exceptions;

public class IllegalParamsException extends ServiceException{
    public IllegalParamsException() {
        super();
    }

    public IllegalParamsException(String message) {
        super(message);
    }

    public IllegalParamsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalParamsException(Throwable cause) {
        super(cause);
    }

    protected IllegalParamsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
