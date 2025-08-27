package com.joao.fintrack.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.joao.fintrack.BD.Bd;
import com.joao.fintrack.model.Usuario;

public class UsuarioController {
    private SQLiteDatabase db;
    private Bd helper;

    public UsuarioController(Context context) {
        helper = new Bd(context);
        db = helper.getWritableDatabase();
    }

    public boolean validarEmail(String email) {
        if (email == null) return false;
        email = email.trim();

        if (!email.contains("@") || !email.endsWith(".com")) {
            return false;
        }

        int atPos = email.indexOf('@');
        return atPos > 0;
    }

    public long cadastrar(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put("email", usuario.getEmail());
        valores.put("senha", usuario.getSenha());
        return db.insert("usuarios", null, valores);
    }

    public boolean autenticar(String email, String senha) {
        Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE email = ? AND senha = ?",
                new String[]{email, senha});
        boolean autenticado = cursor.moveToFirst();
        cursor.close();
        return autenticado;
    }
}
