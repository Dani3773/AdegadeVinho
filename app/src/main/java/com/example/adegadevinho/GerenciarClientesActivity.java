package com.example.adegadevinho;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GerenciarClientesActivity extends AppCompatActivity {

    Button btnAdicionarCliente, btnVoltarAdmin;
    EditText edtBuscarCliente;
    ListView listViewClientes;

    ClienteAdapter adapter;
    List<Cliente> listaOriginal;
    List<Cliente> listaFiltrada;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_clientes);

        btnAdicionarCliente = findViewById(R.id.btnAdicionarCliente);
        btnVoltarAdmin = findViewById(R.id.btnVoltarAdmin);
        edtBuscarCliente = findViewById(R.id.edtBuscarCliente);
        listViewClientes = findViewById(R.id.listViewClientes);

        dbHelper = new DatabaseHelper(this);
        listaOriginal = new ArrayList<>();
        listaFiltrada = new ArrayList<>();

        adapter = new ClienteAdapter(this, listaFiltrada);
        listViewClientes.setAdapter(adapter);

        btnAdicionarCliente.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdicionarClienteActivity.class);
            startActivity(intent);
        });

        btnVoltarAdmin.setOnClickListener(v -> finish());

        edtBuscarCliente.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        listViewClientes.setOnItemClickListener((parent, view, position, id) -> {
            Cliente clienteSelecionado = (Cliente) adapter.getItem(position);
            Intent intent = new Intent(this, DetalhesClienteActivity.class);
            intent.putExtra("cliente", clienteSelecionado);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarClientes();
    }

    private void carregarClientes() {
        listaOriginal = dbHelper.getTodosClientes();
        listaFiltrada.clear();
        listaFiltrada.addAll(listaOriginal);
        adapter.notifyDataSetChanged();
    }
}
