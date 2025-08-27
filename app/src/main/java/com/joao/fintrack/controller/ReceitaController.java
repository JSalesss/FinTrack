package com.joao.fintrack.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joao.fintrack.BD.Bd;
import com.joao.fintrack.model.Receita;
import java.util.ArrayList;

public class ReceitaController {
    private SQLiteDatabase db;
    private Bd helper;

    public ReceitaController(Context context) {
        helper = new Bd(context);
        db = helper.getWritableDatabase();
    }

    public long inserir(Receita r) {
        ContentValues valores = new ContentValues();
        valores.put("valor", r.getValor());
        valores.put("categoria", r.getCategoria());
        valores.put("data", r.getData());
        return db.insert("receitas", null, valores);
    }

    public int atualizar(Receita r) {
        ContentValues valores = new ContentValues();
        valores.put("valor", r.getValor());
        valores.put("categoria", r.getCategoria());
        valores.put("data", r.getData());
        return db.update("receitas", valores, "id = ?", new String[]{String.valueOf(r.getId())});
    }

    public int deletar(int id) {
        return db.delete("receitas", "id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Receita> listar() {
        ArrayList<Receita> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM receitas ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Receita r = new Receita();
                r.setId(cursor.getInt(0));
                r.setValor(cursor.getDouble(1));
                r.setCategoria(cursor.getString(2));
                r.setData(cursor.getString(3));
                lista.add(r);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public double totalReceitas() {
        Cursor cursor = db.rawQuery("SELECT SUM(valor) FROM receitas", null);
        double total = 0;
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }
}
