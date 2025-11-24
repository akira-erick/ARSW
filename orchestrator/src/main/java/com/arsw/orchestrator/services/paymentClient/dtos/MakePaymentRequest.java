package com.arsw.orchestrator.services.paymentClient.dtos;

public record MakePaymentRequest(
        Integer orderId,
        Float amount,
        Boolean work
) {
}
