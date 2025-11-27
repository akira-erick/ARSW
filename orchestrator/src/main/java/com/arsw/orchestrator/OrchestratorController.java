package com.arsw.orchestrator;

import com.arsw.orchestrator.exceptions.GlobalErrorHandler;
import com.arsw.orchestrator.services.orchestratorService.OrchestratorService;
import com.arsw.orchestrator.services.orchestratorService.dtos.BuyRequest;
import com.arsw.orchestrator.services.orchestratorService.dtos.BuyResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/buy")
public class OrchestratorController {

    private final OrchestratorService orchestratorService;

    public OrchestratorController(OrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    @PostMapping("/")
    public Mono<BuyResponse> buy(@RequestBody BuyRequest request){
        return orchestratorService.buy(request);
    }
}
