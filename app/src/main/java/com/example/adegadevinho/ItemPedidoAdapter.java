package com.example.adegadevinho;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.List;

public class ItemPedidoAdapter extends ArrayAdapter<ItemPedido> {
    private Context context;
    private List<ItemPedido> itens;
    private DatabaseHelper db;

    public ItemPedidoAdapter(Context context, List<ItemPedido> itens) {
        super(context, 0, itens);
        this.context = context;
        this.itens = itens;
        this.db = new DatabaseHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemPedido item = itens.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_pedido, parent, false);
        }

        TextView txtNomeVinho = convertView.findViewById(R.id.txtNomeVinho);
        TextView txtQuantidade = convertView.findViewById(R.id.txtQuantidade);
        TextView txtPrecoTotal = convertView.findViewById(R.id.txtPrecoTotal);
        ImageView imgVinho = convertView.findViewById(R.id.imgVinho);

        Vinho vinho = db.getVinhoPorId(item.getVinhoId());

        if (vinho != null) {
            txtNomeVinho.setText("Vinho: " + vinho.getNome());

            double precoUnitario = vinho.getPreco();
            double total = precoUnitario * item.getQuantidade();
            txtPrecoTotal.setText(String.format("Total: R$ %.2f", total));

            if (vinho.getImagem() != 0) {
                imgVinho.setImageResource(vinho.getImagem());
            } else {
                imgVinho.setImageResource(R.drawable.ic_launcher_background);
            }
        } else {
            txtNomeVinho.setText("Vinho desconhecido");
            txtPrecoTotal.setText("Preço indisponível");
            imgVinho.setImageResource(R.drawable.ic_launcher_background);
        }

        txtQuantidade.setText("Quantidade: " + item.getQuantidade());

        return convertView;
    }
}
