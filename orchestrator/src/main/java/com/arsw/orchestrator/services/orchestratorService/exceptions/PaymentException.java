package com.arsw.orchestrator.services.orchestratorService.exceptions;

public class PaymentException extends RuntimeException{

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}
