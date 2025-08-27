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
import com.joao.fintrack.controller.ReceitaController;
import com.joao.fintrack.model.Despesa;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ListView listViewDespesas;
    private TextView textSaldo;
    private DespesaController despesaController;
    private ReceitaController receitaController;
    private ArrayList<Despesa> listaDespesas;
    private ArrayList<String> nomesDespesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewDespesas = findViewById(R.id.listDespesas);
        textSaldo = findViewById(R.id.textResumo);

        despesaController = new DespesaController(this);
        receitaController = new ReceitaController(this);


        findViewById(R.id.btnAdicionar).setOnClickListener(v -> {
            startActivity(new Intent(this, EditActivity.class));
        });


        findViewById(R.id.btnAdicionarReceita).setOnClickListener(v -> {
            startActivity(new Intent(this, AddReceitaActivity.class));
        });


        findViewById(R.id.btnResumo).setOnClickListener(v -> {
            startActivity(new Intent(this, ResumoActivity.class));
        });


        listViewDespesas.setOnItemClickListener((adapterView, view, i, l) -> {
            Despesa d = listaDespesas.get(i);
            Intent it = new Intent(this, EditActivity.class);
            it.putExtra("id", d.getId());
            startActivity(it);
        });


        listViewDespesas.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Despesa d = listaDespesas.get(i);
            new AlertDialog.Builder(this)
                    .setTitle("Remover Despesa")
                    .setMessage("Deseja remover a despesa \"" + d.getCategoria() + "\" no valor de R$" + d.getValor() + "?")
                    .setPositiveButton("Sim", (dialog, which) -> {
                        despesaController.deletar(d.getId());
                        carregarLista();
                        atualizarSaldo();
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
        atualizarSaldo();
    }


    private void carregarLista() {
        listaDespesas = despesaController.listar();
        nomesDespesas = new ArrayList<>();
        for (Despesa d : listaDespesas) {
            nomesDespesas.add(d.getCategoria() + " - R$ " + String.format("%.2f", d.getValor()) + " (" + d.getData() + ")");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nomesDespesas);
        listViewDespesas.setAdapter(adapter);
    }


    private void atualizarSaldo() {
        double totalReceitas = receitaController.totalReceitas();
        double totalDespesas = 0;
        for (Despesa d : despesaController.listar()) {
            totalDespesas += d.getValor();
        }
        double saldo = totalReceitas - totalDespesas;
        textSaldo.setText("Saldo: R$ " + String.format("%.2f", saldo));
    }
}
