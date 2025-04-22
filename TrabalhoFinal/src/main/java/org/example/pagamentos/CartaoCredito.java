package org.example.pagamentos;

public class CartaoCredito implements PagamentoStrategy {
    @Override
    public String processarPagamento(double valor) {
        return "Pagamento confirmado com sucesso via Cartão de Crédito. Valor: R$ " + valor;
    }
}