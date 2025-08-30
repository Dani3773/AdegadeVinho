package com.example.adegadevinho;

import java.util.Date;
import java.util.List;

public class Pedido {
    private int id;
    private int clienteId;
    private int representanteId;
    private String dataPedido;
    private double valorTotal;
    private String formaPagamento;
    private String status;
    private List<ItemPedido> itens;

    public Pedido() {
    }

    public Pedido(int clienteId, int representanteId, String dataPedido,
                  double valorTotal, String formaPagamento, String status) {
        this.clienteId = clienteId;
        this.representanteId = representanteId;
        this.dataPedido = dataPedido;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.status = status;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getRepresentanteId() {
        return representanteId;
    }

    public void setRepresentanteId(int representanteId) {
        this.representanteId = representanteId;
    }

    public String getData() {
        return dataPedido;
    }

    public void setData(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getTotal() {
        return valorTotal;
    }

    public void setTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}
