package com.arsw.orchestrator.services.orderClient.dtos;

public record MakeOrderResponse(
        Integer orderId,
        String status
) {
}
