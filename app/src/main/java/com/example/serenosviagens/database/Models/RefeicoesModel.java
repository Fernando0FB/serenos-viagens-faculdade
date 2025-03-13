package com.example.serenosviagens.database.Models;

import java.math.BigDecimal;

public class RefeicoesModel {

    public static final String TABELA_NOME = "refeicaoDespesa";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_CUSTO_POR_REFEICAO = "custoPorRefeicao",
            COLUNA_QUANTIDADE_REFEICAO = "quantidadeRefeicao",
            COLUNA_DESPESAID = "despesaId";

    public static final String
            CREATE_TABLE =
            "CREATE TABLE if not exists " + TABELA_NOME
                    + "( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_CUSTO_POR_REFEICAO + " NUMERIC NOT NULL, "
                    + COLUNA_QUANTIDADE_REFEICAO + " INTEGER NOT NULL, "
                    + COLUNA_DESPESAID + " INTEGER not null, "
                    + "FOREIGN KEY (" + COLUNA_DESPESAID + ") REFERENCES despesas(_id)"
                    + ");";

    public static final String
            DROP_TABLE = "drop table if exists " + TABELA_NOME;

    private long id, despesaId;
    private BigDecimal custoRefeicao;
    private Integer refeicoesDia;

    public RefeicoesModel(long despesaId, BigDecimal custoRefeicao, Integer refeicoesDia) {
        this.despesaId = despesaId;
        this.custoRefeicao = custoRefeicao;
        this.refeicoesDia = refeicoesDia;
    }

    public RefeicoesModel() {
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

    public BigDecimal getCustoRefeicao() {
        return custoRefeicao;
    }

    public void setCustoRefeicao(BigDecimal custoRefeicao) {
        this.custoRefeicao = custoRefeicao;
    }

    public Integer getRefeicoesDia() {
        return refeicoesDia;
    }

    public void setRefeicoesDia(Integer refeicoesDia) {
        this.refeicoesDia = refeicoesDia;
    }

    public RefeicoesModel(BigDecimal custoRefeicao, Integer refeicoesDia) {
        this.custoRefeicao = custoRefeicao;
        this.refeicoesDia = refeicoesDia;
    }
}
