package com.loja.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.loja.Database;
import com.loja.dao.ClienteDAO;


import com.loja.models.Cliente;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void cadastrarCliente(Cliente cliente) throws SQLException {
        Connection conn = Database.getConnection();


        String sql = "INSERT INTO CLIENTE (CPF, NOME, ENDERECO, TELEFONE) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setString(3, cliente.getEndereco());
            stmt.setString(4, cliente.getTelefone());

            stmt.executeUpdate();
            System.out.println("Cliente " + cliente.getNome() + " cadastrado no banco de dados.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
