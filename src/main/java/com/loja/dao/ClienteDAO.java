package com.loja.dao;

import com.loja.domain.Cliente;

import java.util.ArrayList;

import java.sql.SQLException;

public interface ClienteDAO extends DAO<Cliente> {
    Cliente buscar(String cpf) throws SQLException;
}
