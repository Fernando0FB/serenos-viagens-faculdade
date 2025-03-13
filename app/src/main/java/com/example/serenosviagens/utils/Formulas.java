package com.example.serenosviagens.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Formulas {

    public BigDecimal calculaGasolina(BigDecimal totalKm, BigDecimal mediaKmL, BigDecimal custoLitroGasolina, Integer totalVeiculos) {
        return totalKm.divide(mediaKmL, 2, RoundingMode.HALF_UP)
                .multiply(custoLitroGasolina)
                .divide(BigDecimal.valueOf(totalVeiculos), 2, RoundingMode.HALF_UP)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculaAereo(BigDecimal custoPorPessoa, BigDecimal custoAluguel, Integer totalViajantes){
        return custoPorPessoa.multiply(BigDecimal.valueOf(totalViajantes))
                .add(custoAluguel)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculaRefeicao(Integer quantRefeicaoDia, Integer totalViajantes, BigDecimal custoEstimado, Integer duracaoViagem) {
        return BigDecimal.valueOf(quantRefeicaoDia)
                .multiply(BigDecimal.valueOf(totalViajantes))
                .multiply(custoEstimado)
                .multiply(BigDecimal.valueOf(duracaoViagem))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculaHospedagem(BigDecimal custoMedio, Integer quantNoites, Integer quantQuartos) {
        return custoMedio.multiply(BigDecimal.valueOf(quantNoites))
                .multiply(BigDecimal.valueOf(quantQuartos))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
