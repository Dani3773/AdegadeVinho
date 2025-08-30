package com.example.adegadevinho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class EmissaoAdapter extends BaseAdapter {

    private Context context;
    private List<Pedido> pedidos;
    private DatabaseHelper db;

    public EmissaoAdapter(Context context, List<Pedido> pedidos, DatabaseHelper db) {
        this.context = context;
        this.pedidos = pedidos;
        this.db = db;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Object getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pedidos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pedido pedido = pedidos.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_emissao, parent, false);
        }

        TextView txtData = convertView.findViewById(R.id.txtData);
        TextView txtCliente = convertView.findViewById(R.id.txtCliente);
        TextView txtProdutos = convertView.findViewById(R.id.txtProdutos);
        TextView txtTotal = convertView.findViewById(R.id.txtTotal);
        TextView txtStatus = convertView.findViewById(R.id.txtStatus);

        Cliente cliente = db.getClientePorId(pedido.getClienteId());
        List<ItemPedido> itens = db.getItensPorPedido(pedido.getId());

        StringBuilder produtosBuilder = new StringBuilder();
        for (ItemPedido item : itens) {
            Vinho vinho = db.getVinhoPorId(item.getVinhoId());
            if (vinho != null) {
                produtosBuilder.append("- ").append(vinho.getNome())
                        .append(" (x").append(item.getQuantidade()).append(")\n");
            }
        }

        txtData.setText("Data: " + pedido.getData());
        txtCliente.setText("Cliente: " + (cliente != null ? cliente.getNome() : "Desconhecido"));
        txtProdutos.setText("Produtos:\n" + produtosBuilder.toString().trim());
        txtTotal.setText("Total: R$ " + String.format("%.2f", pedido.getTotal()));
        txtStatus.setText("Status: " + pedido.getStatus());

        return convertView;
    }
}
