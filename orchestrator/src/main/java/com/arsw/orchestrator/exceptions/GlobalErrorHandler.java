package com.arsw.orchestrator.exceptions;

import com.arsw.orchestrator.services.orchestratorService.exceptions.PaymentException;
import com.arsw.orchestrator.services.orchestratorService.exceptions.StockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentError(PaymentException ex) {
        ErrorResponse errorResponse = new ErrorResponse("PAYMENT_ERROR", ex.getMessage());
        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(errorResponse);
    }

    @ExceptionHandler(StockException.class)
    public ResponseEntity<ErrorResponse> handleStockError(StockException ex) {
        ErrorResponse errorResponse = new ErrorResponse("OUT_OF_STOCK", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errorResponse);
    }
}
