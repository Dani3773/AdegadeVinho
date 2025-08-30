package com.example.adegadevinho;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GerenciarRepresentantesActivity extends AppCompatActivity {

    Button btnAdicionarRepresentante, btnVoltarAdmin;
    EditText edtBuscarRepresentante;
    ListView listViewRepresentantes;

    RepresentanteAdapter adapter;
    List<Representante> listaOriginal;
    List<Representante> listaFiltrada;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenciar_representantes);

        btnAdicionarRepresentante = findViewById(R.id.btnAdicionarRepresentante);
        btnVoltarAdmin = findViewById(R.id.btnVoltarAdmin);
        edtBuscarRepresentante = findViewById(R.id.edtBuscarRepresentante);
        listViewRepresentantes = findViewById(R.id.listViewRepresentantes);

        dbHelper = new DatabaseHelper(this);
        listaOriginal = new ArrayList<>();
        listaFiltrada = new ArrayList<>();

        adapter = new RepresentanteAdapter(this, listaFiltrada);
        listViewRepresentantes.setAdapter(adapter);

        btnAdicionarRepresentante.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdicionarRepresentanteActivity.class);
            startActivity(intent);
        });

        btnVoltarAdmin.setOnClickListener(v -> finish());

        edtBuscarRepresentante.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarRepresentantes(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        listViewRepresentantes.setOnItemClickListener((parent, view, position, id) -> {
            Representante repSelecionado = listaFiltrada.get(position);
            Intent intent = new Intent(this, DetalhesRepresentanteActivity.class);
            intent.putExtra("representante", repSelecionado);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarRepresentantes();
    }

    private void carregarRepresentantes() {
        listaOriginal = dbHelper.getTodosRepresentantes();
        listaFiltrada.clear();
        listaFiltrada.addAll(listaOriginal);
        adapter.notifyDataSetChanged();
    }

    private void filtrarRepresentantes(String texto) {
        listaFiltrada.clear();
        for (Representante rep : listaOriginal) {
            if (rep.getNome().toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(rep);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
