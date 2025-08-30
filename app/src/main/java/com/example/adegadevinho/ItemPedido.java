package com.example.adegadevinho;

public class ItemPedido {
    private int id;
    private int pedidoId;
    private int vinhoId;
    private int quantidade;
    private double precoUnitario;

    public ItemPedido() {
    }

    public ItemPedido(int vinhoId, int quantidade, double precoUnitario) {
        this.vinhoId = vinhoId;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getPedidoId() {
        return pedidoId;
    }
    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }
    public int getVinhoId() {
        return vinhoId;
    }
    public void setVinhoId(int vinhoId) {
        this.vinhoId = vinhoId;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public double getPrecoUnitario() {
        return precoUnitario;
    }
    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}

