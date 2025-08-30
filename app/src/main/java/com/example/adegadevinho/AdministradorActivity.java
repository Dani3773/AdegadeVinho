package com.example.adegadevinho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdministradorActivity extends AppCompatActivity {

    public Button btnGerenciarVinhos;
    public Button btnGerenciarRepresentantes;
    public Button btnGerenciarClientes;
    public Button btnRelatorios;
    public Button btnSair;
    public Button btnVerPedidos;
    public Button btnCadastroMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        btnGerenciarVinhos = findViewById(R.id.btnGerenciarVinhos);
        btnGerenciarRepresentantes = findViewById(R.id.btnGerenciarRepresentantes);
        btnGerenciarClientes = findViewById(R.id.btnGerenciarClientes);
        btnCadastroMeta = findViewById(R.id.btnCadastroMeta);
        btnVerPedidos = findViewById(R.id.btnVerPedidos);
        btnRelatorios = findViewById(R.id.btnRelatorios);
        btnSair = findViewById(R.id.btnSairAdmin);

        btnGerenciarVinhos.setOnClickListener(v -> {
            Intent intent = new Intent(AdministradorActivity.this, GerenciarVinhosActivity.class);
            startActivity(intent);
        });

        btnGerenciarRepresentantes.setOnClickListener(v -> {
            Intent intent = new Intent(AdministradorActivity.this, GerenciarRepresentantesActivity.class);
            startActivity(intent);
        });

        btnGerenciarClientes.setOnClickListener(v -> {
            Intent intent = new Intent(AdministradorActivity.this, GerenciarClientesActivity.class);
            startActivity(intent);
        });

        btnVerPedidos.setOnClickListener(v -> {
            Intent intent = new Intent(AdministradorActivity.this, ListaPedidosActivity.class);
            startActivity(intent);
        });

        btnCadastroMeta.setOnClickListener(v -> {
            Intent intent = new Intent(AdministradorActivity.this, CadastroMetaActivity.class);
            startActivity(intent);
        });

        btnRelatorios.setOnClickListener(v -> {
            Intent intent = new Intent(AdministradorActivity.this, RelatoriosActivity.class);
            startActivity(intent);
        });

        btnSair.setOnClickListener(v -> {
            Intent intent = new Intent(AdministradorActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}