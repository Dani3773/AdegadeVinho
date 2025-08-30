package com.example.adegadevinho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.adegadevinho.DatabaseHelper;
import com.example.adegadevinho.Representante;

import androidx.appcompat.app.AppCompatActivity;

public class DetalhesRepresentanteActivity extends AppCompatActivity {

    private TextView txtNome, txtTelefone, txtEmail, txtCpf, txtEndereco;
    private Button btnEditar, btnExcluir;
    private Representante representante;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_representante);

        txtNome = findViewById(R.id.txtNome);
        txtTelefone = findViewById(R.id.txtTelefone);
        txtEmail = findViewById(R.id.txtEmail);
        txtCpf = findViewById(R.id.txtCpf);
        txtEndereco = findViewById(R.id.txtEndereco);
        btnEditar = findViewById(R.id.btnEditar);
        btnExcluir = findViewById(R.id.btnExcluir);

        representante = (Representante) getIntent().getSerializableExtra("representante");

        db = new DatabaseHelper(this);

        if (representante != null) {
            txtNome.setText(representante.getNome());
            txtTelefone.setText(representante.getTelefone());
            txtEmail.setText(representante.getEmail());
            txtCpf.setText(representante.getCpf());
            txtEndereco.setText(representante.getEndereco());
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(DetalhesRepresentanteActivity.this, EditarRepresentanteActivity.class);
            intent.putExtra("representante", representante);
            startActivity(intent);
        });

        btnExcluir.setOnClickListener(v -> {
            db.excluirRepresentante(representante.getId());
            finish();
        });
    }
}
