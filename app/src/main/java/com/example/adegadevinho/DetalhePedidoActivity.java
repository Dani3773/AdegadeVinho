package com.example.adegadevinho;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DetalhePedidoActivity extends AppCompatActivity {

    private TextView txtCliente, txtData, txtPagamento, txtStatus, txtTotal;
    private ListView listItens;
    private DatabaseHelper db;
    private int pedidoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_pedido);

        txtCliente = findViewById(R.id.txtCliente);
        txtData = findViewById(R.id.txtData);
        txtPagamento = findViewById(R.id.txtPagamento);
        txtStatus = findViewById(R.id.txtStatus);
        txtTotal = findViewById(R.id.txtTotal);
        listItens = findViewById(R.id.listItensPedido);

        db = new DatabaseHelper(this);

        pedidoId = getIntent().getIntExtra("pedido_id", -1);
        if (pedidoId == -1) {
            Toast.makeText(this, "Erro ao carregar pedido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Pedido pedido = db.getPedidoPorId(pedidoId);
        Cliente cliente = db.getClientePorId(pedido.getClienteId());
        List<ItemPedido> itens = db.getItensPorPedido(pedidoId);

        txtCliente.setText("Cliente: " + cliente.getNome());
        txtData.setText("Data: " + pedido.getData());
        txtPagamento.setText("Pagamento: " + pedido.getFormaPagamento());
        txtStatus.setText("Status: " + pedido.getStatus());
        txtTotal.setText(String.format("Total: R$ %.2f", pedido.getTotal()));

        ItemPedidoAdapter adapter = new ItemPedidoAdapter(this, itens);
        listItens.setAdapter(adapter);
    }
}
