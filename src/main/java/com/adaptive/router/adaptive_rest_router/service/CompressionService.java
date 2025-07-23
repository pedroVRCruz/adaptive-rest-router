package com.adaptive.router.adaptive_rest_router.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class CompressionService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final double THRESHOLD = 2.5; // abaixo disso o campo é considerado "informacionalmente irrelevante"

    public String comprimir(String body) {
        try {
            ObjectNode root = (ObjectNode) objectMapper.readTree(body);
            Iterator<Map.Entry<String, com.fasterxml.jackson.databind.JsonNode>> campos = root.fields();

            while (campos.hasNext()) {
                Map.Entry<String, com.fasterxml.jackson.databind.JsonNode> campo = campos.next();
                String valor = campo.getValue().asText();

                double entropia = calcularEntropia(valor);
                if (entropia < THRESHOLD) {
                    root.put(campo.getKey(), ""); // ou root.remove(...) se quiser apagar o campo
                }
            }
            System.out.println(objectMapper.writeValueAsString(root));
            return objectMapper.writeValueAsString(root);

        } catch (Exception e) {
            e.printStackTrace();
            return body;
        }
    }

    private double calcularEntropia(String texto) {
        if (texto == null || texto.isEmpty()) return 0.0;

        int[] freq = new int[256];
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
