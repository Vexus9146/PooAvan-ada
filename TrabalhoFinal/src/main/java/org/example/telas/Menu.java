package org.example.telas;

import org.example.banco.Conexao;
import org.example.banco.DadosTeste;
import org.example.classes.Usuarios;
import org.example.classes.Produto;
import org.example.pagamentos.FormaPagamento;
import org.example.pagamentos.PagamentoFactory;
import org.example.pagamentos.PagamentoStrategy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static Usuarios buscarUsuarioPorEmail(String email) {
        try (Connection conn = Conexao.conectar()) {
            String sql = "SELECT * FROM usuarios WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuarios(rs.getInt("id"), rs.getString("nome"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Produto buscarProdutoPorId(int id) {
        try (Connection conn = Conexao.conectar()) {
            String sql = "SELECT * FROM produtos WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(rs.getInt("id"), rs.getString("nome"), rs.getDouble("preco"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static List<Produto> selecionarProdutos() {
        List<Produto> produtosSelecionados = new ArrayList<>();
        System.out.print("Digite os IDs dos produtos (separados por vírgula): ");
        String input = scanner.nextLine();
        String[] ids = input.split(",");

        for (String idStr : ids) {
            int id = Integer.parseInt(idStr.trim());
            Produto produto = buscarProdutoPorId(id);
            if (produto != null) {
                produtosSelecionados.add(produto);
                System.out.println("Produto encontrado: " + produto.getNome() + " (R$ " + produto.getPreco() + ")");
            } else {
                System.out.println("Produto com ID " + id + " não encontrado.");
            }
        }
        return produtosSelecionados;
    }

    private static double calcularValorTotal(List<Produto> produtos) {
        double total = 0;
        for (Produto produto : produtos) {
            total += produto.getPreco();
        }
        return total;
    }

    private static FormaPagamento selecionarFormaPagamento() {
        System.out.println("Escolha a forma de pagamento: ");
        System.out.println("1 - Cartão de Crédito");
        System.out.println("2 - Boleto");
        System.out.println("3 - PIX");
        System.out.print("Opção: ");
        int opcao = Integer.parseInt(scanner.nextLine());

        switch (opcao) {
            case 1:
                return FormaPagamento.CARTAO;
            case 2:
                return FormaPagamento.BOLETO;
            case 3:
                return FormaPagamento.PIX;
            default:
                System.out.println("Opção inválida.");
                return FormaPagamento.PIX;

        }
    }

    private static void registrarVenda(Usuarios usuario, List<Produto> produtos, FormaPagamento formaPagamento, double valorTotal) {
        try (Connection conn = Conexao.conectar()) {

            String sqlVenda = "INSERT INTO vendas (usuario_id, forma_pagamento, valor_total) VALUES (?, ?, ?)";
            PreparedStatement stmtVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
            stmtVenda.setInt(1, usuario.getId());
            stmtVenda.setString(2, formaPagamento.name());
            stmtVenda.setDouble(3, valorTotal);
            stmtVenda.executeUpdate();

            ResultSet rs = stmtVenda.getGeneratedKeys();
            if (rs.next()) {
                int vendaId = rs.getInt(1);

                String sqlItemVenda = "INSERT INTO itens_venda (venda_id, produto_id) VALUES (?, ?)";
                PreparedStatement stmtItem = conn.prepareStatement(sqlItemVenda);
                for (Produto produto : produtos) {
                    stmtItem.setInt(1, vendaId);
                    stmtItem.setInt(2, produto.getId());
                    stmtItem.addBatch();
                }
                stmtItem.executeBatch();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void exibirResumoVenda(Usuarios usuario, List<Produto> produtos, double valorTotal, FormaPagamento formaPagamento) {
        System.out.println("\nResumo da venda:");
        System.out.println("Cliente: " + usuario.getNome());
        System.out.println("Produtos:");
        for (Produto produto : produtos) {
            System.out.println("- " + produto.getNome());
        }
        System.out.println("Valor total: R$ " + valorTotal);
        System.out.println("Pagamento: " + formaPagamento);
        System.out.println("\nVenda registrada com sucesso!");
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Conexao.criarTabelas();

        DadosTeste.inserirDados();

        System.out.print("Digite o Email do usuário: ");
        String email = scanner.nextLine();

        Usuarios usuario = buscarUsuarioPorEmail(email);
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return;
        }
        System.out.println("Usuário encontrado: " + usuario.getNome());

        List<Produto> produtos = selecionarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto selecionado.");
            return;
        }

        FormaPagamento formaPagamento = selecionarFormaPagamento();

        double valorTotal = calcularValorTotal(produtos);

        PagamentoStrategy pagamento = PagamentoFactory.criarPagamento(formaPagamento);
        String resultadoPagamento = pagamento.processarPagamento(valorTotal);

        System.out.println(resultadoPagamento);

        registrarVenda(usuario, produtos, formaPagamento, valorTotal);

        exibirResumoVenda(usuario, produtos, valorTotal, formaPagamento);
    }

}
