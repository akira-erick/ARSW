package com.arsw.orchestrator.services.paymentClient;

import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderResponse;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentRequest;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentServiceClient {
    private final WebClient webClient;

    public PaymentServiceClient(WebClient.Builder builder){
        this.webClient = builder
                .baseUrl("http://localhost:8082")
                .build();
    }

    public Mono<MakePaymentResponse> callMakeOrder(MakePaymentRequest request) {
        return webClient.post()
                .uri("/payment")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MakePaymentResponse.class);
    }
}
