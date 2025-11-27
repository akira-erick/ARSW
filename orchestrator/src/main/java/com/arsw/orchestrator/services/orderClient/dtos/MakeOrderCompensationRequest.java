package com.arsw.orchestrator.services.orderClient.dtos;

public record MakeOrderCompensationRequest(
        Integer orderId,
        String reason
) {
}
