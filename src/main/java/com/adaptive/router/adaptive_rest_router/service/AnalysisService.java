package com.adaptive.router.adaptive_rest_router.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AnalysisService {

    public double calcularEntropia(String conteudo) {
        if (conteudo == null || conteudo.isEmpty()) {
            return 0.0;
        }

        Map<Character, Integer> frequencias = new HashMap<>();
        int totalCaracteres = conteudo.length();

        // Contagem de frequência de cada caractere
        for (char c : conteudo.toCharArray()) {
            frequencias.put(c, frequencias.getOrDefault(c, 0) + 1);
        }

        // Cálculo da entropia
        double entropia = 0.0;
        for (int freq : frequencias.values()) {
            double probabilidade = (double) freq / totalCaracteres;
            entropia += probabilidade * (Math.log(probabilidade) / Math.log(2));
        }

        return -entropia; // Resultado final em bits
    }
}
