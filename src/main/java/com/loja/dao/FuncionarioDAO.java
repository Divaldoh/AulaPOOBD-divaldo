package com.loja.dao;

import com.loja.domain.Funcionario;

import java.sql.SQLException;
import java.util.ArrayList;

public interface FuncionarioDAO extends DAO<Funcionario> {
    public Funcionario buscarFuncionario(String cpf) throws SQLException;
}
