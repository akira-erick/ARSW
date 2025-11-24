package com.arsw.orchestrator.services.orchestratorService.dtos;

public record BuyRequest(
        String customerName,
        Integer item,
        Integer quantity,
        Float amount,
        Boolean work
) {
}
