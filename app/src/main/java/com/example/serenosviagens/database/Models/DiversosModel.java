package com.example.serenosviagens.database.Models;

import java.math.BigDecimal;

public class DiversosModel {

    public static final String TABELA_NOME = "diversosDespesa";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_VALOR = "valor",
            COLUNA_DESPESAID = "despesaId";
    public static final String
            CREATE_TABLE =
            "CREATE TABLE if not exists " + TABELA_NOME
                    + "( "
                    + COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUNA_DESCRICAO + " TEXT NOT NULL, "
                    + COLUNA_VALOR + " NUMERIC NOT NULL, "
                    + COLUNA_DESPESAID + " INTEGER not null, "
                    + "FOREIGN KEY (" + COLUNA_DESPESAID + ") REFERENCES despesas(_id)"
                    + ");";


    public static final String
            DROP_TABLE = "drop table if exists " + TABELA_NOME;

    //-- Getters and Setters --//
    private long id, despesaId;
    private BigDecimal valor;
    private String entretenimento;

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

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEntretenimento() {
        return entretenimento;
    }

    public void setEntretenimento(String entretenimento) {
        this.entretenimento = entretenimento;
    }


    public DiversosModel() {
    }

    public DiversosModel(long despesaId, BigDecimal valor, String entretenimento) {
        this.despesaId = despesaId;
        this.valor = valor;
        this.entretenimento = entretenimento;
    }

    public DiversosModel(BigDecimal valor, String entretenimento) {
        this.valor = valor;
        this.entretenimento = entretenimento;
    }

    public DiversosModel(BigDecimal valor) {
        this.valor = valor;
    }
}
