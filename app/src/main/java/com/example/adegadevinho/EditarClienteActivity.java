package com.example.adegadevinho;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarClienteActivity extends AppCompatActivity {

    EditText edtNome, edtTelefone, edtEmail, edtCnpjCpf, edtEndereco, edtReaponsavel, edtData_Cadastro, edtBairro, edtRegiao;
    Button btnSalvar;
    Cliente cliente;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);

        edtNome = findViewById(R.id.edtNome);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail);
        edtCnpjCpf = findViewById(R.id.edtCnpjCpf);
        edtEndereco = findViewById(R.id.edtEndereco);
        edtReaponsavel = findViewById(R.id.edtResponsavel);
        edtData_Cadastro = findViewById(R.id.edtDataCadastro);
        edtBairro = findViewById(R.id.edtBairro);
        edtRegiao = findViewById(R.id.edtRegiao);
        btnSalvar = findViewById(R.id.btnSalvar);

        db = new DatabaseHelper(this);
        cliente = (Cliente) getIntent().getSerializableExtra("cliente");

        if (cliente != null) {
            edtNome.setText(cliente.getNome());
            edtTelefone.setText(cliente.getTelefone());
            edtEmail.setText(cliente.getEmail());
            edtCnpjCpf.setText(cliente.getCnpjCpf());
            edtEndereco.setText(cliente.getEndereco());
            edtReaponsavel.setText(cliente.getResponsavel());
            edtData_Cadastro.setText(cliente.getDataCadastro());
            edtBairro.setText(cliente.getBairro());
            edtRegiao.setText(cliente.getRegiao());
        }

        btnSalvar.setOnClickListener(v -> {
            cliente.setNome(edtNome.getText().toString());
            cliente.setTelefone(edtTelefone.getText().toString());
            cliente.setEmail(edtEmail.getText().toString());
            cliente.setCnpjCpf(edtCnpjCpf.getText().toString());
            cliente.setEndereco(edtEndereco.getText().toString());
            cliente.setResponsavel(edtReaponsavel.getText().toString());
            cliente.setDataCadastro(edtData_Cadastro.getText().toString());
            cliente.setBairro(edtBairro.getText().toString());
            cliente.setRegiao(edtRegiao.getText().toString());

            db.atualizarCliente(cliente);
            Toast.makeText(this, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
