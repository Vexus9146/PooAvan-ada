package org.example.banco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DadosTeste {

    public static void inserirDados() {
        try (Connection conn = Conexao.conectar()) {
            // Inserir usuário de teste
            String sqlUsuario = "INSERT INTO usuarios (nome, email) VALUES (?, ?)";
            PreparedStatement stmtUsuario = conn.prepareStatement(sqlUsuario);
            stmtUsuario.setString(1, "João da Silva");
            stmtUsuario.setString(2, "rafael@example.com");
            stmtUsuario.executeUpdate();

            // Inserir produtos de teste
            String sqlProduto = "INSERT INTO produtos (nome, preco) VALUES (?, ?)";
            PreparedStatement stmtProduto = conn.prepareStatement(sqlProduto);
            stmtProduto.setString(1, "Camiseta");
            stmtProduto.setDouble(2, 50.00);
            stmtProduto.executeUpdate();

            stmtProduto.setString(1, "Tênis");
            stmtProduto.setDouble(2, 200.00);
            stmtProduto.executeUpdate();

            System.out.println("Dados de teste inseridos com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}