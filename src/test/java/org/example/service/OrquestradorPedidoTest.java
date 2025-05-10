package org.example.service;

import org.example.enums.FormaPagamento;
import org.example.enums.StatusPedido;
import org.example.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrquestradorPedidoTest {

    @Mock
    private ValidadorPedidoService validador;

    @Mock
    private EnviarEmailService emailService;

    private OrquestradorPedido orquestradorPedido;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        orquestradorPedido = new OrquestradorPedido(validador, emailService);
    }

    @Test
    void processarPedido_quandoDadosValidos_entaoDeveConfirmarPedidoEEnviarEmail() {
        Cliente cliente = new Cliente("Test", true);
        Endereco endereco = new Endereco("Rua das Flores", "123", "Apto 101", "Centro", "São Paulo", "SP", "01234-567");
        cliente.adicionarEndereco(endereco);

        Produto produto = new Produto("Camiseta", 50.0, 10);
        CarrinhoCompras carrinho = new CarrinhoCompras(cliente);
        carrinho.adicionarItem(produto, 2);

        Pedido pedido = orquestradorPedido.processarPedido(cliente, carrinho, FormaPagamento.PIX);

        Assertions.assertNotNull(pedido);
        Assertions.assertEquals(StatusPedido.EM_PREPARACAO, pedido.getStatus());

        assertTrue(ChronoUnit.SECONDS.between(
                pedido.getDataAtualizacao(),
                LocalDateTime.now()) < 5);

        verify(validador, times(2)).validarCliente(cliente);
        verify(validador).validarCarrinho(carrinho);
        verify(validador).validarEstoque(carrinho.getItens().getFirst());
        verify(emailService).enviarConfirmacao(eq(cliente.getEmail()), any(Pedido.class));
    }
  
}