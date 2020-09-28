package com.gy.exceptions;

public class RecommitException extends ServiceException {
    public RecommitException() {
        super();
    }

    public RecommitException(String message) {
        super(message);
    }

    public RecommitException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecommitException(Throwable cause) {
        super(cause);
    }

    protected RecommitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
