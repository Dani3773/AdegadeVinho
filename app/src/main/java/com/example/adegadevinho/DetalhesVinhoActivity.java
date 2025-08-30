package com.example.adegadevinho;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;

public class DetalhesVinhoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRecomendacoes;
    private VinhoAdapter vinhoAdapter;
    private List<Vinho> listaVinhos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_vinho);

        recyclerViewRecomendacoes = findViewById(R.id.recyclerViewRecomendacoes);
        recyclerViewRecomendacoes.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        String nome = intent.getStringExtra("nome");
        String tipo = intent.getStringExtra("tipo");
        String descricao = intent.getStringExtra("descricao");
        double preco = intent.getDoubleExtra("preco", 0.0);
        int imagem = intent.getIntExtra("imagem", 0);
        int safra = intent.getIntExtra("safra", 0);
        String harmonizacao = intent.getStringExtra("harmonizacao");

        TextView nomeTextView = findViewById(R.id.textNomeVinho);
        TextView tipoTextView = findViewById(R.id.textTipoVinho);
        TextView descricaoTextView = findViewById(R.id.txtDescricaoVinho);
        TextView precoTextView = findViewById(R.id.txtPrecoVinho);
        ImageView imagemImageView = findViewById(R.id.imagemVinhoDetalhes);
        TextView safraTextView = findViewById(R.id.textSafraVinho);
        TextView harmonizacaoTextView = findViewById(R.id.textHarmonizacaoVinho);

        nomeTextView.setText(nome);
        tipoTextView.setText(tipo);
        descricaoTextView.setText(descricao);
        precoTextView.setText(String.format("R$ %.2f", preco));
        imagemImageView.setImageResource(imagem);
        safraTextView.setText(String.valueOf(safra));
        harmonizacaoTextView.setText(harmonizacao);

        VinhoDAO vinhoDAO = new VinhoDAO(this);
        listaVinhos = vinhoDAO.getTodosVinhos();

        List<Vinho> listaFiltrada = new ArrayList<>();
        for (Vinho vinho : listaVinhos) {
            if (!vinho.getNome().equals(nome)) {
                listaFiltrada.add(vinho);
            }
        }

        vinhoAdapter = new VinhoAdapter(listaFiltrada);
        recyclerViewRecomendacoes.setAdapter(vinhoAdapter);
    }
}
