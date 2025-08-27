package com.joao.fintrack.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Bd extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "fintrack.bd";
    private static final int VERSAO = 3; // incrementado para recriar o BD

    public Bd(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        String sqlDespesas = "CREATE TABLE despesas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "valor REAL," +
                "categoria TEXT," +
                "data TEXT)";
        bd.execSQL(sqlDespesas);

        String sqlReceitas = "CREATE TABLE receitas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "valor REAL," +
                "categoria TEXT," +
                "data TEXT)";
        bd.execSQL(sqlReceitas);

        String sqlUsuarios = "CREATE TABLE usuarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "email TEXT UNIQUE," +
                "senha TEXT)";
        bd.execSQL(sqlUsuarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
        bd.execSQL("DROP TABLE IF EXISTS despesas");
        bd.execSQL("DROP TABLE IF EXISTS receitas"); // agora incluída
        bd.execSQL("DROP TABLE IF EXISTS usuarios");
        onCreate(bd);
    }
}
