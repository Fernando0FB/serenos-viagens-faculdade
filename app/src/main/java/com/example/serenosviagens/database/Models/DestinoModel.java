package com.example.serenosviagens.database.Models;

public class DestinoModel {
    public static final String TABELA_NOME = "destinos";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_NOME = "nome",
            COLUNA_PAIS = "descricao";

    public static final String CREATE_TABLE =
            "create table if not exists '" + TABELA_NOME
                 + "' ("
                    + COLUNA_ID + " INTEGER PRIMARY KEY autoincrement, "
                    + COLUNA_NOME + " TEXT not null, "
                    + COLUNA_PAIS + " TEXT not null "
                 + ");";

    public static final String DROP_TABLE =
            "drop table if exists " + TABELA_NOME + ";";

    private long id;
    private String nome;
    private String descricao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
