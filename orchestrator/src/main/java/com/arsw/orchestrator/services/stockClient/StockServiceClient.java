package com.arsw.orchestrator.services.stockClient;

import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderRequest;
import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderResponse;
import com.arsw.orchestrator.services.stockClient.dtos.ReductStockRequest;
import com.arsw.orchestrator.services.stockClient.dtos.ReductStockResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StockServiceClient {
    private final WebClient webClient;

    public StockServiceClient(WebClient.Builder builder){
        this.webClient = builder
                .baseUrl("http://localhost:8083")
                .build();
    }

    public Mono<ReductStockResponse> callReduction(ReductStockRequest request) {
        return webClient.post()
                .uri("/stock/reduct")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ReductStockResponse.class);
    }
}
