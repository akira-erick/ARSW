package com.arsw.orchestrator.services.orchestratorService.dtos;

public record BuyResponse(
        Integer orderId,
        String orderStatus,
        Integer paymentId,
        String paymentStatus,
        Integer stockQuantity
) {
}
