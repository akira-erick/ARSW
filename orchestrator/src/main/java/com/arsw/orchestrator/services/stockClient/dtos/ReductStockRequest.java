package com.arsw.orchestrator.services.stockClient.dtos;

public record ReductStockRequest(
        Integer id,
        Integer quantity
) {
}
