package com.loja;

import com.loja.domain.*;
import com.loja.implement.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        ClienteDAOImpl clienteDAO = new ClienteDAOImpl();
        ProdutoDAOImpl produtoDAO = new ProdutoDAOImpl();
        PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
        FuncionarioImpl funcionarioDAO = new FuncionarioImpl();

        while (true) {
            System.out.println("\n===== Menu =====");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Cadastrar Produto");
            System.out.println("3. Buscar Produto");
            System.out.println("4. Efetuar Venda");
            System.out.println("5. Listar Vendas");
            System.out.println("6. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    System.out.println("===== Cadastro de Cliente =====");
                    System.out.print("CPF: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Endereço: ");
                    String endereco = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();

                    Cliente cliente = new Cliente(cpf, nome, endereco, telefone);
                    clienteDAO.salvar(cliente);
                    break;

                case 2:
                    System.out.println("===== Cadastro de Produto =====");
                    System.out.print("Nome do Produto: ");
                    String nomeProduto = scanner.nextLine();
                    System.out.print("Valor Unitário: ");
                    double valorUnitario = scanner.nextDouble();
                    System.out.print("Quantidade em Estoque: ");
                    int quantidade = scanner.nextInt();
                    scanner.nextLine();

                    Produto produto = new Produto(nomeProduto, quantidade, valorUnitario);
                    produtoDAO.salvar(produto);
                    System.out.println("Produto cadastrado com sucesso!");
                    break;

                case 3:
                    System.out.println("===== Buscar Produto =====");
                    System.out.print("Digite o ID do Produto: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consumir a quebra de linha

                    Produto produtoEncontrado = produtoDAO.buscarProduto(id);
                    if (produtoEncontrado != null) {
                        System.out.println("Produto encontrado:");
                        System.out.println("ID: " + produtoEncontrado.getId());
                        System.out.println("Nome: " + produtoEncontrado.getNome());
                        System.out.println("Valor Unitário: R$" + produtoEncontrado.getValorUnitario());
                        System.out.println("Quantidade em Estoque: " + produtoEncontrado.getQuantidade());
                    } else {
                        System.out.println("Produto não encontrado.");
                    }
                    break;

                case 4:
                    System.out.print("Informe o CPF do Cliente: ");
                    String cpfCliente = scanner.nextLine();
                    cliente = clienteDAO.buscar(cpfCliente);


                    System.out.print("Informe o CPF do Funcionário: ");
                    String cpfFuncionario = scanner.nextLine();
                    Funcionario funcionario = funcionarioDAO.buscarFuncionario(cpfFuncionario);

                    ArrayList<Produto> itens = new ArrayList<>();

                    while (true) {
                        System.out.print("Informe o ID do produto (ou 0 para finalizar): ");
                        int idProd = scanner.nextInt();
                        scanner.nextLine();

                        if (idProd == 0) break;  // Finaliza a inserção de produtos

                        System.out.print("Informe a quantidade desejada: ");
                        int inputQtd = scanner.nextInt();

                        Produto prod = produtoDAO.buscarProduto(idProd);
                        if (prod != null && prod.getQuantidade() >= inputQtd) {
                            itens.add(new Produto(prod.getId(), inputQtd, prod.getValorUnitario()));
                        } else {
                            System.out.println("Produto não encontrado ou quantidade insuficiente!");
                        }
                    }

                    if (!itens.isEmpty()) {
                        Pedido pedido = new Pedido(cliente, funcionario, itens);
                        pedidoDAO.salvar(pedido);
                        System.out.println("Venda realizada com sucesso!");
                    } else {
                        System.out.println("Nenhum item foi adicionado à venda.");
                    }
                    break;

                case 5:
                    System.out.println("Listando todas as vendas...");
                    ArrayList<Pedido> vendas = pedidoDAO.buscarTodos();

                    if (vendas.isEmpty()) {
                        System.out.println("Nenhuma venda encontrada.");
                    } else {
                        for (Pedido venda : vendas) {
                            System.out.println("\n-------------------------------------------------");
                            System.out.println("Cliente CPF: " + venda.getCliente().getNome());
                            System.out.println("Funcionário CPF: " + venda.getFuncionario().getNome());
                            System.out.println("Itens Comprados:");

                            for (Produto produtos : venda.getItens()) {
                                System.out.println("   - " + produtos.getNome() +
                                        " | Quantidade: " + produtos.getQuantidade() +
                                        " | Valor: R$ " + produtos.getValorUnitario());
                            }

                            double valorTotal = venda.getItens().stream().mapToDouble(item -> item.getValorUnitario() * item.getQuantidade()).sum();

                            System.out.println("Valor Total: R$ " + valorTotal);
                        }
                    }
                    break;

                case 6:
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
