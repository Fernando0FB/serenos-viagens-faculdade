package com.example.serenosviagens.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.serenosviagens.database.DBOpenHelper;
import com.example.serenosviagens.database.Models.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends AbstractDAO {

    public UsuarioDAO(Context context) {
        db_helper = new DBOpenHelper(context);
    }
    private final String[] colunas = {
            UsuarioModel.COLUNA_ID,
            UsuarioModel.COLUNA_USUARIO,
            UsuarioModel.COLUNA_SENHA,
            UsuarioModel.COLUNA_EMAIL,
            UsuarioModel.COLUNA_CPF,
            UsuarioModel.COLUNA_NOME
    };

    public void Insert(UsuarioModel model) {
        try{
            Open();

            ContentValues values = new ContentValues();
            values.put(UsuarioModel.COLUNA_USUARIO, model.getUsuario());
            values.put(UsuarioModel.COLUNA_SENHA, model.getSenha());
            values.put(UsuarioModel.COLUNA_EMAIL, model.getEmail());
            values.put(UsuarioModel.COLUNA_CPF, model.getCpf());
            values.put(UsuarioModel.COLUNA_NOME, model.getNome());
            db.insert(UsuarioModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }
    }

    public void Delete(final String usuario) {
        try{
            Open();
            db.delete(UsuarioModel.TABELA_NOME, UsuarioModel.COLUNA_USUARIO, null);
        } finally {
            Close();
        }
    }

    public void DeleteAll() {
        Open();
        db.delete(UsuarioModel.TABELA_NOME, null, null);
        Close();
    }

    public int Update(final String usuario) {

        int linhasAfetadas = 0;

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(UsuarioModel.COLUNA_USUARIO, usuario);
            linhasAfetadas = db.update(UsuarioModel.TABELA_NOME, values, UsuarioModel.COLUNA_USUARIO + " = ?", new String[]{usuario});
        } finally {
            Close();
        }

        return linhasAfetadas;
    }

    public UsuarioModel Select(final String usuario, final String senha) {

        UsuarioModel model = null;

        try {
            Open();

            // select * from tb_usuario where usuario = ? and senha = ?
            Cursor cursor = db.query
                    (
                            UsuarioModel.TABELA_NOME,
                            colunas,
                            UsuarioModel.COLUNA_USUARIO + " = ? ",
                            new String[]{usuario},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model = CursorToStructure(cursor);
                break;
            }
        }
        finally {
            Close();
        }

        return model;
    }

    public List<UsuarioModel> SelectAll() {

        List<UsuarioModel> lista = new ArrayList<>();

        try {
            Open();
            Cursor cursor = db.query(UsuarioModel.TABELA_NOME, colunas, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                lista.add(CursorToStructure(cursor));
                cursor.moveToNext();
            }
        }
        finally {
            Close();
        }
        return lista;
    }

    public final UsuarioModel CursorToStructure(Cursor cursor) {
        UsuarioModel model = new UsuarioModel();
        model.setId(cursor.getLong(0));
        model.setUsuario(cursor.getString(1));
        model.setSenha(cursor.getString(2));
        model.setEmail(cursor.getString(3));
        model.setCpf(cursor.getString(4));
        model.setNome(cursor.getString(5));
        return model;
    }
}
