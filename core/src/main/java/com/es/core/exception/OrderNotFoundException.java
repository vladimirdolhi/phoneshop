package com.es.core.exception;

public class OrderNotFoundException extends RuntimeException {
    private Long id;

    public OrderNotFoundException(Long id, String message) {
        super(message);
        this.id = id;
    }

    public OrderNotFoundException(String message) {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderNotFoundException(Throwable cause) {
        super(cause);
    }

    public Long getId() {
        return id;
    }
}
