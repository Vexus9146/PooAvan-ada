import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Produto> produtos = new HashMap<>();
        SistemaCadastroProdutos sistema = new SistemaCadastroProdutos(produtos);
        sistema.exibirMenu();
    }
}
