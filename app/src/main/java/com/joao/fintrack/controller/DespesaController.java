package com.joao.fintrack.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joao.fintrack.BD.Bd;
import com.joao.fintrack.model.Despesa;

import java.util.ArrayList;

public class DespesaController {
    private SQLiteDatabase db;
    private Bd helper;

    public DespesaController(Context context) {
        helper = new Bd(context);
        db = helper.getWritableDatabase();
    }

    public long inserir(Despesa d) {
        ContentValues valores = new ContentValues();
        valores.put("valor", d.getValor());
        valores.put("categoria", d.getCategoria());
        valores.put("data", d.getData());
        return db.insert("despesas", null, valores);
    }

    public int atualizar(Despesa d) {
        ContentValues valores = new ContentValues();
        valores.put("valor", d.getValor());
        valores.put("categoria", d.getCategoria());
        valores.put("data", d.getData());
        return db.update("despesas", valores, "id = ?", new String[]{String.valueOf(d.getId())});
    }

    public int deletar(int id) {
        return db.delete("despesas", "id = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Despesa> listar() {
        ArrayList<Despesa> lista = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM despesas ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                Despesa d = new Despesa();
                d.setId(cursor.getInt(0));
                d.setValor(cursor.getDouble(1));
                d.setCategoria(cursor.getString(2));
                d.setData(cursor.getString(3));
                lista.add(d);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return lista;
    }

    public int removerPorCategoria(String categoria) {
        return db.delete("despesas", "categoria = ?", new String[]{categoria});
    }

    public Cursor resumoPorCategoria() {
        return db.rawQuery("SELECT categoria, SUM(valor) FROM despesas GROUP BY categoria", null);
    }

    public Despesa buscarPorId(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM despesas WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            return new Despesa(cursor.getInt(0), cursor.getDouble(1), cursor.getString(2), cursor.getString(3));
        }
        return null;
    }
}
