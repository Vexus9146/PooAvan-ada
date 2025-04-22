package org.example.pagamentos;

public class Boleto implements PagamentoStrategy {
    @Override
    public String processarPagamento(double valor) {
        return "Pagamento confirmado com sucesso via Boleto. Valor: R$ " + valor;
    }
}