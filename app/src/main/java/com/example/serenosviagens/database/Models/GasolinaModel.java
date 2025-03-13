package com.example.serenosviagens.database.Models;

import java.math.BigDecimal;

public class GasolinaModel {

    public static final String TABELA_NOME = "gasolinaDespesa";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_KM_ESTIMADOS = "kmsEstimados",
            COLUNA_MEDIA_KM_L = "mediaKmL",
            COLUNA_CUSTO_MEDIO_L = "custoMedioL",
            COLUNA_QUANT_VEICULOS = "quantVeiculos",
            COLUNA_DESPESAID = "despesaId";

    public static final String CREATE_TABLE =
            "create table if not exists '" + TABELA_NOME
                    +"' ("
                    +  COLUNA_ID + " INTEGER PRIMARY KEY autoincrement, "
                    +  COLUNA_KM_ESTIMADOS + " NUMERIC not null, "
                    +  COLUNA_MEDIA_KM_L + " NUMERIC not null, "
                    +  COLUNA_CUSTO_MEDIO_L + " NUMERIC not null, "
                    +  COLUNA_QUANT_VEICULOS + " INTEGER not null, "
                    +  COLUNA_DESPESAID + " INTEGER not null, "
                    +   "FOREIGN KEY ("+COLUNA_DESPESAID+ ") REFERENCES despesas(_id)"
                    +");";

    public static final String DROP_TABLE =
            "drop table if exists " + TABELA_NOME + ";";

    private long id, totalVeiculos, despesaId;
    private BigDecimal totalEstimadoKM, mediaKMLitro, custoMedioLitro;

    public GasolinaModel(long id, long totalVeiculos, long despesaId, BigDecimal totalEstimadoKM, BigDecimal mediaKMLitro, BigDecimal custoMedioLitro) {
        this.id = id;
        this.totalVeiculos = totalVeiculos;
        this.despesaId = despesaId;
        this.totalEstimadoKM = totalEstimadoKM;
        this.mediaKMLitro = mediaKMLitro;
        this.custoMedioLitro = custoMedioLitro;
    }

    public GasolinaModel(long totalVeiculos, BigDecimal totalEstimadoKM, BigDecimal mediaKMLitro, BigDecimal custoMedioLitro) {
        this.totalVeiculos = totalVeiculos;
        this.totalEstimadoKM = totalEstimadoKM;
        this.mediaKMLitro = mediaKMLitro;
        this.custoMedioLitro = custoMedioLitro;
    }

    public GasolinaModel(long totalVeiculos, long despesaId, BigDecimal totalEstimadoKM, BigDecimal mediaKMLitro, BigDecimal custoMedioLitro) {
        this.totalVeiculos = totalVeiculos;
        this.despesaId = despesaId;
        this.totalEstimadoKM = totalEstimadoKM;
        this.mediaKMLitro = mediaKMLitro;
        this.custoMedioLitro = custoMedioLitro;
    }

    public GasolinaModel(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTotalVeiculos() {
        return totalVeiculos;
    }

    public void setTotalVeiculos(long totalVeiculos) {
        this.totalVeiculos = totalVeiculos;
    }

    public long getDespesaId() {
        return despesaId;
    }

    public void setDespesaId(long despesaId) {
        this.despesaId = despesaId;
    }

    public BigDecimal getTotalEstimadoKM() {
        return totalEstimadoKM;
    }

    public void setTotalEstimadoKM(BigDecimal totalEstimadoKM) {
        this.totalEstimadoKM = totalEstimadoKM;
    }

    public BigDecimal getMediaKMLitro() {
        return mediaKMLitro;
    }

    public void setMediaKMLitro(BigDecimal mediaKMLitro) {
        this.mediaKMLitro = mediaKMLitro;
    }

    public BigDecimal getCustoMedioLitro() {
        return custoMedioLitro;
    }

    public void setCustoMedioLitro(BigDecimal custoMedioLitro) {
        this.custoMedioLitro = custoMedioLitro;
    }
}
