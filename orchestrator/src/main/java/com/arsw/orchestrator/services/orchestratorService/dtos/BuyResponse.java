package com.arsw.orchestrator.services.orchestratorService.dtos;

public record BuyResponse(
        int orderId,
        String orderStatus,
        int paymentId,
        String paymentStatus,
        int stockQuantity
) {
}
