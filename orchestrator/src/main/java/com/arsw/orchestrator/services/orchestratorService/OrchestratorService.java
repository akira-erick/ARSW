package com.arsw.orchestrator.services.orchestratorService;

import com.arsw.orchestrator.services.orchestratorService.dtos.BuyRequest;
import com.arsw.orchestrator.services.orchestratorService.dtos.BuyResponse;
import com.arsw.orchestrator.services.orchestratorService.exceptions.PaymentException;
import com.arsw.orchestrator.services.orchestratorService.exceptions.StockException;
import com.arsw.orchestrator.services.orderClient.OrderServiceClient;
import com.arsw.orchestrator.services.orderClient.dtos.ChangeOrderStatusRequest;
import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderCompensationRequest;
import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderRequest;
import com.arsw.orchestrator.services.paymentClient.PaymentServiceClient;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentCompensationRequest;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentRequest;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentResponse;
import com.arsw.orchestrator.services.stockClient.StockServiceClient;
import com.arsw.orchestrator.services.stockClient.dtos.ReductStockRequest;
import com.arsw.orchestrator.services.stockClient.dtos.ReductStockResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrchestratorService {
    private OrderServiceClient orderServiceClient;
    private PaymentServiceClient paymentServiceClient;
    private StockServiceClient stockServiceClient;

    public OrchestratorService(
            OrderServiceClient orderServiceClient,
            PaymentServiceClient paymentServiceClient,
            StockServiceClient stockServiceClient
    ) {
        this.orderServiceClient = orderServiceClient;
        this.paymentServiceClient = paymentServiceClient;
        this.stockServiceClient = stockServiceClient;
    }

    public Mono<BuyResponse> buy(BuyRequest request) {
        return orderServiceClient.callMakeOrder(
                new MakeOrderRequest(
                        request.customerName(),
                        request.item(),
                        request.quantity()
                )
        ).flatMap(orderResponse ->
                paymentServiceClient.callMakePayment(
                        new MakePaymentRequest(
                                orderResponse.orderId(),
                                request.amount()
                        )
                )
                        .onErrorResume( error ->
                                handlePaymentFailure(orderResponse.orderId(), error)
                        )
                        .flatMap(paymentResponse ->
                        stockServiceClient.callReduction(
                                new ReductStockRequest(
                                        request.item(),
                                        request.quantity()
                                )
                        )
                                .onErrorResume( error ->
                                        handleStockFailure(
                                                orderResponse.orderId(),
                                                paymentResponse.paymentId(),
                                                error
                                        )
                                )
                                .flatMap(reductStockResponse ->
                                orderServiceClient.callChageOrderStatus(
                                        new ChangeOrderStatusRequest(
                                                orderResponse.orderId(),
                                                "Completed"
                                        )
                                ).map(changeOrderStatusResponse ->
                                        new BuyResponse(
                                                orderResponse.orderId(),
                                                changeOrderStatusResponse.status(),
                                                paymentResponse.paymentId(),
                                                paymentResponse.status(),
                                                reductStockResponse.quantity()
                                        )
                                )

                        )
                )
        );
    }

    private Mono<MakePaymentResponse> handlePaymentFailure(Integer orderId, Throwable error) {
        System.out.println("Payment failure");
        return orderServiceClient
            .callMakeOrderCompensation(
                    new MakeOrderCompensationRequest(orderId, "PAYMENT_ERROR"))
            .then(Mono.error(new PaymentException("Payment failed", error)))
                .thenReturn(new MakePaymentResponse(0, ""));
    }

    private Mono<ReductStockResponse> handleStockFailure(Integer orderId, Integer paymentId, Throwable error) {
        System.out.println("Stock failure");
        return paymentServiceClient
            .callMakePaymentCompensation(
                    new MakePaymentCompensationRequest(paymentId, "OUT_OF_STOCK")
            )
            .then(
                    orderServiceClient.callMakeOrderCompensation(
                            new MakeOrderCompensationRequest(orderId, "OUT_OF_STOCK")
                    )
            )
            .then(Mono.error(new StockException("Product out of stock", error)))
                .thenReturn(new ReductStockResponse(0, 0));
    }
}
