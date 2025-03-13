package com.example.serenosviagens.database.Models;

import java.math.BigDecimal;

public class HospedagemModel {

    public static final String TABELA_NOME = "hospedagemDespesa";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_CUSTO_POR_NOITE = "custoPorNoite",
            COLUNA_TOTAL_NOITES = "totalNoites",
            COLUNA_TOTAL_QUARTOS = "totalQuartos",
            COLUNA_DESPESAID = "despesaId";
    public static final String
            CREATE_TABLE =
            "CREATE TABLE if not exists " + TABELA_NOME
                    + "( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_CUSTO_POR_NOITE + " NUMERIC NOT NULL, "
                    + COLUNA_TOTAL_NOITES + " INTEGER NOT NULL, "
                    + COLUNA_TOTAL_QUARTOS + " INTEGER NOT NULL, "
                    + COLUNA_DESPESAID + " INTEGER not null, "
                    + "FOREIGN KEY (" + COLUNA_DESPESAID + ") REFERENCES despesas(_id)"
                    + ");";

    public static final String
            DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private long id, despesaId;
    private BigDecimal custoMedioNoite;
    private Integer totalNoite, totalQuartos;

    public HospedagemModel(long despesaId, BigDecimal custoMedioNoite, Integer totalNoite, Integer totalQuartos) {
        this.despesaId = despesaId;
        this.custoMedioNoite = custoMedioNoite;
        this.totalNoite = totalNoite;
        this.totalQuartos = totalQuartos;
    }

    public HospedagemModel() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDespesaId() {
        return despesaId;
    }

    public void setDespesaId(long despesaId) {
        this.despesaId = despesaId;
    }

    public BigDecimal getCustoMedioNoite() {
        return custoMedioNoite;
    }

    public void setCustoMedioNoite(BigDecimal custoMedioNoite) {
        this.custoMedioNoite = custoMedioNoite;
    }

    public Integer getTotalNoite() {
        return totalNoite;
    }

    public void setTotalNoite(Integer totalNoite) {
        this.totalNoite = totalNoite;
    }

    public Integer getTotalQuartos() {
        return totalQuartos;
    }

    public void setTotalQuartos(Integer totalQuartos) {
        this.totalQuartos = totalQuartos;
    }

    public HospedagemModel(long id, long despesaId, BigDecimal custoMedioNoite, Integer totalNoite, Integer totalQuartos) {
        this.id = id;
        this.despesaId = despesaId;
        this.custoMedioNoite = custoMedioNoite;
        this.totalNoite = totalNoite;
        this.totalQuartos = totalQuartos;
    }
    public HospedagemModel(BigDecimal custoMedioNoite, Integer totalNoite, Integer totalQuartos) {
        this.custoMedioNoite = custoMedioNoite;
        this.totalNoite = totalNoite;
        this.totalQuartos = totalQuartos;
    }
}
