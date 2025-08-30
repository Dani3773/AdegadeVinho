package com.example.adegadevinho;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProdutosDisponiveisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VinhoAdapter vinhoAdapter;
    private VinhoDAO vinhoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos_disponiveis);

        recyclerView = findViewById(R.id.recyclerViewProdutos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vinhoDAO = new VinhoDAO(this);
        List<Vinho> listaVinhos = vinhoDAO.getTodosVinhos();

        vinhoAdapter = new VinhoAdapter(listaVinhos);
        recyclerView.setAdapter(vinhoAdapter);
    }
}

