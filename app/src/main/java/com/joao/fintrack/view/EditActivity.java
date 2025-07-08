package com.joao.fintrack.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.joao.fintrack.R;
import com.joao.fintrack.controller.DespesaController;
import com.joao.fintrack.model.Despesa;

public class EditActivity extends Activity {

    private EditText edtValor, edtCategoria, edtData;
    private Button btnSalvar;
    private DespesaController controller;
    private int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtValor = findViewById(R.id.edtValor);
        edtCategoria = findViewById(R.id.edtCategoria);
        edtData = findViewById(R.id.edtData);
        btnSalvar = findViewById(R.id.btnSalvar);

        controller = new DespesaController(this);

        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", -1);
            Despesa d = controller.buscarPorId(id);
            if (d != null) {
                edtValor.setText(String.valueOf(d.getValor()));
                edtCategoria.setText(d.getCategoria());
                edtData.setText(d.getData());
            }
        }

        btnSalvar.setOnClickListener(v -> {
            Despesa d = new Despesa(id,
                    Double.parseDouble(edtValor.getText().toString()),
                    edtCategoria.getText().toString(),
                    edtData.getText().toString());

            if (id == -1)
                controller.inserir(d);
            else
                controller.atualizar(d);

            finish();
        });
    }
}
