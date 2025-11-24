package com.arsw.orchestrator.services.orderClient;

import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderRequest;
import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceAClient {
    private final WebClient webClient;

    public OrderServiceAClient(WebClient.Builder builder){
        this.webClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    public Mono<MakeOrderResponse> callMakeOrder(MakeOrderRequest request) {
        return webClient.post()
                .uri("/order")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MakeOrderResponse.class);
    }
}
