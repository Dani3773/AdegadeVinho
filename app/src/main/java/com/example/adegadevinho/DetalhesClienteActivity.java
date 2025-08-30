package com.example.adegadevinho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetalhesClienteActivity extends AppCompatActivity {

    TextView txtNome, txtTelefone, txtEmail, txtCnpjCpf, txtEndereco, txtResponsavel, txtData_Cadastro, txtBairro, txtRegiao;
    Button btnEditar, btnExcluir;
    Cliente cliente;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_cliente);

        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtEmail = findViewById(R.id.txtEmail);
        txtCnpjCpf = findViewById(R.id.txtCnpjCpf);
        txtEndereco = findViewById(R.id.txtEndereco);
        txtResponsavel = findViewById(R.id.txtResponsavel);
        txtData_Cadastro = findViewById(R.id.txtData_Cadastro);
        txtBairro = findViewById(R.id.txtBairro);
        txtRegiao = findViewById(R.id.txtRegiao);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        db = new DatabaseHelper(this);
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        if (cliente != null) {
            txtNome.setText(cliente.getNome());
            txtTelefone.setText(cliente.getTelefone());
            txtEmail.setText(cliente.getEmail());
            txtCnpjCpf.setText(cliente.getCnpjCpf());
            txtEndereco.setText(cliente.getEndereco());
            txtResponsavel.setText(cliente.getResponsavel());
            txtData_Cadastro.setText(cliente.getDataCadastro());
            txtBairro.setText(cliente.getBairro());
            txtRegiao.setText(cliente.getRegiao());
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarClienteActivity.class);
            intent.putExtra("cliente", cliente);
            startActivity(intent);
        });

        btnExcluir.setOnClickListener(v -> {
            db.excluirCliente(cliente.getId());
            finish();
        });
    }
}
