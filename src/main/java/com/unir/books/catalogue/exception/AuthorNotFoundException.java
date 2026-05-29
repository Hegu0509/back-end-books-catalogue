package com.unir.books.catalogue.exception;

public class AuthorNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AuthorNotFoundException(String message) {
        super(message);
    }
    public AuthorNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
