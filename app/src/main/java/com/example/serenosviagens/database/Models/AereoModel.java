package com.example.serenosviagens.database.Models;

import java.math.BigDecimal;

public class AereoModel {

    public static final String TABELA_NOME = "aereoDespesa";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_CUSTO_POR_PESSOA = "custoPorPessoa",
            COLUNA_ALUGUEL_VEICULO = "aluguelVeiculo",
            COLUNA_DESPESAID = "despesaId";
    public static final String
            CREATE_TABLE =
            "CREATE TABLE if not exists " + TABELA_NOME
                    + "( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_CUSTO_POR_PESSOA + " NUMERIC NOT NULL, "
                    + COLUNA_ALUGUEL_VEICULO + " NUMERIC NOT NULL, "
                    + COLUNA_DESPESAID + " INTEGER not null, "
                    + "FOREIGN KEY (" + COLUNA_DESPESAID + ") REFERENCES despesas(_id)"
                    + ");";


    public static final String
            DROP_TABLE = "drop table if exists " + TABELA_NOME;

    //-- Getters and Setters --//
    private long id, despesaId;
    private BigDecimal custoPessoa, custoAluguelVeiculo;

    public AereoModel(long despesaId, BigDecimal custoPessoa, BigDecimal custoAluguelVeiculo) {
        this.despesaId = despesaId;
        this.custoPessoa = custoPessoa;
        this.custoAluguelVeiculo = custoAluguelVeiculo;
    }

    public AereoModel() {

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

    public BigDecimal getCustoPessoa() {
        return custoPessoa;
    }

    public void setCustoPessoa(BigDecimal custoPessoa) {
        this.custoPessoa = custoPessoa;
    }

    public BigDecimal getCustoAluguelVeiculo() {
        return custoAluguelVeiculo;
    }

    public void setCustoAluguelVeiculo(BigDecimal custoAluguelVeiculo) {
        this.custoAluguelVeiculo = custoAluguelVeiculo;
    }

    public AereoModel(long id, long despesaId, BigDecimal custoPorPessoa, BigDecimal custoAluguelVeiculo) {
        this.id = id;
        this.despesaId = despesaId;
        this.custoPessoa = custoPorPessoa;
        this.custoAluguelVeiculo = custoAluguelVeiculo;
    }
    public AereoModel(BigDecimal custoPorPessoa, BigDecimal custoAluguelVeiculo) {
        this.id = id;
        this.despesaId = despesaId;
        this.custoPessoa = custoPorPessoa;
        this.custoAluguelVeiculo = custoAluguelVeiculo;
    }
}
