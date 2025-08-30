package com.example.adegadevinho;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RepresentanteActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listViewEmissoes;
    private Button btnSair, btnVisualizarProdutos, btnEmitirPedido, btnVisualizarClientes, btnVerMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representante);

        listViewEmissoes = findViewById(R.id.listViewEmissoes);
        btnEmitirPedido = findViewById(R.id.btnEmitirPedido);
        btnSair = findViewById(R.id.btnSair);
        btnVerMetas = findViewById(R.id.btnVerMetas);
        btnVisualizarProdutos = findViewById(R.id.btnVisualizarProdutos);
        btnVisualizarClientes = findViewById(R.id.btnVisualizarClientes);

        databaseHelper = new DatabaseHelper(this);

        Usuario usuario = Sessao.getUsuarioLogado();
        if (usuario != null) {
            int representanteId = usuario.getId();
            Log.d("DEBUG_REP", "Representante logado: " + usuario.getId() + " - " + usuario.getNome());
            carregarEmissoes(representanteId);
        } else {
            Log.e("DEBUG_REP", "Nenhum representante logado encontrado na sessão.");
        }

        btnEmitirPedido.setOnClickListener(v -> {
            startActivity(new Intent(this, EmissaoPedidoActivity.class));
        });

        btnVisualizarProdutos.setOnClickListener(v -> {
            startActivity(new Intent(this, ProdutosDisponiveisActivity.class));
        });

        btnVisualizarClientes.setOnClickListener(v -> {
            startActivity(new Intent(this, VerClientesActivity.class));
        });

        btnVerMetas.setOnClickListener(v -> {
            startActivity(new Intent(this, MetasActivity.class));
        });

        btnSair.setOnClickListener(v -> {
            Sessao.limparSessao();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    private void carregarEmissoes(int representanteId) {
        List<Pedido> pedidos = databaseHelper.getRelatorioPedidos("", "", -1, "", representanteId);
        EmissaoAdapter adapter = new EmissaoAdapter(this, pedidos, databaseHelper);
        listViewEmissoes.setAdapter(adapter);
    }
}
