package com.unir.supplies.orders.controller;

import com.unir.supplies.orders.controller.model.ErrorResponse;
import com.unir.supplies.orders.exception.SupplyNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SuppliesControllerAdvice {

    @ExceptionHandler(SupplyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSupplyNotFound(SupplyNotFoundException ex) {
         return ResponseEntity
                 .status(HttpStatus.NOT_FOUND)
                 .body(ErrorResponse.builder()
                         .details(ex.getMessage())
                         .build());
     }
}
