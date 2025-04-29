package org.example;

import org.example.enums.FormaPagamento;
import org.example.model.*;
import org.example.utils.builders.PedidoBuilder;
import org.example.utils.fixures.ClienteFixures;
import org.junit.jupiter.api.*;

public class TestFixtureTecnicas {
    private CarrinhoCompras carrinho;
    private Cliente cliente;
    private Endereco endereco;

    @BeforeEach
    void setup() {
        cliente = new Cliente("João Silva", "joao@email.com", "11987654321", true);
        endereco = new Endereco("Rua das Flores", "123", "Apto 101", "Centro", "São Paulo", "SP", "01234-567");
        cliente.adicionarEndereco(endereco);
        carrinho = new CarrinhoCompras(cliente);
    }

    @BeforeAll
    static void configurarAmbienteGeral() {
        System.out.println("Executado uma vez antes de todos os testes");
    }

    @Test
    @DisplayName("Teste sem setup method")
    void testCarrinhoSemSetup() {
        //Arrange
        Cliente cliente = new Cliente("João Lino Silva", "joao@email.com", "11987654321", true);
        Endereco endereco = new Endereco("Rua das Flores", "123", "Apto 101", "Centro", "São Paulo", "SP", "01234-567");
        cliente.adicionarEndereco(endereco);
        var carrinho = new CarrinhoCompras(cliente);

        Produto camiseta = new Produto("Camiseta", "Camiseta de algodão", 50.0, 10, "Vestuário");
        carrinho.adicionarItem(camiseta, 2);
        var expectedValue = 100.0;

        //ACT
        var total = carrinho.getTotal();

        //ASSERT
        Assertions.assertEquals(expectedValue, total);
    }

    @Test
    @DisplayName("Teste com setup method")
    void testCarrinhoComMaisProdutosComSetup() {
        //Arrange
        Produto tenis = new Produto("Tenis", "Camiseta de algodão", 250.0, 10, "Vestuário");
        Produto bermuda = new Produto("Bermuda de algodão egipcio", "Camiseta de algodão", 1200.0, 10, "Vestuário");
        carrinho.adicionarItem(tenis, 1);
        carrinho.adicionarItem(bermuda, 1);
        var expectedValue = 1450.0;

        //ACT
        var total = carrinho.getTotal();

        //ASSERT
        Assertions.assertEquals(expectedValue, total);
    }

    private Produto criarProduto(String nome, double preco, int estoque) {
        return new Produto(nome, "...", preco, estoque, "...");
    }

    @Test
    @DisplayName("Teste usando o Factory Method")
    void testCarrinhoProdutosComFactory() {
        //Arrange
        Produto tenis = criarProduto("Tenis", 250.0, 10);
        Produto bermuda = criarProduto("Bermuda", 1200.0, 10);
        Produto toalha = criarProduto("Toalha", 110.0, 20);
        carrinho.adicionarItem(tenis, 1);
        carrinho.adicionarItem(bermuda, 1);
        carrinho.adicionarItem(toalha, 2);
        var expectedValue = 1670.0;

        //ACT
        var total = carrinho.getTotal();

        //ASSERT
        Assertions.assertEquals(expectedValue, total);
    }

    @Test
    @DisplayName("Teste usando Object Mother Pattern")
    void testCarrinhoProdutosComObjectMother() {
        //Arrange
        Cliente clientePadrao = ClienteFixures.criarClientePadrao();
        CarrinhoCompras novoCarrinho = new CarrinhoCompras(clientePadrao);

        Produto tenis = criarProduto("Tenis", 250.0, 10);
        novoCarrinho.adicionarItem(tenis, 2);

        var expectedValue = 500.0;

        //ACT
        var total = novoCarrinho.getTotal();

        //ASSERT
        Assertions.assertEquals(expectedValue, total);
        Assertions.assertEquals(0.0, carrinho.getTotal());
    }

    @Test
    @DisplayName("Teste usando Builder Pattern")
    void testCarrinhoProdutosComBuilderPattern() {
        //Arrange
        Pedido pedido = new PedidoBuilder()
                .comCliente(cliente)
                .comEndereco(endereco)
                .comItem(criarProduto("Camiseta", 50.0, 10), 2)
                .comItem(criarProduto("tenis", 150.0, 15), 1)
                .comFormaDePagamento(FormaPagamento.BOLETO)
                .build();

        //ACT
        var total = pedido.getTotal();
        var expectedTotal = 250.0;

        //ASSERT
        Assertions.assertEquals(expectedTotal, total);
    }
}
