package com.loja.dao;

import com.loja.models.Cliente;

import java.sql.SQLException;

public interface ClienteDAO extends DAO<Cliente> {
    void cadastrarCliente(Cliente cliente) throws SQLException;
}
