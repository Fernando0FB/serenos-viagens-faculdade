package com.example.serenosviagens.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.example.serenosviagens.database.DBOpenHelper;
import com.example.serenosviagens.database.Models.ViagemModel;

import java.util.ArrayList;
import java.util.List;

public class ViagemDAO extends AbstractDAO {
    private final String[] colunas = {
            ViagemModel.COLUNA_ID,
            ViagemModel.COLUNA_DESCRICAO,
            ViagemModel.COLUNA_DESTINO,
            ViagemModel.COLUNA_PESSOAS,
            ViagemModel.COLUNA_DATA,
            ViagemModel.COLUNA_DATA_FIM,
            ViagemModel.COLUNA_SINCRONIZADO,
            ViagemModel.COLUNA_IDNUVEM,
            ViagemModel.COLUNA_USERID
    };

    public ViagemDAO(final Context contexto) {
        db_helper = new DBOpenHelper(contexto);
    }

    public void Insert(ViagemModel model) {

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemModel.COLUNA_DESCRICAO, model.getDescricao());
            values.put(ViagemModel.COLUNA_DESTINO, model.getDestino());
            values.put(ViagemModel.COLUNA_PESSOAS, model.getQuantPessoas());
            values.put(ViagemModel.COLUNA_DATA, model.getData());
            values.put(ViagemModel.COLUNA_DATA_FIM, model.getDataFim());
            values.put(ViagemModel.COLUNA_SINCRONIZADO, false);
            values.put(ViagemModel.COLUNA_USERID, model.getUserId());
            db.insert(ViagemModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }
    }

    public void Delete(final Long id) {
        Open();
        try {
            db.delete(ViagemModel.TABELA_NOME, ViagemModel.COLUNA_ID + " = ?", new String[]{id.toString()});
        } finally {
            Close();
        }
    }

    public void setIdNuvem(final long id, final Integer idNuvem) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemModel.COLUNA_IDNUVEM, idNuvem);
            values.put(ViagemModel.COLUNA_SINCRONIZADO, true);
            db.update(ViagemModel.TABELA_NOME, values, ViagemModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
    }

    public void setSincronizado(final long id, final Boolean isSincronizado) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemModel.COLUNA_SINCRONIZADO, isSincronizado);
            db.update(ViagemModel.TABELA_NOME, values, ViagemModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
    }

    public void update(final long id, final String descricao, final String destino, final Integer quantPessoas, final String data, final String dataFim) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(ViagemModel.COLUNA_DESCRICAO, descricao);
            values.put(ViagemModel.COLUNA_DESTINO, destino);
            values.put(ViagemModel.COLUNA_PESSOAS, quantPessoas);
            values.put(ViagemModel.COLUNA_DATA, data);
            values.put(ViagemModel.COLUNA_DATA_FIM, dataFim);
            db.update(ViagemModel.TABELA_NOME, values, ViagemModel.COLUNA_ID + " = ?", new String[]{String.valueOf(id)});
        } finally {
            Close();
        }
    }

    public ViagemModel getById (Long viagemId) {
        ViagemModel model = new ViagemModel();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            ViagemModel.TABELA_NOME,
                    colunas,
                    ViagemModel.COLUNA_ID + " in ( ? )",
                    new String[]{viagemId.toString()},
                    null,
                    null,
                    null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                model = CursorToStructure(cursor);
                break;
            }
        } finally {
            Close();
        }

        return model;
    }

    public List<ViagemModel> getByUserId(final Long usuarioId){

        List<ViagemModel> list = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            ViagemModel.TABELA_NOME,
                            colunas,
                            ViagemModel.COLUNA_USERID + " = ?",
                            new String[]{usuarioId.toString()},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(CursorToStructure(cursor));
                cursor.moveToNext();
            }
        } finally {
            Close();
        }

        return list;
    }

    public List<ViagemModel> SelectAll() {

        List<ViagemModel> lista = new ArrayList<>();

        try {
            Open();
            Cursor cursor = db.query(ViagemModel.TABELA_NOME, colunas, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                lista.add(CursorToStructure(cursor));
                cursor.moveToNext();
            }
        } finally {
            Close();
        }
        return lista;
    }

    public final ViagemModel CursorToStructure(Cursor cursor) {
        ViagemModel model = new ViagemModel();
        model.setId(cursor.getLong(0));
        model.setDescricao(cursor.getString(1));
        model.setDestino(cursor.getString(2));
        model.setQuantPessoas(cursor.getInt(3));
        model.setData(cursor.getString(4));
        model.setDataFim(cursor.getString(5));
        model.setSincronizado(Boolean.valueOf(cursor.getString(6)));
        model.setIdNuvem(cursor.getInt(7));
        model.setUserId(cursor.getLong(8));
        return model;
    }
}
