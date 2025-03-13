package com.example.serenosviagens.database.Models;

public class ViagemModel {

    public static final String TABELA_NOME = "viagens";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_DESCRICAO = "descricao",
            COLUNA_DESTINO = "destino",
            COLUNA_PESSOAS = "quantPessoas",
            COLUNA_DATA = "data",
            COLUNA_DATA_FIM = "dataFim",
            COLUNA_SINCRONIZADO = "sincronizado",
            COLUNA_IDNUVEM = "idNuvem",
            COLUNA_USERID = "userId";

    public static final String CREATE_TABLE =
            "create table if not exists " + TABELA_NOME
                + "("
                +   COLUNA_ID + " INTEGER PRIMARY KEY autoincrement, "
                +   COLUNA_DESCRICAO + " TEXT not null, "
                +   COLUNA_DESTINO + " TEXT not null, "
                +   COLUNA_PESSOAS + " INTEGER not null, "
                +   COLUNA_DATA + " DATE not null, "
                +   COLUNA_DATA_FIM + " DATE not null, "
                +   COLUNA_SINCRONIZADO + " BOOLEAN not null, "
                +   COLUNA_IDNUVEM + " INTEGER, "
                +   COLUNA_USERID + " INTEGER not null, "
                +   "FOREIGN KEY ("+COLUNA_USERID+ ") REFERENCES usuario(_id)"
                + ");";

    public static final String DROP_TABLE =
            "drop table if exists " + TABELA_NOME + ";";

    private long id, userId;
    private Integer quantPessoas, idNuvem;
    private String descricao, data, dataFim, destino;
    private Boolean sincronizado;

    public ViagemModel(long userId, Integer quantPessoas, Integer idNuvem, String descricao, String data, String dataFim, String destino, Boolean sincronizado) {
        this.userId = userId;
        this.quantPessoas = quantPessoas;
        this.idNuvem = idNuvem;
        this.descricao = descricao;
        this.data = data;
        this.dataFim = dataFim;
        this.destino = destino;
        this.sincronizado = sincronizado;
    }

    public ViagemModel(String descricao, String destino, Integer quantPessoas, String data, String dataFim, Long userId) {
        this.descricao = descricao;
        this.destino = destino;
        this.data = data;
        this.dataFim = dataFim;
        this.quantPessoas = quantPessoas;
        this.userId = userId;
    }

    public ViagemModel(String descricao, String destino, String data, Long userId) {
        this.descricao = descricao;
        this.destino = destino;
        this.data = data;
        this.userId = userId;
    }

    public ViagemModel(String descricao, String destino, String data) {
        this.descricao = descricao;
        this.destino = destino;
        this.data = data;
    }

    public ViagemModel(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public Integer getQuantPessoas() {
        return quantPessoas;
    }

    public void setQuantPessoas(Integer quantPessoas) {
        this.quantPessoas = quantPessoas;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getIdNuvem() {
        return idNuvem;
    }

    public void setIdNuvem(Integer idNuvem) {
        this.idNuvem = idNuvem;
    }

    public Boolean getSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(Boolean sincronizado) {
        this.sincronizado = sincronizado;
    }
}
