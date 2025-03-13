package com.example.serenosviagens.database.Models;

import java.math.BigDecimal;

public class DespesasModel {

    public static final String TABELA_NOME = "despesas";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_TIPO = "tipo",
            COLUNA_VALOR = "valor",
            COLUNA_ADICIONAR_TOTAL = "adcTotal",
            COLUNA_VIAGEMID = "viagemId";
    ;

    public static final String CREATE_TABLE =
            "create table if not exists '" + TABELA_NOME
                    + "' ("
                    +   COLUNA_ID + " INTEGER PRIMARY KEY autoincrement, "
                    +   COLUNA_DESCRICAO + " TEXT not null, "
                    +   COLUNA_TIPO + " TEXT not null, "
                    +   COLUNA_VALOR + " NUMERIC not null, "
                    +   COLUNA_ADICIONAR_TOTAL + " TEXT not null, "
                    +   COLUNA_VIAGEMID + " INTEGER not null, "
                    +   "FOREIGN KEY ("+COLUNA_VIAGEMID+ ") REFERENCES viagens(_id)"
                    + ");";


    public static final String DROP_TABLE =
            "drop table if exists " + TABELA_NOME + ";";

    private long id, viagemId;
    private String descricao, tipo, adcTotal;
    private BigDecimal valor;

    public DespesasModel(long id, long viagemId, String descricao, String tipo, String adcTotal, BigDecimal valor) {
        this.id = id;
        this.viagemId = viagemId;
        this.descricao = descricao;
        this.tipo = tipo;
        this.adcTotal = adcTotal;
        this.valor = valor;
    }

    public DespesasModel(String descricao, String tipo, BigDecimal valor, String adcTotal, Long viagemId){
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
        this.adcTotal = adcTotal;
        this.viagemId = viagemId;
    }

    public DespesasModel(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getViagemId() {
        return viagemId;
    }

    public void setViagemId(long viagemId) {
        this.viagemId = viagemId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAdcTotal() {
        return adcTotal;
    }

    public void setAdcTotal(String adcTotal) {
        this.adcTotal = adcTotal;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
