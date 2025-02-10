package com.loja.models;

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
        return nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    @Override
    public String toString() {
        return "Produto{" + "ID=" + ID + ", Nome='" + nome + "', Quantidade=" + quantidade + ", Preço=" + valorUnitario + "}";
    }

}