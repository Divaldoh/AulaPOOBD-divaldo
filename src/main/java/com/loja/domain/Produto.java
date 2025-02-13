package com.loja.domain;

public class Produto {
    private int ID;
    private String nome;
    private int quantidade;
    private double valorUnitario;

    public Produto(int ID, String nome, int quantidade, double preco) {
        this.ID = ID;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = preco;
    }

    public Produto(String nome, int quantidade, double preco) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valorUnitario = preco;
    }

    public Produto(int ID, int quantidade, double preco) {
        this.ID = ID;
        this.quantidade = quantidade;
        this.valorUnitario = preco;
    }


    public int getId() {
        return ID;
    }

    public String getNome() {
        return this.nome;
    }

    public int getQuantidade() {
        return this.quantidade;
    }

    public double getValorUnitario() {
        return this.valorUnitario;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "Produto{" + "ID=" + ID + ", Nome='" + nome + "', Quantidade=" + quantidade + ", Preço=" + valorUnitario + "}";
    }
}