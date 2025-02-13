package com.loja.implement;

import com.loja.Database;
import com.loja.dao.ProdutoDAO;
import com.loja.domain.Produto;
import java.sql.*;
import java.util.ArrayList;

public class ProdutoDAOImpl implements ProdutoDAO {

    @Override
    public void salvar(Produto produto) throws SQLException {
        Connection conn = Database.getConnection();

        String sql = "INSERT INTO PRODUTO (NOME, VALOR_UNIT, QUANTIDADE) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getValorUnitario());
            stmt.setInt(3, produto.getQuantidade());

            stmt.executeUpdate();
            System.out.println("Produto " + produto.getNome() + " cadastrado no banco de dados.");

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ERRO: Já existe um produto cadastrado com esse ID.");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar produto. Tente novamente mais tarde.");

        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado.");
        }
    }

    @Override
    public Produto buscarProduto(int ID) throws SQLException {
        Connection conn = Database.getConnection();

        String sql = "SELECT * FROM PRODUTO WHERE ID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                        rs.getInt("ID"),
                        rs.getString("NOME"),
                        rs.getInt("QUANTIDADE"),
                        rs.getDouble("VALOR_UNIT")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Produto> buscarTodos() throws SQLException {
            Connection conn = Database.getConnection();


            String sql = "SELECT * FROM PRODUTO";
            ArrayList<Produto> produtos = new ArrayList<>();

            try {
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Produto produto = new Produto(
                            rs.getInt("ID"),
                            rs.getString("nome"),
                            rs.getInt("quantidade"),
                            rs.getDouble("preco")
                    );
                    produtos.add(produto);
                }
            } catch (SQLException e) {
                System.out.println("Erro ao buscar clientes: " + e.getMessage());
                throw e;
            }
            return produtos;
        }
    }





