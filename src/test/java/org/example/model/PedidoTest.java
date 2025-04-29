package org.example.model;

import org.example.enums.FormaPagamento;
import org.example.enums.StatusPedido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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

    @Test
    void deveRealizarPagamentoComSucesso() {
        pedido.adicionarItem(item);
        pedido.definirEnderecoEntrega(endereco);

        pedido.realizarPagamento(FormaPagamento.PIX);

        var expectedFormaPagamento = new Pagamento(pedido.getTotal(), FormaPagamento.PIX);

        Assertions.assertEquals(StatusPedido.EM_PREPARACAO, pedido.getStatus());
        Assertions.assertNotNull(pedido.getPagamento());
        Assertions.assertEquals(expectedFormaPagamento, pedido.getPagamento());
        Assertions.assertTrue(pedido.getPagamento().isAprovado());

        Assertions.assertTrue(ChronoUnit.SECONDS.between(pedido.getDataAtualizacao(),
                LocalDateTime.now()) <= 2);
    }
}