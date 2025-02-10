package com.loja.dao;

import com.loja.Database;
import com.loja.models.Produto;
import com.loja.DTO.VendaDTO;
import java.sql.*;
import java.util.ArrayList;

public class ProdutoDAOImpl implements ProdutoDAO {

    @Override
    public void cadastrarProduto(Produto produto) throws SQLException {
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
    public Produto buscarProduto(int id) throws SQLException {
        Connection conn = Database.getConnection();

        String sql = "SELECT * FROM PRODUTO WHERE ID = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, id);
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
    public void efetuarVenda(VendaDTO vendaDTO) throws SQLException {
        Connection conn = Database.getConnection();

        String sqlPedido = "INSERT INTO PEDIDO (CPF_CLIENTE_FK, CPF_FUNCIONARIO_FK, VALOR_TOTAL) VALUES (?, ?, ?)";
        String sqlItemPedido = "INSERT INTO ITEM_PEDIDO (ID_PEDIDO_FK, ID_PRODUTO_FK, QUANTIDADE, VALOR) VALUES (?, ?, ?, ?)";
        String sqlUpdateEstoque = "UPDATE PRODUTO SET QUANTIDADE = QUANTIDADE - ? WHERE ID = ?";

        try {
             PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stmtItem = conn.prepareStatement(sqlItemPedido);
             PreparedStatement stmtEstoque = conn.prepareStatement(sqlUpdateEstoque);

            double valorTotal = vendaDTO.getItens().stream().mapToDouble(item -> item.getValorUnitario() * item.getQuantidade()).sum();
            stmtPedido.setString(1, vendaDTO.getCpfCliente());
            stmtPedido.setString(2, vendaDTO.getCpfFuncionario());
            stmtPedido.setDouble(3, valorTotal);
            stmtPedido.executeUpdate();

            ResultSet rs = stmtPedido.getGeneratedKeys();
            if (rs.next()) {
                int pedidoId = rs.getInt(1);
                for (Produto item : vendaDTO.getItens()) {
                    stmtItem.setInt(1, pedidoId);
                    stmtItem.setInt(2, item.getId());
                    stmtItem.setInt(3, item.getQuantidade());
                    stmtItem.setDouble(4, item.getValorUnitario());
                    stmtItem.executeUpdate();

                    stmtEstoque.setInt(1, item.getQuantidade());
                    stmtEstoque.setInt(2, item.getId());
                    stmtEstoque.executeUpdate();
                }
                System.out.println("Venda registrada com sucesso! Pedido ID: " + pedidoId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Override
        public ArrayList<VendaDTO> buscarVenda() throws SQLException {

        Connection conn = Database.getConnection();
            ArrayList<VendaDTO> vendas = new ArrayList<>();
            String sql = "SELECT p.ID AS PedidoID, " +
                    "c.CPF AS ClienteCPF, f.CPF AS FuncionarioCPF, " +
                    "pr.ID AS ProdutoID, pr.NOME AS ProdutoNome, " +
                    "i.QUANTIDADE, i.VALOR " +
                    "FROM PEDIDO p " +
                    "JOIN CLIENTE c ON p.CPF_CLIENTE_FK = c.CPF " +
                    "JOIN FUNCIONARIO f ON p.CPF_FUNCIONARIO_FK = f.CPF " +
                    "JOIN ITEM_PEDIDO i ON p.ID = i.ID_PEDIDO_FK " +
                    "JOIN PRODUTO pr ON i.ID_PRODUTO_FK = pr.ID " +
                    "ORDER BY p.ID;";


            try {
                 PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery();
                int pedidoAtual = -1;
                VendaDTO vendaAtual = null;
                ArrayList<Produto> itensVenda = new ArrayList<>();

                while (rs.next()) {
                    int pedidoID = rs.getInt("PedidoID");

                    if (pedidoID != pedidoAtual) {
                        // Se já tem uma venda sendo montada, adiciona à lista
                        if (vendaAtual != null) {
                            vendaAtual.setItens(itensVenda);
                            vendas.add(vendaAtual);
                        }

                        // Criando nova venda
                        pedidoAtual = pedidoID;
                        vendaAtual = new VendaDTO(
                                rs.getString("ClienteCPF"),
                                rs.getString("FuncionarioCPF"),
                                new ArrayList<>()
                        );

                        itensVenda = new ArrayList<>();
                    }

                    // Criando produto e adicionando à venda
                    Produto produto = new Produto(
                            rs.getInt("ProdutoID"),
                            rs.getString("ProdutoNome"),
                            rs.getInt("QUANTIDADE"),
                            rs.getDouble("VALOR")
                    );
                    itensVenda.add(produto);
                }

                // Adiciona a última venda na lista
                if (vendaAtual != null) {
                    vendaAtual.setItens(itensVenda);
                    vendas.add(vendaAtual);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return vendas;
        }
    }

