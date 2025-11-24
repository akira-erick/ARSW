package com.arsw.orchestrator.services.paymentClient.dtos;

public record MakePaymentResponse(
    Integer paymentId,
    String status
) {
}
