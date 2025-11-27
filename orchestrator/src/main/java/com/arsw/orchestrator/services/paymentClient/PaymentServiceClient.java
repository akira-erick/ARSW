package com.arsw.orchestrator.services.paymentClient;

import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentCompensationRequest;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentCompensationResponse;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentRequest;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PaymentServiceClient {
    private final WebClient webClient;

    public PaymentServiceClient(WebClient.Builder builder){
        String host = System.getenv("PAYMENT_SERVICE_HOST");
        String port = System.getenv("PAYMENT_SERVICE_PORT");

        if (host == null || port == null) {
            throw new RuntimeException("Environment variables are missing");
        }

        String baseUrl = "http://" + host + ":" + port;
        System.out.println("Order service base URL = " + baseUrl);

        this.webClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<MakePaymentResponse> callMakePayment(MakePaymentRequest request) {
        return webClient.post()
                .uri("/payment")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MakePaymentResponse.class);
    }

    public Mono<MakePaymentCompensationResponse> callMakePaymentCompensation(MakePaymentCompensationRequest request) {
        return webClient.post()
                .uri("/payment/compensate")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(MakePaymentCompensationResponse.class);
    }
}
