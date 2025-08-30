package com.example.adegadevinho;

import java.io.Serializable;

public class Cliente implements Serializable{

    private int id;
    private String nome;
    private String cnpjCpf;
    private String endereco;
    private String responsavel;
    private String telefone;
    private String email;
    private String dataCadastro;
    private String bairro;
    private String regiao;

    public Cliente() {
    }

    public Cliente(int id, String nome, String cnpjCpf, String endereco, String responsavel,
                   String telefone, String email, String dataCadastro, String bairro, String regiao) {
        this.id = id;
        this.nome = nome;
        this.cnpjCpf = cnpjCpf;
        this.endereco = endereco;
        this.responsavel = responsavel;
        this.telefone = telefone;
        this.email = email;
        this.dataCadastro = dataCadastro;
        this.bairro = bairro;
        this.regiao = regiao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(String dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }
}
