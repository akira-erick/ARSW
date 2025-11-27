package com.arsw.orchestrator.services.paymentClient.dtos;

public record MakePaymentCompensationRequest(
        Integer paymentId,
        String reason
) {
}
