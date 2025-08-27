package com.joao.fintrack.view;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.joao.fintrack.R;
import com.joao.fintrack.controller.UsuarioController;
import com.joao.fintrack.model.Usuario;

public class CadastroActivity extends Activity {

    private EditText edtEmail, edtSenha;
    private Button btnCadastrar;
    private UsuarioController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnCadastrar = findViewById(R.id.btnCadastrarFinal);

        controller = new UsuarioController(this);

        btnCadastrar.setOnClickListener(v -> {
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!controller.validarEmail(email)) {
                Toast.makeText(this, "Email inválido. Use formato válido (ex: usuario@gmail.com).", Toast.LENGTH_SHORT).show();
                return;
            }

            long id = controller.cadastrar(new Usuario(0, email, senha));
            if (id > 0) {
                Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Erro: e-mail já cadastrado.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
