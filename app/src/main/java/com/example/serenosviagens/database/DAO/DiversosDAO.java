package com.example.serenosviagens.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.serenosviagens.database.DBOpenHelper;
import com.example.serenosviagens.database.Models.DiversosModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DiversosDAO extends AbstractDAO{
    private final String[] colunas = {
            DiversosModel.COLUNA_ID,
            DiversosModel.COLUNA_DESCRICAO,
            DiversosModel.COLUNA_VALOR,
            DiversosModel.COLUNA_DESPESAID
    };

    public DiversosDAO(final Context context) { db_helper = new DBOpenHelper(context); }

    public void Insert(DiversosModel model) {

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(DiversosModel.COLUNA_DESCRICAO, model.getEntretenimento());
            values.put(DiversosModel.COLUNA_VALOR, model.getValor().toString());
            values.put(DiversosModel.COLUNA_DESPESAID, model.getDespesaId());
            db.insert(DiversosModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }
    }

    public void Delete(final Long id) {
        try {
            db.delete(DiversosModel.TABELA_NOME, DiversosModel.COLUNA_ID + " in (?)", new String[]{id.toString()});
            Open();
        } finally {
            Close();
        }
    }



    public List<DiversosModel> getAll() {

        List<DiversosModel> list = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            DiversosModel.TABELA_NOME,
                            colunas,
                            null,
                            null,
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

    public DiversosModel getByDespesaId(Long idDespesa) {
        DiversosModel diversosModel = new DiversosModel();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            DiversosModel.TABELA_NOME,
                            colunas,
                            DiversosModel.COLUNA_DESPESAID + " = ?",
                            new String[]{idDespesa.toString()},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                diversosModel = CursorToStructure(cursor);
                break;
            }
        } finally {
            Close();
        }

        return diversosModel;
    }

    public final DiversosModel CursorToStructure(Cursor cursor) {
        DiversosModel model = new DiversosModel();
        model.setId(cursor.getLong(0));
        model.setEntretenimento(cursor.getString(1));
        model.setValor(BigDecimal.valueOf(cursor.getFloat(2)));
        model.setDespesaId(cursor.getLong(3));
        return model;
    }
}
