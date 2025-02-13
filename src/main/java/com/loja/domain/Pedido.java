package com.loja.domain;

import java.util.ArrayList;


public class Pedido {
    private Cliente cliente;
    private Funcionario funcionario;
    private ArrayList<Produto> itens;

    public Pedido(Cliente cliente, Funcionario funcionario, ArrayList<Produto> itens) {
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.itens = itens;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(String cpfFuncionario) {
        this.funcionario = funcionario;
    }


    public ArrayList<Produto> getItens() {
    return this.itens;
    }

    public void setItens(ArrayList<Produto> itens) {
        this.itens = itens;
    }
}


