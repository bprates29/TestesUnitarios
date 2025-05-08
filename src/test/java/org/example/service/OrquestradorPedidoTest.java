package org.example.service;

import org.example.enums.FormaPagamento;
import org.example.enums.StatusPedido;
import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class OrquestradorPedidoTest {

    @Mock
    private ValidadorPedidoService validador;

    @Mock
    private EmailService emailService;

    private OrquestradorPedido orquestradorPedido;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orquestradorPedido = new OrquestradorPedido(validador, emailService);
    }

    @Test
    void processarPedido_quandoDadosValidos_entaoDeveConfirmarPedidoEEnviarEmail() {
        Cliente cliente = new Cliente("Teste", true);
        Endereco endereco = new Endereco("Rua das flores", "123", "asdasd", "Centro", "PoA", "RJ", "3123123");
        cliente.adicionarEndereco(endereco);
        Produto produto = new Produto("Camiseta", 50.0, 10);
        CarrinhoCompras carrinho = new CarrinhoCompras(cliente);
        carrinho.adicionarItem(produto, 2);

        Pedido pedido = orquestradorPedido.processarPedido(cliente, carrinho, FormaPagamento.PIX);

        assertNotNull(pedido);
        assertEquals(StatusPedido.EM_PREPARACAO, pedido.getStatus());

        assertTrue(ChronoUnit.SECONDS.between(pedido.getDataAtualizacao(), LocalDateTime.now()) < 5);

        Mockito.verify(validador, Mockito.times(2)).validarCliente(cliente);
        Mockito.verify(validador).validarCarrinho(carrinho);
        Mockito.verify(validador).validarEstoque(carrinho.getItens().getFirst());
        Mockito.verify(emailService)
                .enviarEmail(eq(cliente.getEmail()), any(Pedido.class));
    }

}