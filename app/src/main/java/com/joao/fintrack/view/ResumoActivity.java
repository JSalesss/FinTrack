package com.joao.fintrack.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.joao.fintrack.R;
import com.joao.fintrack.controller.DespesaController;

import java.util.ArrayList;

public class ResumoActivity extends Activity {

    private ListView listResumo;
    private DespesaController controller;
    private ArrayList<String> listaResumo;
    private ArrayList<String> categoriasResumo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);

        listResumo = findViewById(R.id.listResumo);
        controller = new DespesaController(this);

        findViewById(R.id.btnVoltar).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        carregarResumo();

        listResumo.setOnItemLongClickListener((adapterView, view, i, l) -> {
            String categoriaSelecionada = categoriasResumo.get(i);

            new AlertDialog.Builder(this)
                    .setTitle("Remover despesas")
                    .setMessage("Deseja remover TODAS as despesas da categoria \"" + categoriaSelecionada + "\"?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        int removidos = controller.removerPorCategoria(categoriaSelecionada);
                        Toast.makeText(this, removidos + " item(ns) removido(s)", Toast.LENGTH_SHORT).show();
                        carregarResumo();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();

            return true;
        });
    }

    private void carregarResumo() {
        listaResumo = new ArrayList<>();
        categoriasResumo = new ArrayList<>();

        Cursor c = controller.resumoPorCategoria();
        if (c.moveToFirst()) {
            do {
                String categoria = c.getString(0);
                double total = c.getDouble(1);
                categoriasResumo.add(categoria); // Guarda a categoria original
                listaResumo.add(categoria + " – R$ " + String.format("%.2f", total));
            } while (c.moveToNext());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaResumo);
        listResumo.setAdapter(adapter);
    }
}
