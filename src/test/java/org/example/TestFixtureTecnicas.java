package org.example;

import org.example.model.CarrinhoCompras;
import org.example.model.Cliente;
import org.example.model.Endereco;
import org.example.model.Produto;
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
        Produto camiseta = criarProduto("Camiseta", 50.0);
        carrinho.adicionarItem(camiseta, 2);

        //ACT
        double total = carrinho.getTotal();

        Assertions.assertEquals(100.0, total);
    }

    private static Produto criarProduto(String nome, double preco) {
        return new Produto(nome, "Descricao camiseta", preco, 10, "Roupas");
    }

    @Test
    @DisplayName("Teste usando setup method (@BeforeEach)")
    void testCarrinhoComSetupMethod() {
        //ARRANGE

        Produto camiseta = criarProduto("Soquete", 400.0);
        carrinho.adicionarItem(camiseta, 3);

        //ACT
        double total = carrinho.getTotal();

        Assertions.assertEquals(1200.0, total);
    }
}
