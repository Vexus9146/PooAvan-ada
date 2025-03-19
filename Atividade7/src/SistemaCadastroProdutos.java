import java.util.Map;
import java.util.Scanner;

public class SistemaCadastroProdutos {
    private Map<Integer, Produto> produtos;

    public SistemaCadastroProdutos(Map<Integer, Produto> produtos) {
        this.produtos = produtos;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Buscar produto por código");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarProduto(scanner);
                    break;
                case 2:
                    buscarProduto(scanner);
                    break;
                case 0:
                    System.out.println("Saindo");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrarProduto(Scanner scanner) {
        System.out.print("Digite o código do produto: ");
        int codigo = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o preço do produto: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();


        if (produtos.containsKey(codigo)) {
            Produto produtoExistente = produtos.get(codigo);
            System.out.println("Id já cadastrado. ID sendo utilizado pelo produto: " + produtoExistente.getNome());
        } else {
            Produto produto = new Produto(codigo, nome, preco);
            produtos.put(codigo, produto);
            System.out.println("Produto cadastrado com sucesso!");
        }
    }

    private void buscarProduto(Scanner scanner) {
        System.out.print("Digite o código do produto a ser buscado: ");
        int codigoBusca = scanner.nextInt();
        scanner.nextLine();

        Produto produtoEncontrado = produtos.get(codigoBusca);
        if (produtoEncontrado != null) {
            System.out.println("Produto encontrado:");
            System.out.println(produtoEncontrado);
        } else {
            System.out.println("Produto não encontrado.");
        }
    }
}
