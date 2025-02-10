package com.loja.DTO;

import com.loja.models.Produto;

import java.util.ArrayList;

public class VendaDTO {
    private String cpfCliente;
    private String cpfFuncionario;
    ArrayList<Produto> itens;

    public VendaDTO(String cpfCliente, String cpfFuncionario, ArrayList<Produto> itens) {
        this.cpfCliente = cpfCliente;
        this.cpfFuncionario = cpfFuncionario;
        this.itens = itens;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public String getCpfFuncionario() {
        return cpfFuncionario;
    }

    public void setCpfFuncionario(String cpfFuncionario) {
        this.cpfFuncionario = cpfFuncionario;
    }

    public ArrayList<Produto> getItens() {
        return itens;
    }

    public void setItens(ArrayList<Produto> itens) {
        this.itens = itens;
    }
}
