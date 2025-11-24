package com.arsw.orchestrator.services.orderClient.dtos;

public record MakeOrderResponse(
        int orderId,
        String status
) {
}
