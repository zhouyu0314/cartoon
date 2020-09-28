package com.gy.exceptions;

public class UsernameConflictExcaption extends ServiceException {
    public UsernameConflictExcaption() {
        super();
    }

    public UsernameConflictExcaption(String message) {
        super(message);
    }

    public UsernameConflictExcaption(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameConflictExcaption(Throwable cause) {
        super(cause);
    }

    protected UsernameConflictExcaption(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
