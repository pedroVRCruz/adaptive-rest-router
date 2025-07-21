package com.adaptive.router.adaptive_rest_router.service;

import com.adaptive.router.adaptive_rest_router.Enum.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DecisionService {
    @Autowired
    private AnalysisService analysisService;

    public Action decidir(String conteudoJson) {
        //calcular a entropia para tomada de decisao
        double h = analysisService.calcularEntropia(conteudoJson);

        //parametros de ref para tomada de decisao
        //necessários mais testes para validar os parametros condicionais
        if (h < 2.0) return Action.SUPRIMIR;
        if (h < 3.5) return Action.COMPRIMIR;
        return Action.ENCAMINHAR;
    }
}
