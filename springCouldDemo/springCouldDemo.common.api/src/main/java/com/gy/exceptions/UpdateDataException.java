package com.gy.exceptions;

public class UpdateDataException extends ServiceException {
    public UpdateDataException() {
        super();
    }

    public UpdateDataException(String message) {
        super(message);
    }

    public UpdateDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateDataException(Throwable cause) {
        super(cause);
    }

    protected UpdateDataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
