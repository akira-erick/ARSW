package com.arsw.orchestrator.services.paymentClient.dtos;

public record MakePaymentResponse(
    int paymentId,
    String status
) {
}
