package com.example.serenosviagens.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.serenosviagens.database.DBOpenHelper;
import com.example.serenosviagens.database.Models.AereoModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AereoDAO extends AbstractDAO {

    private final String[] colunas = {
            AereoModel.COLUNA_ID,
            AereoModel.COLUNA_CUSTO_POR_PESSOA,
            AereoModel.COLUNA_ALUGUEL_VEICULO,
            AereoModel.COLUNA_DESPESAID
    };

    public AereoDAO(final Context context) { db_helper = new DBOpenHelper(context); }

    public void Insert(AereoModel model) {

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(AereoModel.COLUNA_CUSTO_POR_PESSOA, model.getCustoPessoa().toString());
            values.put(AereoModel.COLUNA_ALUGUEL_VEICULO, model.getCustoAluguelVeiculo().toString());
            values.put(AereoModel.COLUNA_DESPESAID, model.getDespesaId());
            db.insert(AereoModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }
    }

    public void Delete(final Long id) {
        try {
            db.delete(AereoModel.TABELA_NOME, AereoModel.COLUNA_ID + " in (?)", new String[]{id.toString()});
            Open();
        } finally {
            Close();
        }
    }

    public List<AereoModel> getAll() {

        List<AereoModel> list = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            AereoModel.TABELA_NOME,
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

    public AereoModel getByDespesaId(Long idDespesa) {
        AereoModel aereoModel = new AereoModel();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            AereoModel.TABELA_NOME,
                            colunas,
                            AereoModel.COLUNA_DESPESAID + " = ?",
                            new String[]{idDespesa.toString()},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                aereoModel = CursorToStructure(cursor);
                break;
            }
        } finally {
            Close();
        }

        return aereoModel;
    }

    public final AereoModel CursorToStructure(Cursor cursor) {
        AereoModel model = new AereoModel();
        model.setId(cursor.getLong(0));
        model.setCustoPessoa(BigDecimal.valueOf(cursor.getFloat(1)));
        model.setCustoAluguelVeiculo(BigDecimal.valueOf(cursor.getFloat(2)));
        model.setDespesaId(cursor.getLong(3));
        return model;
    }
}