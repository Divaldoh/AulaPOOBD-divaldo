package com.loja.domain;

import com.loja.domain.abstractClasses.Pessoa;

public class Funcionario extends Pessoa {

    public Funcionario(String cpf, String nome, String endereco, String telefone) {
        super(cpf,nome,endereco, telefone);
    }

    @Override
    public String toString() {
        return "Funcionario{" +
                "cpf='" + getCpf() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", endereco='" + getEndereco() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                '}';
    }
}