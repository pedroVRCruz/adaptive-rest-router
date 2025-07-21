package com.adaptive.router.adaptive_rest_router.controller;

import com.adaptive.router.adaptive_rest_router.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proxy")
public class ProxyController {

    @Autowired
    private AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<String> interceptRequest(@RequestBody String body) {
        double entropia = analysisService.calcularEntropia(body);

        return ResponseEntity.ok("Entropia do payload: " + entropia);
    }
}
