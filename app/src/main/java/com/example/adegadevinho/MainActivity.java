package com.example.adegadevinho;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewVinhos;
    private VinhoAdapter vinhoAdapter;
    private List<Vinho> listaVinhos = new ArrayList<>();
    private EditText editBuscarVinho;

    private Button btnFiltroTodos, btnFiltroTinto, btnFiltroBranco, btnFiltroRose, btnFiltroEspumante;
    private Button btnContato;

    private String filtroAtual = "Todos";
    private VinhoDAO vinhoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences loginPrefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        if (!loginPrefs.getBoolean("logado", false)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        ImageButton btnMenu = findViewById(R.id.btnMenu);
        editBuscarVinho = findViewById(R.id.editBuscarVinho);
        recyclerViewVinhos = findViewById(R.id.recyclerViewVinhos);
        btnContato = findViewById(R.id.btnContato);

        btnFiltroTodos = findViewById(R.id.btnFiltroTodos);
        btnFiltroTinto = findViewById(R.id.btnFiltroTinto);
        btnFiltroBranco = findViewById(R.id.btnFiltroBranco);
        btnFiltroRose = findViewById(R.id.btnFiltroRose);
        btnFiltroEspumante = findViewById(R.id.btnFiltroEspumante);

        recyclerViewVinhos.setLayoutManager(new GridLayoutManager(this, 2));
        vinhoDAO = new VinhoDAO(this);

        if (vinhoDAO.getTodosVinhos().isEmpty()) {
            inserirVinhosExemplo();
        }

        carregarVinhos();

        editBuscarVinho.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrar();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        btnContato.setOnClickListener(v -> abrirTelaContato());

        btnMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        btnFiltroTodos.setOnClickListener(v -> {
            filtroAtual = "Todos";
            filtrar();
        });

        btnFiltroTinto.setOnClickListener(v -> {
            filtroAtual = "Tinto";
            filtrar();
        });

        btnFiltroBranco.setOnClickListener(v -> {
            filtroAtual = "Branco";
            filtrar();
        });

        btnFiltroRose.setOnClickListener(v -> {
            filtroAtual = "Rosé";
            filtrar();
        });

        btnFiltroEspumante.setOnClickListener(v -> {
            filtroAtual = "Espumante";
            filtrar();
        });
    }

    private void abrirTelaContato() {
        Intent intent = new Intent(MainActivity.this, ContatoActivity.class);
        startActivity(intent);
    }

    private void inserirVinhosExemplo() {
        vinhoDAO.inserirVinho(new Vinho(
                "Cabernet Sauvignon",
                "Tinto",
                R.drawable.carbenet_sauvignon,
                "Um vinho tinto encorpado, com notas de frutas negras, como amora e cassis, taninos marcantes e envelhecimento em barrica de carvalho. Ideal para carnes vermelhas e queijos duros.",
                89.90,
                2018,
                "Carnes vermelhas, queijos duros"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Chardonnay",
                "Branco",
                R.drawable.chardonnay,
                "Vinho branco de corpo médio a encorpado, com aromas de frutas tropicais como abacaxi e manga, além de notas amanteigadas quando envelhecido em carvalho. Excelente com peixes e massas com molhos leves.",
                75.50,
                2021,
                "Peixes, massas com molhos leves"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Pinot Noir",
                "Tinto",
                R.drawable.pinot_noir,
                "Leve e elegante, com aromas de cereja, framboesa e toques terrosos. Taninos suaves e acidez equilibrada, ideal para acompanhar pratos leves como aves e cogumelos.",
                94.00,
                2020,
                "Aves, cogumelos"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Espumante Brut",
                "Espumante",
                R.drawable.espumante_brut,
                "Refrescante e seco, com bolhas finas e persistentes. Apresenta aromas de maçã verde, limão e toques de pão tostado. Ideal como aperitivo ou para celebrações.",
                64.90,
                2022,
                "Aperitivos, celebrações"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Rosé Provence",
                "Rosé",
                R.drawable.rose_provence,
                "Delicado e fresco, com notas de morango, framboesa e flores. Possui acidez agradável e final seco. Perfeito para dias quentes, frutos do mar e saladas.",
                79.00,
                2021,
                "Frutos do mar, saladas"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Merlot",
                "Tinto",
                R.drawable.merlot,
                "Vinho macio e frutado, com notas de ameixa, cereja e chocolate. Corpo médio e taninos suaves. Combina bem com massas ao molho vermelho e carnes grelhadas.",
                68.40,
                2019,
                "Massas com molho vermelho, carnes grelhadas"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Sauvignon Blanc",
                "Branco",
                R.drawable.sauvignon_blanc,
                "Branco refrescante com aromas de maracujá, limão e grama cortada. Alta acidez, ideal para pratos com frutos do mar, saladas e comidas leves.",
                71.30,
                2022,
                "Frutos do mar, saladas, comidas leves"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Syrah",
                "Tinto",
                R.drawable.syrah,
                "Encorpado e intenso, com notas de frutas escuras, pimenta preta e defumado. Ideal para carnes de caça e pratos condimentados.",
                82.75,
                2020,
                "Carnes de caça, pratos condimentados"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Prosecco",
                "Espumante",
                R.drawable.prosecco,
                "Leve, frutado e refrescante, com aromas de maçã verde e pera. Ótimo como aperitivo ou para acompanhar entradas leves.",
                59.90,
                2023,
                "Aperitivos, entradas leves"
        ));

        vinhoDAO.inserirVinho(new Vinho(
                "Rosé Syrah",
                "Rosé",
                R.drawable.rose_sirah,
                "Rosé vibrante com notas de frutas vermelhas frescas, boa acidez e final seco. Excelente para churrascos, culinária mediterrânea e dias quentes.",
                77.60,
                2022,
                "Churrascos, culinária mediterrânea"
        ));
    }

    private void carregarVinhos() {
        listaVinhos = vinhoDAO.getTodosVinhos();
        vinhoAdapter = new VinhoAdapter(listaVinhos);
        recyclerViewVinhos.setAdapter(vinhoAdapter);
    }

    private void filtrar() {
        String textoBusca = editBuscarVinho.getText().toString().trim().toLowerCase();
        List<Vinho> filtrados = new ArrayList<>();

        for (Vinho vinho : listaVinhos) {
            boolean nomeCorresponde = vinho.getNome().toLowerCase().contains(textoBusca);
            boolean tipoCorresponde = filtroAtual.equals("Todos") || vinho.getTipo().equalsIgnoreCase(filtroAtual);

            if (nomeCorresponde && tipoCorresponde) {
                filtrados.add(vinho);
            }
        }

        vinhoAdapter.atualizarLista(filtrados);
    }
}
