package com.example.serenosviagens.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.serenosviagens.database.DBOpenHelper;
import com.example.serenosviagens.database.Models.HospedagemModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HospedagemDAO extends AbstractDAO{
    private final String[] colunas = {
            HospedagemModel.COLUNA_ID,
            HospedagemModel.COLUNA_CUSTO_POR_NOITE,
            HospedagemModel.COLUNA_TOTAL_NOITES,
            HospedagemModel.COLUNA_TOTAL_QUARTOS,
            HospedagemModel.COLUNA_DESPESAID
    };

    public HospedagemDAO(final Context context) { db_helper = new DBOpenHelper(context); }

    public void Insert(HospedagemModel model) {

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(HospedagemModel.COLUNA_CUSTO_POR_NOITE, model.getCustoMedioNoite().toString());
            values.put(HospedagemModel.COLUNA_TOTAL_NOITES, model.getTotalNoite().toString());
            values.put(HospedagemModel.COLUNA_TOTAL_QUARTOS, model.getTotalQuartos().toString());
            values.put(HospedagemModel.COLUNA_DESPESAID, model.getDespesaId());
            db.insert(HospedagemModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }
    }

    public void Delete(final Long id) {
        try {
            db.delete(HospedagemModel.TABELA_NOME, HospedagemModel.COLUNA_ID + " in (?)", new String[]{id.toString()});
            Open();
        } finally {
            Close();
        }
    }

    public List<HospedagemModel> getAll() {

        List<HospedagemModel> list = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            HospedagemModel.TABELA_NOME,
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

    public HospedagemModel getByDespesaId(Long idDespesa) {
        HospedagemModel hospedagemModel = new HospedagemModel();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            HospedagemModel.TABELA_NOME,
                            colunas,
                            HospedagemModel.COLUNA_DESPESAID + " = ?",
                            new String[]{idDespesa.toString()},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                hospedagemModel = CursorToStructure(cursor);
                break;
            }
        } finally {
            Close();
        }

        return hospedagemModel;
    }

    public final HospedagemModel CursorToStructure(Cursor cursor) {
        HospedagemModel model = new HospedagemModel();
        model.setId(cursor.getLong(0));
        model.setCustoMedioNoite(BigDecimal.valueOf(cursor.getFloat(1)));
        model.setTotalNoite(cursor.getInt(2));
        model.setTotalQuartos(cursor.getInt(3));
        model.setDespesaId(cursor.getLong(4));
        return model;
    }
}
