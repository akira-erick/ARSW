package com.arsw.orchestrator.exceptions;

public class ErrorResponse {
    private final String code;
    private final String message;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Include standard getters for code and message
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
