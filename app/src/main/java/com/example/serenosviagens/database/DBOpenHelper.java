package com.example.serenosviagens.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.serenosviagens.database.Models.AereoModel;
import com.example.serenosviagens.database.Models.DespesasModel;
import com.example.serenosviagens.database.Models.DestinoModel;
import com.example.serenosviagens.database.Models.DiversosModel;
import com.example.serenosviagens.database.Models.GasolinaModel;
import com.example.serenosviagens.database.Models.HospedagemModel;
import com.example.serenosviagens.database.Models.RefeicoesModel;
import com.example.serenosviagens.database.Models.UsuarioModel;
import com.example.serenosviagens.database.Models.ViagemModel;

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String
    DATABASE_NOME = "banco.db";
    private static final int DATABASE_VERSAO = 1;

    public DBOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NOME, null, DATABASE_VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DestinoModel.CREATE_TABLE);
        db.execSQL(UsuarioModel.CREATE_TABLE);
        db.execSQL(ViagemModel.CREATE_TABLE);
        db.execSQL(DespesasModel.CREATE_TABLE);
        db.execSQL(GasolinaModel.CREATE_TABLE);
        db.execSQL(AereoModel.CREATE_TABLE);
        db.execSQL(RefeicoesModel.CREATE_TABLE);
        db.execSQL(HospedagemModel.CREATE_TABLE);
        db.execSQL(DiversosModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DestinoModel.DROP_TABLE);
            db.execSQL(UsuarioModel.DROP_TABLE);
            db.execSQL(ViagemModel.DROP_TABLE);
            db.execSQL(DespesasModel.DROP_TABLE);
            db.execSQL(GasolinaModel.DROP_TABLE);
            db.execSQL(AereoModel.DROP_TABLE);
            db.execSQL(RefeicoesModel.DROP_TABLE);
            db.execSQL(HospedagemModel.DROP_TABLE);
            db.execSQL(DiversosModel.DROP_TABLE);
        }finally {
            onCreate(db);
        }
    }
}
