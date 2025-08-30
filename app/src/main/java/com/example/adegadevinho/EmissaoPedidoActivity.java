package com.example.adegadevinho;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.*;

public class EmissaoPedidoActivity extends AppCompatActivity {

    private EditText editNomeCliente, editNomeVinho, editQuantidade;
    private Spinner spinnerFormaPagamento;
    private ListView listaItensPedido;
    private Button btnAdicionarItem, btnFinalizarPedido;

    private DatabaseHelper db;
    private List<ItemPedido> itensPedido = new ArrayList<>();
    private ArrayAdapter<String> itensAdapter;
    private String[] statusArray = {"Pix", "Dinheiro", "Cartão"};

    private List<Cliente> clientes;
    private List<Vinho> vinhos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emissao_pedido);

        db = new DatabaseHelper(this);

        editNomeCliente = findViewById(R.id.editNomeCliente);
        editNomeVinho = findViewById(R.id.editNomeVinho);
        editQuantidade = findViewById(R.id.editQuantidade);
        spinnerFormaPagamento = findViewById(R.id.spinnerFormaPagamento);
        listaItensPedido = findViewById(R.id.listaItensPedido);
        btnAdicionarItem = findViewById(R.id.btnAdicionarItem);
        btnFinalizarPedido = findViewById(R.id.btnFinalizarPedido);

        clientes = db.getTodosClientes();
        vinhos = db.getTodosVinhos();

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item_pagamento,
                statusArray
        );
        statusAdapter.setDropDownViewResource(R.layout.spinner_item_pagamento);
        spinnerFormaPagamento.setAdapter(statusAdapter);

        configurarLista();

        btnAdicionarItem.setOnClickListener(v -> adicionarItem());
        btnFinalizarPedido.setOnClickListener(v -> finalizarPedido());
    }

    private void configurarLista() {
        itensAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                new ArrayList<>());
        listaItensPedido.setAdapter(itensAdapter);
    }

    private void adicionarItem() {
        String nomeVinho = editNomeVinho.getText().toString().trim();
        String quantidadeStr = editQuantidade.getText().toString().trim();

        if (nomeVinho.isEmpty() || quantidadeStr.isEmpty()) {
            Toast.makeText(this, "Preencha o nome do vinho e a quantidade.", Toast.LENGTH_SHORT).show();
            return;
        }

        Vinho vinhoSelecionado = buscarVinhoPorNome(nomeVinho);
        if (vinhoSelecionado == null) {
            Toast.makeText(this, "Vinho não encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantidade = Integer.parseInt(quantidadeStr);
        double precoUnitario = vinhoSelecionado.getPreco();

        ItemPedido item = new ItemPedido(vinhoSelecionado.getId(), quantidade, precoUnitario);
        itensPedido.add(item);

        itensAdapter.add(vinhoSelecionado.getNome() + " - Quantidade: " + quantidade);
        itensAdapter.notifyDataSetChanged();
    }

    private void finalizarPedido() {
        String nomeCliente = editNomeCliente.getText().toString().trim();
        String formaPagamento = spinnerFormaPagamento.getSelectedItem().toString();
        int representanteId = Sessao.getUsuarioLogado().getId();

        if (nomeCliente.isEmpty()) {
            Toast.makeText(this, "Preencha o nome do cliente.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (itensPedido.isEmpty()) {
            Toast.makeText(this, "Adicione pelo menos um item ao pedido.", Toast.LENGTH_SHORT).show();
            return;
        }

        Cliente clienteSelecionado = buscarClientePorNome(nomeCliente);
        if (clienteSelecionado == null) {
            Toast.makeText(this, "Cliente não encontrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        double total = calcularTotalPedido();
        String data = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        String status = "Em andamento";

        long pedidoId = db.criarPedido(clienteSelecionado.getId(), representanteId, data, formaPagamento, status, total, itensPedido);

        if (pedidoId != -1) {
            Toast.makeText(this, "Pedido criado com sucesso!", Toast.LENGTH_SHORT).show();

            NotificacaoUtil.enviarNotificacao(this,
                    "Novo Pedido Criado",
                    "Pedido #" + pedidoId + " foi emitido com sucesso.");

            finish();
        } else {
            Toast.makeText(this, "Erro ao criar pedido.", Toast.LENGTH_SHORT).show();
        }
    }

    private double calcularTotalPedido() {
        double total = 0;
        for (ItemPedido item : itensPedido) {
            total += item.getQuantidade() * item.getPrecoUnitario();
        }
        return total;
    }

    private Cliente buscarClientePorNome(String nome) {
        for (Cliente c : clientes) {
            if (c.getNome().equalsIgnoreCase(nome.trim())) {
                return c;
            }
        }
        return null;
    }

    private Vinho buscarVinhoPorNome(String nome) {
        for (Vinho v : vinhos) {
            if (v.getNome().equalsIgnoreCase(nome.trim())) {
                return v;
            }
        }
        return null;
    }
}
