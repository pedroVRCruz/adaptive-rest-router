package com.adaptive.router.adaptive_rest_router.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class AnalysisService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public double calcularEntropiaSomenteValores(String conteudo) {
        if (conteudo == null || conteudo.isEmpty()) return 0.0;

        try {
            JsonNode root = objectMapper.readTree(conteudo);
            StringBuilder valoresConcat = new StringBuilder();

            Iterator<JsonNode> elementos = root.elements();
            while (elementos.hasNext()) {
                JsonNode valor = elementos.next();
                valoresConcat.append(valor.asText());
            }
            return calcularEntropia(valoresConcat.toString());

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    // cálculo de entropia padrão
    public double calcularEntropia(String texto) {
        if (texto == null || texto.isEmpty()) return 0.0;

        int[] freq = new int[256]; //ASCII de 8 bits
        int total = texto.length();

        for (char c : texto.toCharArray()) {
            freq[c]++;
        }

        double entropia = 0.0;
        for (int f : freq) {
            if (f > 0) {
                double p = (double) f / total;
                entropia += p * (Math.log(p) / Math.log(2));
            }
        }

        return -entropia;
    }
}
