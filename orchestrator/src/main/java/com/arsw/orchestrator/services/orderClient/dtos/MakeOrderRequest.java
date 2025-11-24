package com.arsw.orchestrator.services.orderClient.dtos;

public record MakeOrderRequest(
        String customerName,
        String item,
        int quantity
) {
}
