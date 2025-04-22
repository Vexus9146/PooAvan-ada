package org.example.pagamentos;

public class Pix implements PagamentoStrategy {
    @Override
    public String processarPagamento(double valor) {
        return "Pagamento confirmado com sucesso via PIX. Valor: R$ " + valor;
    }
}