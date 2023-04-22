package com.es.core.exception;

import com.es.core.model.phone.Phone;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException() {
    }

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
