package com.bargainbee.itemlistingservice.exception;

public class UnauthorizedItemModificationException extends RuntimeException {
    public UnauthorizedItemModificationException() {
    }

    public UnauthorizedItemModificationException(String message) {
        super(message);
    }

    public UnauthorizedItemModificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedItemModificationException(Throwable cause) {
        super(cause);
    }

    public UnauthorizedItemModificationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
