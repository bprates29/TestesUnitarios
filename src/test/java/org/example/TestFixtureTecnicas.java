package org.example;

import org.example.enums.FormaPagamento;
import org.example.model.*;
import org.example.utils.builders.PedidoBuilder;
import org.example.utils.fixures.ClienteFixures;
import org.junit.jupiter.api.*;

import java.util.List;

public class TestFixtureTecnicas {

    private CarrinhoCompras carrinho;
    private Cliente cliente;
    private Endereco endereco;
    private List<Produto> produtos;

    @BeforeEach
    void setup() {
        // Configuraç"oes bãsicas que será utilizada em todos os testes
        cliente = new Cliente("João Batista", "joao@email", "53453453", true);
        endereco = new Endereco("Rua das flores", "123", "asdasd", "Centro", "PoA", "RJ", "3123123");
        cliente.adicionarEndereco(endereco);
        carrinho = new CarrinhoCompras(cliente);
    }

    @BeforeAll
    static void configurarAmbienteGera() {
        System.out.println("Exercutado uma vez antes de todos os testes");
    }

    @Test
    @DisplayName("Teste sem setup method")
    void testCarrinhoSemSetupMethod() {
        cliente = new Cliente("João Batista", "joao@email", "53453453", true);
        endereco = new Endereco("Rua das flores", "123", "asdasd", "Centro", "PoA", "RJ", "3123123");
        cliente.adicionarEndereco(endereco);
        carrinho = new CarrinhoCompras(cliente);
        Produto camiseta = criarProduto("Camiseta", 50.0, 3);
        carrinho.adicionarItem(camiseta, 2);

        //ACT
        double total = carrinho.getTotal();

        Assertions.assertEquals(100.0, total);
    }

    //2. Factory Method
    private static Produto criarProduto(String nome, double preco, int estoque) {
        return new Produto(nome, "Descricao camiseta", preco, estoque, "Roupas");
    }

    @Test
    @DisplayName("Teste usando setup method (@BeforeEach) e Factory method")
    void testCarrinhoComSetupMethod() {
        //ARRANGE
        Produto camiseta = criarProduto("Soquete", 400.0, 5);
        carrinho.adicionarItem(camiseta, 3);

        //ACT
        double total = carrinho.getTotal();

        Assertions.assertEquals(1200.0, total);
    }

    @Test
    @DisplayName("Teste usando Object Mother Pattern")
    void testCarrinhoComObjectMother() {
        var cliente2 = ClienteFixures.criarClientePadrao();
        CarrinhoCompras novoCarrinho = new CarrinhoCompras(cliente2);
        var expectedTotal = 200.0;

        var tenisValue = 200.0;
        Produto tenis = criarProduto("Tenis", tenisValue, 3);
        novoCarrinho.adicionarItem(tenis, 1);

        Assertions.assertEquals(expectedTotal, novoCarrinho.getTotal());
    }

    @Test
    @DisplayName("Teste usando Builder Pattern")
    void testPedidoComBuilder() {
        var expectedTotal = 250.0;

        Pedido pedido = new PedidoBuilder()
                .comCliente(cliente)
                .comEndereco(endereco)
                .comItem(criarProduto("Camiseta", 50.0, 10), 2)
                .comItem(criarProduto("Tenis", 150.0, 5), 1)
                .comFormaDePagamento(FormaPagamento.PIX)
                .build();

        double total = pedido.getTotal();

        Assertions.assertEquals(expectedTotal, total);
    }
}
