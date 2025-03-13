package com.example.serenosviagens.database.Models;

import com.example.serenosviagens.database.DAO.UsuarioDAO;

public class UsuarioModel {

    public static final String TABELA_NOME = "usuario";

    public static final String
            COLUNA_ID = "_id",
            COLUNA_NOME  = "nome",
            COLUNA_EMAIL  = "email",
            COLUNA_USUARIO  = "usuario",
            COLUNA_CPF  = "cpf",
            COLUNA_SENHA  = "senha";

    public static final String
            CREATE_TABLE =
            "CREATE TABLE if not exists "+ TABELA_NOME
                    +"( "
                    +   COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +   COLUNA_NOME + " TEXT NOT NULL, "
                    +   COLUNA_EMAIL + " TEXT NOT NULL, "
                    +   COLUNA_USUARIO + " TEXT NOT NULL, "
                    +   COLUNA_CPF + " TEXT NOT NULL, "
                    +   COLUNA_SENHA + " TEXT NOT NULL "
                    +");";

    public UsuarioModel(){}
    public UsuarioModel(String nome, String email, String cpf, String usuario, String senha) {
        this.nome = nome;
        this.email = email;
        this.usuario = usuario;
        this.cpf = cpf;
        this.senha = senha;
    }
    public static final String
            DROP_TABLE = "drop table if exists "+TABELA_NOME;

    //-- Getters and Setters --//
    private long id;
    private String nome, email, usuario, cpf, senha, bla;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
