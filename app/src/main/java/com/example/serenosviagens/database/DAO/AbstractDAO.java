package com.example.serenosviagens.database.DAO;

import android.database.sqlite.SQLiteDatabase;
import com.example.serenosviagens.database.DBOpenHelper;

public abstract class AbstractDAO {

    protected SQLiteDatabase db;
    protected DBOpenHelper db_helper;

    protected void Open() {
        db = db_helper.getWritableDatabase();
    }

    protected void Close() {
        db_helper.close();
    }

}
