package com.arsw.orchestrator.services.stockClient;
import com.arsw.orchestrator.services.stockClient.dtos.ReductStockRequest;
import com.arsw.orchestrator.services.stockClient.dtos.ReductStockResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StockServiceClient {
    private final WebClient webClient;

    public StockServiceClient(WebClient.Builder builder){
        String host = System.getenv("STOCK_SERVICE_HOST");
        String port = System.getenv("STOCK_SERVICE_PORT");

        if (host == null || port == null) {
            throw new RuntimeException("Environment variables are missing");
        }

        String baseUrl = "http://" + host + ":" + port;
        System.out.println("Order service base URL = " + baseUrl);

        this.webClient = builder
                .baseUrl(baseUrl)
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
