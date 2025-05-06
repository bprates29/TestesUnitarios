package org.example.model;

import org.example.enums.FormaPagamento;
import org.example.enums.StatusPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {
    private Cliente cliente;
    private Endereco endereco;
    public Produto produto;
    private ItemPedido item;
    private Pedido pedido;

    @BeforeEach
    void setup() {
        cliente = new Cliente("João Silva", "joao@email.com", "11987654321", true);
        endereco = new Endereco("Rua das Flores", "123", "Apto 101", "Centro", "São Paulo", "SP", "01234-567");
        cliente.adicionarEndereco(endereco);
        produto = new Produto("Camiseta", 50.0, 10);
        item = new ItemPedido(produto, 2);
        pedido = new Pedido(cliente);
    }

    //<nomeDoMetodo>_quando<condição>_então<resultadoEsperado>
    //<nomeDoMetodo>_when<condição>_should<resultadoEsperado>
    //popcorn_ice_crean_selles
    // void teste1() - deveCalcularTotalPedidoCorretamente()
    //testeLogin() - login_Quando_usuario_valido_Entao_sucesso
    //

    @Test
    void deveRealizarPagamentoComSucesso() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);

        pedido.realizarPagamento(FormaPagamento.PIX);

        var expectedFormaPagamento = new Pagamento(pedido.getTotal(), FormaPagamento.PIX);

        assertEquals(StatusPedido.EM_PREPARACAO, pedido.getStatus());
        Assertions.assertNotNull(pedido.getPagamento());
        assertEquals(expectedFormaPagamento, pedido.getPagamento());
        Assertions.assertTrue(pedido.getPagamento().isAprovado());

        Assertions.assertTrue(ChronoUnit.SECONDS.between(pedido.getDataAtualizacao(),
                LocalDateTime.now()) <= 2);
    }

    //Objetivo: realizarPagamento_quandoPedidoCriado_entaoDeveConfirmarPagamentoESeEstaAprovadoComStatusEmPreparacao
    //Behavior Driven: dadoPedidoCriado_quandoPagamentoRealizado_entaoStatusDeveSerEmPreparacaoComPagamentoAprovado
    //simples direto: pagamentoPedidoDeveMudarStatusEConfirmarPagamento

    @Test
    void deveLancarExcecaoAoPagarPedidoJaPago() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);

        pedido.realizarPagamento(FormaPagamento.PIX);

        Exception exception = assertThrows(IllegalStateException.class,
                () -> pedido.realizarPagamento(FormaPagamento.BOLETO));

        var expectedErrorMessage = "Não é possível realizar pagamento para um pedido com status: " + pedido.getStatus();

        assertTrue(exception.getMessage().contains("Não é possível realizar pagamento para um pedido com status"));
        assertEquals(expectedErrorMessage, exception.getMessage());
    }

    //Objetivo: ?
    //Behavior Driven: ?
    //simples direto: ?

    @Test
    void deveFinaizarPedidoComSucesso() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);

        pedido.realizarPagamento(FormaPagamento.PIX);
        pedido.atualizarStatus(StatusPedido.ENVIADO);

        pedido.finalizar();

        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    void deveLancarExcecaoAoFinalizarPedidoNaoEnviado() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);
        pedido.realizarPagamento(FormaPagamento.PIX);

        Exception exception = assertThrows(IllegalStateException.class,
                () -> pedido.finalizar());

        assertEquals("Não é possível finalizar um pedido que não foi enviado", exception.getMessage());
    }

    @Test
    void deveCancelarPedidoComSucesso() {
        pedido.adicionarItem(item);

        pedido.cancelar();

        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }

    @Test
    void DeveLancarExcecaoAoCancelarPedidoPago() {
        pedido.adicionarItem(item);
        pedido.realizarPagamento(FormaPagamento.PIX);

        Exception exception =  assertThrows(IllegalStateException.class,
                () -> pedido.cancelar());

        var expectedMessage = "Não é possível cancelar um pedido com status: " + pedido.getStatus();
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @EnumSource(value = StatusPedido.class,
            names = {"CRIADO", "AGUARDANDO_PAGAMENTO"},
            mode = EnumSource.Mode.EXCLUDE)
    void DeveLancarExcecaoAoCancelarPedidoComStatusAguardandoPagamento(StatusPedido status) {
        pedido.adicionarItem(item);
        pedido.realizarPagamento(FormaPagamento.PIX);
        pedido.atualizarStatus(status);

        Exception exception =  assertThrows(IllegalStateException.class,
                () -> pedido.cancelar());

        var expectedMessage = "Não é possível cancelar um pedido com status: " + pedido.getStatus();
        assertEquals(expectedMessage, exception.getMessage());
    }
}