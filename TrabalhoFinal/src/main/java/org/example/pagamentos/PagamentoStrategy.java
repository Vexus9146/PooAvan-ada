package org.example.pagamentos;

public interface PagamentoStrategy {
    String processarPagamento(double valor);
}