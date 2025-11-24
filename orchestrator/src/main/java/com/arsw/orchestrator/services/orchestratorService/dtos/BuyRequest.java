package com.arsw.orchestrator.services.orchestratorService.dtos;

public record BuyRequest(
        String customerName,
        int item,
        int quantity,
        float amount,
        boolean work
) {
}
