package com.loja.dao;

import java.sql.*;

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

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERRO: Já existe um cliente cadastrado com esse CPF.");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente. Tente novamente mais tarde.");

        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado.");
        }
    }
}

