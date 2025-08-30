package com.example.adegadevinho;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class VerClientesActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listViewClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        listViewClientes = findViewById(R.id.listViewClientes);
        databaseHelper = new DatabaseHelper(this);

        carregarClientes();
    }

    private void carregarClientes() {
        List<Cliente> clientes = databaseHelper.getTodosClientes();

        if (clientes == null || clientes.isEmpty()) {
            Toast.makeText(this, "Nenhum cliente cadastrado", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> listaInfos = new ArrayList<>();
        for (Cliente c : clientes) {
            String info = "Nome: " + c.getNome() +
                    "\nTelefone: " + c.getTelefone() +
                    "\nEmail: " + c.getEmail() +
                    "\nData de cadastro: " + c.getDataCadastro() +
                    "\nEndereço: " + c.getEndereco() +
                    "\nBairro: " + c.getBairro() +
                    "\nRegião: " + c.getRegiao();
            listaInfos.add(info);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listaInfos
        );

        listViewClientes.setAdapter(adapter);
    }
}
