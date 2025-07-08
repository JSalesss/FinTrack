package com.joao.fintrack.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.joao.fintrack.R;
import com.joao.fintrack.controller.DespesaController;
import com.joao.fintrack.model.Despesa;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView listView;
    private TextView textResumo;
    private DespesaController controller;
    private ArrayList<Despesa> lista;
    private ArrayList<String> nomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listDespesas);
        textResumo = findViewById(R.id.textResumo);
        controller = new DespesaController(this);

        findViewById(R.id.btnAdicionar).setOnClickListener(v -> {
            startActivity(new Intent(this, EditActivity.class));
        });

        findViewById(R.id.btnResumo).setOnClickListener(v -> {
            startActivity(new Intent(this, ResumoActivity.class));
        });


        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Despesa d = lista.get(i);
            Intent it = new Intent(this, EditActivity.class);
            it.putExtra("id", d.getId());
            startActivity(it);
        });

        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Despesa d = lista.get(i);
            new AlertDialog.Builder(this)
                    .setTitle("Remover Despesa")
                    .setMessage("Deseja remover a despesa \"" + d.getCategoria() + "\" no valor de R$" + d.getValor() + "?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        controller.deletar(d.getId());
                        carregarLista();
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    private void carregarLista() {
        lista = controller.listar();
        nomes = new ArrayList<>();
        double total = 0;

        for (Despesa d : lista) {
            nomes.add(d.getCategoria() + " - R$ " + String.format("%.2f", d.getValor()) + " (" + d.getData() + ")");
            total += d.getValor();
        }

        textResumo.setText("Resumo Mensal: R$" + String.format("%.2f", total));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomes);
        listView.setAdapter(adapter);
    }
}
