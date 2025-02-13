package com.loja.dao;

import com.loja.domain.Cliente;

import java.sql.SQLException;

public interface ClienteDAO extends DAO<Cliente> {
    Cliente buscar(String cpf) throws SQLException;
}
