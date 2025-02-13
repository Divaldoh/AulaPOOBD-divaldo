package com.loja.implement;

import com.loja.Database;
import com.loja.implement.ClienteDAOImpl;
import com.loja.domain.Cliente;
import com.loja.domain.Funcionario;
import com.loja.dao.PedidoDAO;
import com.loja.domain.Pedido;
import com.loja.domain.Produto;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class PedidoDAOImpl implements PedidoDAO {
    private ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
    private FuncionarioImpl funcionarioDAO = new FuncionarioImpl();

   @Override
    public void salvar(Pedido pedido) throws SQLException {
        Connection conn = Database.getConnection();
        String sqlVerificaCliente = "SELECT COUNT(*) FROM CLIENTE WHERE CPF = ?";
        String sqlVerificaFuncionario = "SELECT COUNT(*) FROM FUNCIONARIO WHERE CPF = ?";
        String sqlPedido = "INSERT INTO PEDIDO (CPF_CLIENTE_FK, CPF_FUNCIONARIO_FK, VALOR_TOTAL) VALUES (?, ?, ?)";
        String sqlItemPedido = "INSERT INTO ITEM_PEDIDO (ID_PEDIDO_FK, ID_PRODUTO_FK, QUANTIDADE, VALOR) VALUES (?, ?, ?, ?)";
        String sqlUpdateEstoque = "UPDATE PRODUTO SET QUANTIDADE = QUANTIDADE - ? WHERE ID = ?";

        try {
            conn.setAutoCommit(false); // Inicia a transação

            // Verifica se o cliente existe
            PreparedStatement stmtVerificaCliente = conn.prepareStatement(sqlVerificaCliente);
            stmtVerificaCliente.setString(1, pedido.getCliente().getCpf());
            ResultSet rsCliente = stmtVerificaCliente.executeQuery();
            rsCliente.next();
            if (rsCliente.getInt(1) == 0) {
                System.out.println("Erro: Cliente não encontrado.");
                return;
            }

            // Verifica se o funcionário existe
            PreparedStatement stmtVerificaFuncionario = conn.prepareStatement(sqlVerificaFuncionario);
            stmtVerificaFuncionario.setString(1, pedido.getFuncionario().getCpf());
            ResultSet rsFuncionario = stmtVerificaFuncionario.executeQuery();
            rsFuncionario.next();
            if (rsFuncionario.getInt(1) == 0) {
                System.out.println("Erro: Funcionário não encontrado.");
                return;
            }

            // Prepara a inserção do pedido
            PreparedStatement stmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            double valorTotal = pedido.getItens().stream().mapToDouble(item -> item.getValorUnitario() * item.getQuantidade()).sum();
            stmtPedido.setString(1, pedido.getCliente().getCpf());
            stmtPedido.setString(2, pedido.getFuncionario().getCpf());
            stmtPedido.setDouble(3, valorTotal);
            stmtPedido.executeUpdate();

            // Obtém o ID do pedido gerado
            ResultSet rs = stmtPedido.getGeneratedKeys();
            if (rs.next()) {
                int pedidoId = rs.getInt(1);

                // Insere os itens do pedido
                PreparedStatement stmtItem = conn.prepareStatement(sqlItemPedido);
                PreparedStatement stmtEstoque = conn.prepareStatement(sqlUpdateEstoque);
                for (Produto item : pedido.getItens()) {
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

            conn.commit(); // Confirma a transação
        } catch (Exception e) {
            try {
                conn.rollback(); // Desfaz qualquer alteração em caso de erro
                System.out.println("Erro ao registrar a venda. A transação foi desfeita.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                conn.setAutoCommit(true); // Restaura o comportamento padrão
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public ArrayList<Pedido> buscarTodos() throws SQLException {

        ArrayList<Pedido> vendas = new ArrayList<>();
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

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int pedidoAtual = -1;
            Pedido vendaAtual = null;
            ArrayList<Produto> itensVenda = new ArrayList<>();

            while (rs.next()) {
                int pedidoID = rs.getInt("PEDIDOID");

                // Se encontrarmos um novo pedido, precisamos salvar o anterior e iniciar um novo
                if (pedidoID != pedidoAtual) {
                    if (vendaAtual != null) {
                        vendaAtual.setItens(itensVenda);
                        vendas.add(vendaAtual);
                    }

                    // Buscando Cliente e Funcionário pelo CPF
                    String clienteCPF = rs.getString("ClienteCPF");
                    Cliente cliente = clienteDAO.buscar(clienteCPF);

                    String funcionarioCPF = rs.getString("FuncionarioCPF");
                    Funcionario funcionario = funcionarioDAO.buscarFuncionario(funcionarioCPF);

                    // Criando um novo pedido
                    pedidoAtual = pedidoID;
                    vendaAtual = new Pedido(cliente, funcionario, new ArrayList<>());
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

            // Adiciona o último pedido à lista
            if (vendaAtual != null) {
                vendaAtual.setItens(itensVenda);
                vendas.add(vendaAtual);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erro ao buscar pedidos: " + e.getMessage());
        }

        return vendas;
    }

    @Override
    public Pedido buscarPedido(int ID) throws SQLException {
        return null;
    }
}




