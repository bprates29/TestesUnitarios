package org.example.model;

import org.example.enums.FormaPagamento;
import org.example.enums.StatusPedido;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @Test
    void deveFinalizarPedidoComSucesso() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);
        pedido.atualizarStatus(StatusPedido.ENVIADO);

        pedido.finalizar();

        assertEquals(StatusPedido.ENTREGUE, pedido.getStatus());
    }

    @Test
    void deveCancelarPedidoComSucesso() {
        pedido.adicionarItem(item);

        pedido.cancelar();

        assertEquals(StatusPedido.CANCELADO, pedido.getStatus());
    }

}