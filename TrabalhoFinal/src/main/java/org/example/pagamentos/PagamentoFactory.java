package org.example.pagamentos;

public class PagamentoFactory {
    public static PagamentoStrategy criarPagamento(FormaPagamento forma) {
        switch (forma) {
            case CARTAO:
                return new CartaoCredito();
            case BOLETO:
                return new Boleto();
            case PIX:
                return new Pix();
            default:
                throw new IllegalArgumentException("Forma de pagamento inválida!");
        }
    }
}