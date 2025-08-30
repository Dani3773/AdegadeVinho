package com.example.adegadevinho;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adegadevinho.DatabaseHelper;
import com.example.adegadevinho.Representante;

public class EditarRepresentanteActivity extends AppCompatActivity {

    private EditText edtNome, edtTelefone, edtEmail, edtCpf, edtEndereco;
    private Button btnSalvar;

    private Representante representante;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_representante);

        edtNome = findViewById(R.id.edtNome);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail);
        edtCpf = findViewById(R.id.edtCpf);
        edtEndereco = findViewById(R.id.edtEndereco);
        btnSalvar = findViewById(R.id.btnSalvar);

        representante = (Representante) getIntent().getSerializableExtra("representante");
        db = new DatabaseHelper(this);

        if (representante != null) {
            edtNome.setText(representante.getNome());
            edtTelefone.setText(representante.getTelefone());
            edtEmail.setText(representante.getEmail());
            edtCpf.setText(representante.getCpf());
            edtEndereco.setText(representante.getEndereco());
        }

        btnSalvar.setOnClickListener(v -> {
            representante.setNome(edtNome.getText().toString());
            representante.setTelefone(edtTelefone.getText().toString());
            representante.setEmail(edtEmail.getText().toString());
            representante.setCpf(edtCpf.getText().toString());
            representante.setEndereco(edtEndereco.getText().toString());

            db.atualizarRepresentante(representante);
            Toast.makeText(this, "Representante atualizado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
