package com.joao.fintrack.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Bd extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "fintrack.bd";
    private static final int VERSAO = 1;

    public Bd(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        String sql = "CREATE TABLE despesas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "valor REAL," +
                "categoria TEXT," +
                "data TEXT)";
        bd.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("DROP TABLE IF EXISTS despesas");
        onCreate(bd);
    }
}
