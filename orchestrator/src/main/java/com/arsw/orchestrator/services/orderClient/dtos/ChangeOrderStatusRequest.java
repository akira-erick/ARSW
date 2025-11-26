package com.arsw.orchestrator.services.orderClient.dtos;

public record ChangeOrderStatusRequest(
        Integer orderId,
        String status
) {
}
