package com.loja.domain;

import com.loja.domain.abstractClasses.Pessoa;

public class Cliente extends Pessoa {

    public Cliente(String cpf, String nome, String endereco, String telefone) {
        super(cpf,nome,endereco, telefone);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "cpf='" + getCpf() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                '}';
    }
}

