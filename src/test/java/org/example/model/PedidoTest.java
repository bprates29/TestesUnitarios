package org.example.model;

import org.example.enums.FormaPagamento;
import org.example.enums.StatusPedido;
import org.example.utils.asserts.PedidoAssertions;
import org.example.utils.builders.PedidoBuilder;
import org.example.utils.builders.ProdutoBuilder;
import org.example.utils.fixures.ClienteFixtures;
import org.example.utils.fixures.ProdutoFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {
    private Cliente cliente;
    private Endereco endereco;
    private Pedido pedido;
    private Produto produto;
    private ItemPedido item;

    @BeforeEach
    void setup() {
        // Configuraç"oes bãsicas que será utilizada em todos os testes
        cliente = new Cliente("João Batista", "joao@email", "53453453", true);
        endereco = new Endereco("Rua das flores", "123", "asdasd", "Centro", "PoA", "RJ", "3123123");
        cliente.adicionarEndereco(endereco);
        produto = new Produto("Camiseta", 50.0, 10);
        pedido = new Pedido(cliente);
        item = new ItemPedido(produto, 2);
    }

    //nomeDoMetodo_quando_parara_entao_parara
    //nomeDoMetodo_when_parara_should_parara
    //testeLogin() - login_Quando_senha_invalida_Entao_falha()

    @Test
    void deveRealizarPagamentoComSucesso() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);

        pedido.realizarPagamento(FormaPagamento.CARTAO_CREDITO);

        assertEquals(StatusPedido.EM_PREPARACAO, pedido.getStatus());
        assertNotNull(pedido.getPagamento());
        assertTrue(pedido.getPagamento().isAprovado());
        assertTrue(ChronoUnit.SECONDS.between(pedido.getDataAtualizacao(), LocalDateTime.now()) <= 2);
    }

    //objetivo: void realizarPagamento_quandoPedidoCriado_entaoDeveConfirmarPagamentoEAprovar
    //behavior drive: void dadoPedidoCriado_quandoPagamentoRealizado_entaoStatusDeveSerEmPreparacao
    //simples e direto: pagamentoPedidoDeveMudarStatusParaEmPreparacaoEPedidoAprovado

    @Test
    void deveLancarExcecaoAoPagarPedidoJaPago() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);

        pedido.realizarPagamento(FormaPagamento.CARTAO_CREDITO);

        Exception exception = assertThrows(IllegalStateException.class,
                () -> {
                    pedido.realizarPagamento(FormaPagamento.PIX);
                });

        var expectedMessage = "Não é possível realizar pagamento para um pedido com status: " + pedido.getStatus();
        assertEquals(expectedMessage, exception.getMessage());
    }

    // realizarPagamento_quandoPedidoJaPago_entaoDeveLancarExcecao

    @Test
    void deveFinalizarPedidoComSucesso() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);
        pedido.atualizarStatus(StatusPedido.ENVIADO);

        pedido.finalizar();

        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    // finalizar_quandoStatusEnviado_entaoDeveMudarParaEntregue

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class, names = { "ENVIADO" }, mode = EnumSource.Mode.EXCLUDE)
    void deveLancarExcecaoAoFinalizarPedidoNaoEnviado(StatusPedido status) {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);
        pedido.atualizarStatus(status);

        Exception exception = assertThrows(IllegalStateException.class,
                () -> pedido.finalizar());

        var expectedMessage = "Não é possível finalizar um pedido que não foi enviado";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void deveCancelarPedidoComSucesso() {
        pedido.adicionarItem(item);

        pedido.cancelar();

        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }

    // cancelar_quandoPedidoCriado_entaoStatusDeveSerCancelado
    // behavior driven: dadoPedidoCriado_quandoCancelar_entaoDeveSerCancelado

//    @ParameterizedTest
//    @EnumSource(value = StatusPedido.class, names = { "CRIADO", "AGUARDANDO_PAGAMENTO" }, mode = EnumSource.Mode.EXCLUDE)
    @Test
    void deveLancarExcecaoAoCancelarPedidoDiferenteDosQuePermitemCancelamento() {
        pedido.adicionarItem(item);
        pedido.atualizarStatus(StatusPedido.EM_PREPARACAO);

        var exception = assertThrows(IllegalStateException.class,
                () -> pedido.cancelar());

        var expectedMessage = "Não é possível cancelar um pedido com status: " + pedido.getStatus();
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void deveCalcularTotal() {
        Cliente cliente = new Cliente("teste nome", true);

        Produto camiseta = new Produto("Camiseta", 50.0, 100);
        Produto tenis = new Produto("Tenis", 500.0, 50);

        Endereco endereco = new Endereco("Rua das flores", "123", "asdasd", "Centro", "PoA", "RJ", "3123123");

        Pedido pedido = new Pedido(cliente);
        pedido.definirEnderecoEntrega(endereco);
        pedido.adicionarItem(new ItemPedido(camiseta, 2));
        pedido.adicionarItem(new ItemPedido(tenis, 1));

        assertEquals(600.0, pedido.getTotal());
    }

    @Test
    void deveCalcularTotalWithHelpers() {
        Pedido pedido = new PedidoBuilder()
                .comCliente(ClienteFixtures.criarClientePadrao())
                .comItem(ProdutoFixture.camisetaBarata(), 2)
                .comItem(new ProdutoBuilder().comNome("Tenis").comPreco(500.0).build(), 1)
                .build();

        PedidoAssertions.assertPedido(pedido, StatusPedido.EM_PREPARACAO, 600.0);
    }
}