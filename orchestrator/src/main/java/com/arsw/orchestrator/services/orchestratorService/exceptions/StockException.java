package com.arsw.orchestrator.services.orchestratorService.exceptions;

public class StockException extends RuntimeException {

    public StockException(String message) {
        super(message);
    }

    public StockException(String message, Throwable cause) {
        super(message, cause);
    }
}
