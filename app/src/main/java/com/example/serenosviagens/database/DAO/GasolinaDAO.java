package com.example.serenosviagens.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.serenosviagens.database.DBOpenHelper;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.GasolinaModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GasolinaDAO extends AbstractDAO {
    private final String[] colunas = {
            GasolinaModel.COLUNA_ID,
            GasolinaModel.COLUNA_KM_ESTIMADOS,
            GasolinaModel.COLUNA_MEDIA_KM_L,
            GasolinaModel.COLUNA_CUSTO_MEDIO_L,
            GasolinaModel.COLUNA_QUANT_VEICULOS,
            GasolinaModel.COLUNA_DESPESAID
    };

    public GasolinaDAO(final Context context) { db_helper = new DBOpenHelper(context); }

    public void insert(GasolinaModel model) {
        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(GasolinaModel.COLUNA_KM_ESTIMADOS, model.getTotalEstimadoKM().toString());
            values.put(GasolinaModel.COLUNA_MEDIA_KM_L, model.getMediaKMLitro().toString());
            values.put(GasolinaModel.COLUNA_CUSTO_MEDIO_L, model.getCustoMedioLitro().toString());
            values.put(GasolinaModel.COLUNA_QUANT_VEICULOS, model.getTotalVeiculos());
            values.put(GasolinaModel.COLUNA_DESPESAID, model.getDespesaId());
            db.insert(GasolinaModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }
    }

    public void delete(final Long id) {
        try {
            db.delete(GasolinaModel.TABELA_NOME, GasolinaModel.COLUNA_ID + " in (?)", new String[]{id.toString()});
            Open();
        } finally {
            Close();
        }
    }

    public GasolinaModel getByDespesaId(Long idDespesa) {
        GasolinaModel gasolinaModel = new GasolinaModel();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            GasolinaModel.TABELA_NOME,
                            colunas,
                            GasolinaModel.COLUNA_DESPESAID + " = ?",
                            new String[]{idDespesa.toString()},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                gasolinaModel = CursorToStructure(cursor);
                break;
            }
        } finally {
            Close();
        }

        return gasolinaModel;
    }

    public List<GasolinaModel> getAll() {

        List<GasolinaModel> list = new ArrayList<>();

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
                cursor.moveToNext();
            }
        } finally {
            Close();
        }

        return list;
    }

    public final GasolinaModel CursorToStructure(Cursor cursor) {
        GasolinaModel model = new GasolinaModel();
        model.setId(cursor.getLong(0));
        model.setTotalEstimadoKM(BigDecimal.valueOf(cursor.getFloat(1)));
        model.setMediaKMLitro(BigDecimal.valueOf(cursor.getFloat(2)));
        model.setCustoMedioLitro(BigDecimal.valueOf(cursor.getFloat(3)));
        model.setTotalVeiculos(cursor.getInt(4));
        model.setDespesaId(cursor.getLong(5));
        return model;
    }
}
