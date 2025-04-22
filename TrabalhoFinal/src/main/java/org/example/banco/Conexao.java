package org.example.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:sqlite:ecommerce.db";

    public static Connection conectar() throws SQLException {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new SQLException("Erro ao conectar com o banco de dados", e);
        }
    }

    public static void criarTabelas() {
        try (Connection conn = conectar()) {
            String sqlUsuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "nome TEXT NOT NULL, "
                    + "email TEXT NOT NULL UNIQUE);";

            String sqlProdutos = "CREATE TABLE IF NOT EXISTS produtos ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "nome TEXT NOT NULL, "
                    + "preco REAL NOT NULL);";

            String sqlVendas = "CREATE TABLE IF NOT EXISTS vendas ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "usuario_id INTEGER, "
                    + "forma_pagamento TEXT, "
                    + "valor_total REAL, "
                    + "FOREIGN KEY (usuario_id) REFERENCES usuarios(id));";

            String sqlItensVenda = "CREATE TABLE IF NOT EXISTS itens_venda ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "venda_id INTEGER, "
                    + "produto_id INTEGER, "
                    + "FOREIGN KEY (venda_id) REFERENCES vendas(id), "
                    + "FOREIGN KEY (produto_id) REFERENCES produtos(id));";

            conn.createStatement().executeUpdate(sqlUsuarios);
            conn.createStatement().executeUpdate(sqlProdutos);
            conn.createStatement().executeUpdate(sqlVendas);
            conn.createStatement().executeUpdate(sqlItensVenda);

            System.out.println("Tabelas criadas com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
