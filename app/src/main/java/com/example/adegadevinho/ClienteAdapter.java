package com.example.adegadevinho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ClienteAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<Cliente> listaOriginal;
    private List<Cliente> listaFiltrada;

    public ClienteAdapter(Context context, List<Cliente> lista) {
        this.context = context;
        this.listaOriginal = new ArrayList<>(lista);
        this.listaFiltrada = lista;
    }

    @Override
    public int getCount() { return listaFiltrada.size(); }

    @Override
    public Object getItem(int position) { return listaFiltrada.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cliente cliente = listaFiltrada.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false);
        }

        TextView txtNome = convertView.findViewById(R.id.txtNome);
        TextView txtTelefone = convertView.findViewById(R.id.txtTelefone);
        TextView txtCnpjCpf = convertView.findViewById(R.id.txtCnpjCpf);
        TextView txtEmail = convertView.findViewById(R.id.txtEmail);

        txtNome.setText(cliente.getNome());
        txtTelefone.setText(cliente.getTelefone());
        txtCnpjCpf.setText(cliente.getCnpjCpf());
        txtEmail.setText(cliente.getEmail());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Cliente> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(listaOriginal);
                } else {
                    String pattern = constraint.toString().toLowerCase().trim();
                    for (Cliente c : listaOriginal) {
                        if (c.getNome().toLowerCase().contains(pattern)) {
                            filteredList.add(c);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaFiltrada = (List<Cliente>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
