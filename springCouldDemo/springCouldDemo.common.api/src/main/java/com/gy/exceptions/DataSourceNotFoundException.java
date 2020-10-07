package com.gy.exceptions;

public class DataSourceNotFoundException extends ServiceException {
    public DataSourceNotFoundException() {
        super();
    }

    public DataSourceNotFoundException(String message) {
        super(message);
    }

    public DataSourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSourceNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DataSourceNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
