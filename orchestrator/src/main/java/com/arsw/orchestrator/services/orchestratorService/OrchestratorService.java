package com.arsw.orchestrator.services.orchestratorService;

import com.arsw.orchestrator.services.orchestratorService.dtos.BuyRequest;
import com.arsw.orchestrator.services.orchestratorService.dtos.BuyResponse;
import com.arsw.orchestrator.services.orderClient.OrderServiceClient;
import com.arsw.orchestrator.services.orderClient.dtos.ChangeOrderStatusRequest;
import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderRequest;
import com.arsw.orchestrator.services.orderClient.dtos.MakeOrderResponse;
import com.arsw.orchestrator.services.paymentClient.PaymentServiceClient;
import com.arsw.orchestrator.services.paymentClient.dtos.MakePaymentRequest;
import com.arsw.orchestrator.services.stockClient.StockServiceClient;
import com.arsw.orchestrator.services.stockClient.dtos.ReductStockRequest;
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
        )).flatMap(orderResponse ->
                paymentServiceClient.callMakePayment(
                        new MakePaymentRequest(
                                orderResponse.orderId(),
                                request.amount(),
                                request.work()
                        )).flatMap(paymentResponse ->
                        stockServiceClient.callReduction(
                                new ReductStockRequest(
                                        request.item(),
                                        request.quantity()
                                ))
                                .doOnSuccess(reductStockResponse -> {
                                    orderServiceClient.callChageOrderStatus(
                                            new ChangeOrderStatusRequest(
                                                    orderResponse.orderId(),
                                                    "COMPLETED"
                                            )).subscribe();
                                })
                                .map(stockResponse ->
                                new BuyResponse(
                                        orderResponse.orderId(),
                                        orderResponse.status(),
                                        paymentResponse.paymentId(),
                                        paymentResponse.status(),
                                        stockResponse.quantity())
                        )
                )
        );
    }
}
