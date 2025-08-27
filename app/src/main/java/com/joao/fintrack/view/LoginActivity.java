package com.joao.fintrack.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.joao.fintrack.R;
import com.joao.fintrack.controller.UsuarioController;

public class LoginActivity extends Activity {

    private EditText edtEmail, edtSenha;
    private Button btnEntrar, btnCadastrar;
    private UsuarioController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        controller = new UsuarioController(this);

        btnEntrar.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (controller.autenticar(email, senha)) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Credenciais inválidas", Toast.LENGTH_SHORT).show();
            }
        });

        btnCadastrar.setOnClickListener(v -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });
    }
}
