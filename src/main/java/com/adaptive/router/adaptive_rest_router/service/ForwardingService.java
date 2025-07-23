package com.adaptive.router.adaptive_rest_router.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

@Service
public class ForwardingService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String DESTINO_URL = "http://localhost:8081/api/destino"; //implementar metodo de persistencia de dados e apontar para lá

    public String encaminhar(String payload) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        try {
            return restTemplate.postForObject(DESTINO_URL, entity, String.class);
        } catch (Exception e) {
            return "Erro ao encaminhar: " + e.getMessage();
        }
    }
}
