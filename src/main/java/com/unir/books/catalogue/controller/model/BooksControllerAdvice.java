package com.unir.books.catalogue.controller.model;

import com.unir.books.catalogue.exception.AuthorNotFoundException;
import com.unir.books.catalogue.exception.BookNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BooksControllerAdvice {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBookNotFound(BookNotFoundException ex) {
         return ResponseEntity
                 .status(HttpStatus.NOT_FOUND)
                 .body(ErrorResponse.builder()
                         .details(ex.getMessage())
                         .build());
     }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFound(AuthorNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponse.builder()
                        .details(ex.getMessage())
                        .build());
    }
}
