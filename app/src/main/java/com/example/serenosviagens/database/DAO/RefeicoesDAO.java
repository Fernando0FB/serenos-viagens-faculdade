package com.example.serenosviagens.database.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.example.serenosviagens.database.DBOpenHelper;
import com.example.serenosviagens.database.Models.RefeicoesModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RefeicoesDAO extends AbstractDAO{

    private final String[] colunas = {
            RefeicoesModel.COLUNA_ID,
            RefeicoesModel.COLUNA_CUSTO_POR_REFEICAO,
            RefeicoesModel.COLUNA_QUANTIDADE_REFEICAO,
            RefeicoesModel.COLUNA_DESPESAID
    };

    public RefeicoesDAO(final Context context) { db_helper = new DBOpenHelper(context); }

    public void Insert(RefeicoesModel model) {

        try {
            Open();

            ContentValues values = new ContentValues();
            values.put(RefeicoesModel.COLUNA_CUSTO_POR_REFEICAO, model.getCustoRefeicao().toString());
            values.put(RefeicoesModel.COLUNA_QUANTIDADE_REFEICAO, model.getRefeicoesDia().toString());
            values.put(RefeicoesModel.COLUNA_DESPESAID, model.getDespesaId());
            db.insert(RefeicoesModel.TABELA_NOME, null, values);
        } finally {
            Close();
        }
    }

    public void Delete(final Long id) {
        try {
            db.delete(RefeicoesModel.TABELA_NOME, RefeicoesModel.COLUNA_ID + " in (?)", new String[]{id.toString()});
            Open();
        } finally {
            Close();
        }
    }

    public List<RefeicoesModel> getAll() {

        List<RefeicoesModel> list = new ArrayList<>();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            RefeicoesModel.TABELA_NOME,
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

    public RefeicoesModel getByDespesaId(Long idDespesa) {
        RefeicoesModel refeicoesModel = new RefeicoesModel();

        try {
            Open();

            Cursor cursor = db.query
                    (
                            RefeicoesModel.TABELA_NOME,
                            colunas,
                            RefeicoesModel.COLUNA_DESPESAID + " = ?",
                            new String[]{idDespesa.toString()},
                            null,
                            null,
                            null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                refeicoesModel = CursorToStructure(cursor);
                break;
            }
        } finally {
            Close();
        }

        return refeicoesModel;
    }


    public final RefeicoesModel CursorToStructure(Cursor cursor) {
        RefeicoesModel model = new RefeicoesModel();
        model.setId(cursor.getLong(0));
        model.setCustoRefeicao(BigDecimal.valueOf(cursor.getFloat(1)));
        model.setRefeicoesDia(cursor.getInt(2));
        model.setDespesaId(cursor.getLong(3));
        return model;
    }
}
