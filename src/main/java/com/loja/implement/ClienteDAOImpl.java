package com.loja.implement;

import java.sql.*;
import java.util.ArrayList;

import com.loja.Database;
import com.loja.dao.ClienteDAO;
import com.loja.domain.Cliente;

public class ClienteDAOImpl implements ClienteDAO {

    @Override
    public void salvar(Cliente cliente) throws SQLException {
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

    @Override
    public Cliente buscar(String cpf) throws SQLException {

            Connection conn = Database.getConnection();

            String sql = "SELECT * FROM CLIENTE WHERE CPF = ?";

            try {

                 PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, cpf);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return  new Cliente(
                            rs.getString("CPF"),
                            rs.getString("NOME"),
                            rs.getString("ENDERECO"),
                            rs.getString("TELEFONE")
                    );
                }
            } catch (SQLException e) {
                System.out.println("Erro ao buscar cliente: " + e.getMessage());
                throw e;
            }
            return null;
        }

    @Override
    public ArrayList<Cliente> buscarTodos() throws SQLException {
        Connection conn = Database.getConnection();


        String sql = "SELECT * FROM CLIENTE";
        ArrayList<Cliente> clientes = new ArrayList<>();

        try {
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar clientes: " + e.getMessage());
            throw e;
        }
        return clientes;
    }
}

