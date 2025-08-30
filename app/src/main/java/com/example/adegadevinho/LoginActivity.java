package com.example.adegadevinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editUsuario, editSenha;
    private Button btnLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsuario = findViewById(R.id.editUsuario);
        editSenha = findViewById(R.id.editSenha);
        btnLogin = findViewById(R.id.btnLogin);
        dbHelper = new DatabaseHelper(this);

        btnLogin.setOnClickListener(v -> realizarLogin());
    }

    private void realizarLogin() {
        String usuario = editUsuario.getText().toString().trim();
        String senha = editSenha.getText().toString().trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        String tipoUsuario = dbHelper.validarLogin(usuario, senha);

        if (tipoUsuario != null) {
            Usuario usuarioLogado = dbHelper.getUsuarioPorLogin(usuario);
            if (usuarioLogado != null) {
                Sessao.setUsuarioLogado(usuarioLogado);

                SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                prefs.edit().putBoolean("logado", true).apply();

                NotificacaoUtil.enviarNotificacao(this, "Login", "Bem-vindo, " + usuarioLogado.getNome() + "!");

                Intent intent;
                if (tipoUsuario.equals("administrador")) {
                    intent = new Intent(this, AdministradorActivity.class);
                } else if (tipoUsuario.equals("representante")) {
                    intent = new Intent(this, RepresentanteActivity.class);
                } else {
                    Toast.makeText(this, "Tipo de usuário não reconhecido.", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Erro ao recuperar dados do usuário.", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Login inválido!", Toast.LENGTH_SHORT).show();
        }
    }
}
