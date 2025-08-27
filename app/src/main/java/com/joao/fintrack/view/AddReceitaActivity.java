package com.joao.fintrack.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.joao.fintrack.R;
import com.joao.fintrack.controller.DespesaController;
import com.joao.fintrack.controller.ReceitaController;
import com.joao.fintrack.model.Receita;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AddReceitaActivity extends Activity {

    private EditText edtValor, edtData;
    private Spinner spinnerCategoria;
    private Button btnSalvar, btnVoltar;
    private ReceitaController receitaController;
    private DespesaController despesaController;

    private static final String[] CATEGORIAS = {
            "Selecione uma categoria", "Salário", "Freelance", "Investimentos", "Outros"
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receita);

        edtValor = findViewById(R.id.edtValor);
        edtData = findViewById(R.id.edtData);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnVoltar = findViewById(R.id.btnVoltar);

        receitaController = new ReceitaController(this);
        despesaController = new DespesaController(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, CATEGORIAS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        btnSalvar.setOnClickListener(v -> salvarReceita());
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void salvarReceita() {
        String valorStr = edtValor.getText().toString().trim();
        String dataStr = edtData.getText().toString().trim();
        String categoria = spinnerCategoria.getSelectedItem().toString();

        if (valorStr.isEmpty()) {
            Toast.makeText(this, "Informe o valor da receita.", Toast.LENGTH_SHORT).show();
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

        if (spinnerCategoria.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Selecione uma categoria válida.", Toast.LENGTH_SHORT).show();
            return;
        }

        Receita receita = new Receita(0, valor, categoria, dataStr);
        long id = receitaController.inserir(receita);
        if (id > 0) {
            Toast.makeText(this, "Receita adicionada com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar receita.", Toast.LENGTH_SHORT).show();
        }
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
