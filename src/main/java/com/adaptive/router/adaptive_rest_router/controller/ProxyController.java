package com.adaptive.router.adaptive_rest_router.controller;

import com.adaptive.router.adaptive_rest_router.Enum.Action;
import com.adaptive.router.adaptive_rest_router.service.AnalysisService;
import com.adaptive.router.adaptive_rest_router.service.DecisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proxy")
public class ProxyController {

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private DecisionService decisionService;

    @PostMapping
    public ResponseEntity<String> interceptRequest(@RequestBody String body) {
        double entropia = analysisService.calcularEntropiaSomenteValores(body);
        Action acao = decisionService.decidir(body);

        return ResponseEntity.ok("Entropia: " + entropia + " | Ação: " + acao);
    }
}

