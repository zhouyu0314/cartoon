package com.gy.exceptions;

public class SellOutException extends ServiceException {
    public SellOutException() {
        super();
    }

    public SellOutException(String message) {
        super(message);
    }

    public SellOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public SellOutException(Throwable cause) {
        super(cause);
    }

    protected SellOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
