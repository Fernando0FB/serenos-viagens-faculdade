package com.example.serenosviagens.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.widget.Toast;

import com.example.serenosviagens.database.DBOpenHelper;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.ViagemModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DespesasDAO extends AbstractDAO {

    private final String[] colunas = {
            DespesasModel.COLUNA_ID,
            DespesasModel.COLUNA_DESCRICAO,
            DespesasModel.COLUNA_TIPO,
            DespesasModel.COLUNA_VALOR,
            DespesasModel.COLUNA_ADICIONAR_TOTAL,
            DespesasModel.COLUNA_VIAGEMID
    };

    public DespesasDAO(final Context context) {
        db_helper = new DBOpenHelper(context);
    }

    public Long insert(DespesasModel model) {
        Long id = -1L;
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(DespesasModel.COLUNA_DESCRICAO, model.getDescricao());
            values.put(DespesasModel.COLUNA_TIPO, model.getTipo());
            values.put(DespesasModel.COLUNA_VALOR, model.getValor().toString());
            values.put(DespesasModel.COLUNA_VIAGEMID, model.getViagemId());
            values.put(DespesasModel.COLUNA_ADICIONAR_TOTAL, model.getAdcTotal());
            id = db.insert(DespesasModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }

        return id;
    }

    public void Delete(final Long id) {
        try {
            Open();
            db.delete(DespesasModel.TABELA_NOME, DespesasModel.COLUNA_ID + " = ?", new String[]{id.toString()});
        } finally {
            Close();
        }
    }

    public List<DespesasModel> getAll() {

        List<DespesasModel> list = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            DespesasModel.TABELA_NOME,
                            colunas,
                            null,
                            null,
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(CursorToStructure(cursor));
                break;
            }
        } finally {
            Close();
        }

        return list;
    }

    public DespesasModel getById(final Long id) {

        DespesasModel despesaModel = new DespesasModel();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            DespesasModel.TABELA_NOME,
                            colunas,
                            DespesasModel.COLUNA_ID + " = ?",
                            new String[]{id.toString()},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                despesaModel = CursorToStructure(cursor);
                break;
            }
        } finally {
            Close();
        }

        return despesaModel;
    }

    public List<DespesasModel> getByViagemId(final Long viagemId){
        List<DespesasModel> list = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            DespesasModel.TABELA_NOME,
                            colunas,
                            DespesasModel.COLUNA_VIAGEMID + " = ?",
                            new String[]{viagemId.toString()},
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

    public final DespesasModel CursorToStructure(Cursor cursor) {
        DespesasModel model = new DespesasModel();
        model.setId(cursor.getLong(0));
        model.setDescricao(cursor.getString(1));
        model.setTipo(cursor.getString(2));
        model.setValor(BigDecimal.valueOf(cursor.getFloat(3)));
        model.setAdcTotal(cursor.getString(4));
        model.setViagemId(cursor.getLong(5));
        return model;
    }
}
