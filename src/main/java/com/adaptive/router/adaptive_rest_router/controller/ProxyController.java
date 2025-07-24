package com.adaptive.router.adaptive_rest_router.controller;

import com.adaptive.router.adaptive_rest_router.Enum.Action;
import com.adaptive.router.adaptive_rest_router.service.AnalysisService;
import com.adaptive.router.adaptive_rest_router.service.CompressionService;
import com.adaptive.router.adaptive_rest_router.service.DecisionService;
import com.adaptive.router.adaptive_rest_router.service.ForwardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/proxy")
public class ProxyController {

    private static final Logger log = LoggerFactory.getLogger(ProxyController.class);

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private DecisionService decisionService;

    @Autowired
    private CompressionService compressionService;

    @Autowired
    private ForwardingService forwardingService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("PONG");
    }

    @PostMapping
    public ResponseEntity<String> interceptRequest(@RequestBody String body) {
        double entropia = analysisService.calcularEntropiaSomenteValores(body);
        Action acao = decisionService.decidir(body);

        log.info("[AUDITORIA] Entropia: {} | Ação: {} | Tamanho: {} | Hash: {}",
                entropia, acao, body.length(), body.hashCode());

        switch (acao) {
            case SUPRIMIR:
                return ResponseEntity.ok("Requisição suprimida (entropia: " + entropia + ")");

            case COMPRIMIR:
                String comprimido = compressionService.comprimir(body);
                String respostaComprimida = forwardingService.encaminhar(comprimido);
                return ResponseEntity.ok("Payload comprimido e encaminhado: " + respostaComprimida);

            case ENCAMINHAR:
                String resposta = forwardingService.encaminhar(body);
                return ResponseEntity.ok("Requisição encaminhada: " + resposta);

            default:
                return ResponseEntity.status(500).body("Ação desconhecida");
        }
    }
}
