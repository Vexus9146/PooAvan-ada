package org.example.classes;

import java.util.List;

public class Venda {
    private Usuarios usuario;
    private List<Produto> produtos;
    private String formaPagamento;
    private double valorTotal;

    public Venda(Usuarios usuario, List<Produto> produtos, String formaPagamento, double valorTotal) {
        this.usuario = usuario;
        this.produtos = produtos;
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}