package com.arsw.orchestrator.services.paymentClient.dtos;

public record MakePaymentRequest(
        int orderId,
        float amount,
        boolean work
) {
}
