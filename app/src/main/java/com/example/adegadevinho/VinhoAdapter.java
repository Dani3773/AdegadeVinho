package com.example.adegadevinho;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class VinhoAdapter extends RecyclerView.Adapter<VinhoAdapter.VinhoViewHolder> {

    private List<Vinho> listaVinhos;

    private OnVinhoClickListener listener;

    private Context context;

    public interface OnVinhoClickListener {
        void onClick(Vinho vinho);
    }

    public VinhoAdapter(List<Vinho> listaVinhos) {
        this.listaVinhos = new ArrayList<>(listaVinhos);
    }

    public VinhoAdapter(List<Vinho> listaVinhos, OnVinhoClickListener listener) {
        this.listaVinhos = new ArrayList<>(listaVinhos);
        this.listener = listener;
    }

    @Override
    public VinhoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_vinho, parent, false);
        return new VinhoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VinhoViewHolder holder, int position) {

        Vinho vinho = listaVinhos.get(position);

        holder.nomeTextView.setText(vinho.getNome());
        holder.tipoTextView.setText(vinho.getTipo());
        holder.imagemImageView.setImageResource(vinho.getImagem());
        holder.precoTextView.setText(String.format("R$ %.2f", vinho.getPreco()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(vinho);
            } else {

                Intent intent = new Intent(context, DetalhesVinhoActivity.class);
                intent.putExtra("nome", vinho.getNome());
                intent.putExtra("tipo", vinho.getTipo());
                intent.putExtra("imagem", vinho.getImagem());
                intent.putExtra("descricao", vinho.getDescricao());
                intent.putExtra("preco", vinho.getPreco());
                intent.putExtra("safra", vinho.getSafra());
                intent.putExtra("harmonizacao", vinho.getHarmonizacao());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaVinhos.size();
    }


    public static class VinhoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeTextView, tipoTextView, precoTextView;
        ImageView imagemImageView;

        public VinhoViewHolder(View itemView) {
            super(itemView);
            nomeTextView = itemView.findViewById(R.id.txtNomeVinho);
            tipoTextView = itemView.findViewById(R.id.txtTipoVinho);
            imagemImageView = itemView.findViewById(R.id.imgVinho);
            precoTextView = itemView.findViewById(R.id.txtPrecoVinho);
        }
    }

    public void removerVinhoAtual(String nomeVinhoAtual) {
        List<Vinho> novaLista = new ArrayList<>();
        for (Vinho vinho : listaVinhos) {
            if (!vinho.getNome().equals(nomeVinhoAtual)) {
                novaLista.add(vinho);
            }
        }
        atualizarLista(novaLista);
    }

    public void atualizarLista(List<Vinho> novaLista) {
        this.listaVinhos = new ArrayList<>(novaLista);
        notifyDataSetChanged();
    }
}

