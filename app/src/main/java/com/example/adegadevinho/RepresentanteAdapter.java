package com.example.adegadevinho;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RepresentanteAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Representante> listaOriginal;
    private List<Representante> listaFiltrada;

    public RepresentanteAdapter(Context context, List<Representante> lista) {
        this.context = context;
        this.listaOriginal = new ArrayList<>(lista);
        this.listaFiltrada = lista;
    }

    @Override
    public int getCount() {
        return listaFiltrada.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFiltrada.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Representante rep = listaFiltrada.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_representante, parent, false);
        }

        TextView txtNome = convertView.findViewById(R.id.txtNome);
        TextView txtTelefone = convertView.findViewById(R.id.txtTelefone);
        TextView txtEmail = convertView.findViewById(R.id.txtEmail);
        TextView txtcpf = convertView.findViewById(R.id.txtcpf);

        txtNome.setText(rep.getNome());
        txtTelefone.setText(rep.getTelefone());
        txtEmail.setText(rep.getEmail());
        txtcpf.setText(rep.getCpf());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Representante> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(listaOriginal);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (Representante rep : listaOriginal) {
                        if (rep.getNome().toLowerCase().contains(filterPattern)) {
                            filteredList.add(rep);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listaFiltrada = (List<Representante>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}

