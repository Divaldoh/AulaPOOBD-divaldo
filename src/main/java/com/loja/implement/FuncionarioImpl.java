package com.loja.implement;

import com.loja.Database;
import com.loja.dao.FuncionarioDAO;
import com.loja.domain.Funcionario;

import java.sql.*;
import java.util.ArrayList;

public class FuncionarioImpl implements FuncionarioDAO {

    @Override
    public ArrayList<Funcionario> buscarTodos() throws SQLException {
            Connection conn = Database.getConnection();


            String sql = "SELECT * FROM FUNCIONARIO";
            ArrayList<Funcionario> funcionarios = new ArrayList<>();

            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Funcionario funcionario = new Funcionario(
                            rs.getString("cpf"),
                            rs.getString("nome"),
                            rs.getString("endereco"),
                            rs.getString("telefone")
                    );
                    funcionarios.add(funcionario);
                }
            } catch (SQLException e) {
                System.out.println("Erro ao buscar clientes: " + e.getMessage());
                throw e;
            }
            return funcionarios;
        }

    @Override
    public void salvar(Funcionario funcionario) throws SQLException {
        Connection conn = Database.getConnection();


        String sql = "INSERT INTO FUNCIONARIO (CPF, NOME, ENDERECO, TELEFONE) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, funcionario.getCpf());
            stmt.setString(2, funcionario.getNome());
            stmt.setString(3, funcionario.getEndereco());
            stmt.setString(4, funcionario.getTelefone());

            stmt.executeUpdate();
            System.out.println("Cliente " + funcionario.getNome() + " cadastrado no banco de dados.");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERRO: Já existe um cliente cadastrado com esse CPF.");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar cliente. Tente novamente mais tarde.");

        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado.");
        }
    }

    @Override
    public Funcionario buscarFuncionario(String cpf) throws SQLException {
        Connection conn = Database.getConnection();
        Funcionario funcionario = null;

        String sql = "SELECT * FROM FUNCIONARIO WHERE cpf = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                funcionario =  new Funcionario(
                        rs.getString("cpf"),
                        rs.getString("nome"),
                        rs.getString("endereco"),
                        rs.getString("telefone")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
            throw e;
        }
        return funcionario;
    }
}

