package com.example.adegadevinho;

public class Vinho {

    private int id;
    private String nome;
    private String tipo;
    private int imagem;
    private String descricao;
    private double preco;
    private int safra;
    private String harmonizacao;

    public Vinho() {
    }

    public Vinho(int id, String nome, String tipo, int imagem, String descricao, double preco, int safra, String harmonizacao) { //ADICIONADO
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.imagem = imagem;
        this.descricao = descricao;
        this.preco = preco;
        this.safra = safra;
        this.harmonizacao = harmonizacao;
    }

    public Vinho(String nome, String tipo, int imagem, String descricao, double preco, int safra, String harmonizacao) { // ADICIONADO
        this.nome = nome;
        this.tipo = tipo;
        this.imagem = imagem;
        this.descricao = descricao;
        this.preco = preco;
        this.safra = safra;
        this.harmonizacao = harmonizacao;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getImagem() { return imagem; }

    public void setImagem(int imagem) { this.imagem = imagem; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getPreco() { return preco; }

    public void setPreco(double preco) { this.preco = preco; }

    public int getSafra() {return safra; }

    public void setSafra(int safra) {this.safra = safra; }

    public String getHarmonizacao() {return harmonizacao; }

    public void setHarmonizacao(String harmonizacao) {this.harmonizacao = harmonizacao; }

    @Override
    public String toString() {
        return nome + " - R$ " + String.format("%.2f", preco);
    }
}
