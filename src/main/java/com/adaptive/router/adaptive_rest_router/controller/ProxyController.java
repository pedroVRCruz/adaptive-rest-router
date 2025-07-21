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
        double entropia = analysisService.calcularEntropia(body);
        Action acao = decisionService.decidir(body);

        switch (acao) {
            case SUPRIMIR:
                return ResponseEntity.ok("Requisição suprimida (entropia: " + entropia + ")");
            case COMPRIMIR:
                String comprimido = body.replaceAll("\"status\":\"ativo\"", "");
                return ResponseEntity.ok("Payload comprimido: " + comprimido);
            case ENCAMINHAR:
                return ResponseEntity.ok("Requisição encaminhada (entropia: " + entropia + ")");
            default:
                return ResponseEntity.status(500).body("Erro inesperado");
        }
    }
}
