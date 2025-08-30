package com.example.adegadevinho;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.text.DecimalFormat;
import java.util.List;

public class RelatorioAdapter extends BaseAdapter {

    private final Context context;
    private final List<Pedido> pedidos;
    private final LayoutInflater inflater;
    private final DecimalFormat currencyFormat = new DecimalFormat("R$ #,##0.00");

    public RelatorioAdapter(Context context, List<Pedido> pedidos) {
        this.context = context;
        this.pedidos = pedidos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Pedido getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return pedidos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.item_relatorio, parent, false);
        }

        Pedido pedido = getItem(position);

        TextView txtPedido = view.findViewById(R.id.txtPedido);
        TextView txtData = view.findViewById(R.id.txtData);
        TextView txtTotal = view.findViewById(R.id.txtTotal);

        txtPedido.setText("Pedido #" + pedido.getId());
        txtData.setText("Data: " + pedido.getData());
        txtTotal.setText("Total: " + currencyFormat.format(pedido.getTotal()));

        return view;
    }
}
