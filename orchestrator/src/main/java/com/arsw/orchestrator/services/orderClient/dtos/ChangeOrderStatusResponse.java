package com.arsw.orchestrator.services.orderClient.dtos;

public record ChangeOrderStatusResponse(
        Integer orderId,
        String status
) {
}
