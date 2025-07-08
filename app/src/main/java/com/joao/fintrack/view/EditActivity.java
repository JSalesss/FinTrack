package com.joao.fintrack.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.joao.fintrack.R;
import com.joao.fintrack.controller.DespesaController;
import com.joao.fintrack.model.Despesa;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class EditActivity extends Activity {

    private EditText edtValor, edtData;
    private Spinner spinnerCategoria;
    private Button btnSalvar;
    private DespesaController controller;
    private int id = -1;

    private static final String[] CATEGORIAS = {
            "Selecione uma opção", "Moradia", "Alimentação", "Transporte", "Saúde",
            "Educação", "Lazer", "Vestuário",
            "Contas básicas (Agua, Luz, Internet)", "Impostos", "Outros"
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        findViewById(R.id.btnVoltar).setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
        });

        edtValor = findViewById(R.id.edtValor);
        edtData = findViewById(R.id.edtData);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnSalvar = findViewById(R.id.btnSalvar);

        controller = new DespesaController(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, CATEGORIAS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        if (getIntent().hasExtra("id")) {
            id = getIntent().getIntExtra("id", -1);
            Despesa d = controller.buscarPorId(id);
            if (d != null) {
                edtValor.setText(String.valueOf(d.getValor()));
                edtData.setText(d.getData());

                for (int i = 0; i < CATEGORIAS.length; i++) {
                    if (CATEGORIAS[i].equals(d.getCategoria())) {
                        spinnerCategoria.setSelection(i);
                        break;
                    }
                }
            }
        }

        btnSalvar.setOnClickListener(v -> {
            String valorStr = edtValor.getText().toString().trim();
            String dataStr = edtData.getText().toString().trim();
            String categoria = spinnerCategoria.getSelectedItem().toString();

            if (spinnerCategoria.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Selecione uma categoria válida.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (valorStr.isEmpty()) {
                Toast.makeText(this, "Informe o valor da despesa.", Toast.LENGTH_SHORT).show();
                return;
            }

            double valor;
            try {
                valor = Double.parseDouble(valorStr);
                if (valor <= 0) {
                    Toast.makeText(this, "O valor deve ser maior que zero.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Valor inválido.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isDataValida(dataStr)) {
                Toast.makeText(this, "Data inválida. Use o formato dd/MM/yyyy.", Toast.LENGTH_SHORT).show();
                return;
            }

            Despesa d = new Despesa(id, valor, categoria, dataStr);

            if (id == -1)
                controller.inserir(d);
            else
                controller.atualizar(d);

            finish();
        });
    }

    private boolean isDataValida(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(data);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
