package com.arsw.orchestrator.services.orderClient;

import com.arsw.orchestrator.services.orderClient.dtos.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceClient {
    private final WebClient webClient;

    public OrderServiceClient(WebClient.Builder builder){
        String host = System.getenv("ORDER_SERVICE_HOST");
        String port = System.getenv("ORDER_SERVICE_PORT");

        if (host == null || port == null) {
            throw new RuntimeException("Environment variables are missing");
        }

        String baseUrl = "http://" + host + ":" + port;
        System.out.println("Order service base URL = " + baseUrl);

        this.webClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<MakeOrderResponse> callMakeOrder(MakeOrderRequest request) {
        System.out.println(request.customerName());
        return webClient.post()
                .uri("/order")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MakeOrderResponse.class);
    }

    public Mono<ChangeOrderStatusResponse> callChageOrderStatus(ChangeOrderStatusRequest request) {
        return webClient.put()
                .uri("/order/status")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ChangeOrderStatusResponse.class);
    }

    public Mono<MakeOrderCompensationResponse> callMakeOrderCompensation(MakeOrderCompensationRequest request) {
        return webClient.post()
                .uri("/order/compensate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MakeOrderCompensationResponse.class);
    }
}
