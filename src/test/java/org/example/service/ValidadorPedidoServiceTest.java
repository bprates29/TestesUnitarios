package org.example.service;

import org.example.model.CarrinhoCompras;
import org.example.model.Cliente;
import org.example.model.ItemPedido;
import org.example.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorPedidoServiceTest {

    private ValidadorPedidoService validadorPedidoService;

    @BeforeEach
    void setup() {
        validadorPedidoService = new ValidadorPedidoService();
    }

    @Test
    void validarCliente_quandoClienteAtivo_entaoNaoDeveLancarExcecao() {
        Cliente clienteAtivo = new Cliente("Nome", true);

        assertDoesNotThrow(() ->
                validadorPedidoService.validarCliente(clienteAtivo));
    }

    @Test
    void validarCliente_quandoClienteInativo_entaoDeveLancarExcecao() {
        Cliente clienteInativo = new Cliente("Nome", false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                validadorPedidoService.validarCliente(clienteInativo));

        assertEquals("Cliente inativo.", exception.getMessage());
    }

    @Test
    void validarCarrinho_quandoComItens_entaoNaoDeveLancarExcecao() {
        Cliente cliente = new Cliente("Nome", true);
        CarrinhoCompras carrinho = new CarrinhoCompras(cliente);
        Produto produto = new Produto("notebook", 4000.0, 5);
        carrinho.adicionarItem(produto, 1);

        assertDoesNotThrow(() ->
                validadorPedidoService.validarCarrinho(carrinho));
    }

    @Test
    void validarCarrinho_quandoCarrinhoVazio_entaoDeveLancarExcecao() {
        Cliente cliente = new Cliente("Nome", true);
        CarrinhoCompras carrinho = new CarrinhoCompras(cliente);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                validadorPedidoService.validarCarrinho(carrinho));

        assertEquals("Carrinho vazio.", exception.getMessage());
    }

    @Test
    void validarEstoque_quandoComEstoqueSuficiente_entaoNaoDeveLancarExcecao() {
        Cliente cliente = new Cliente("Nome", true);
        CarrinhoCompras carrinho = new CarrinhoCompras(cliente);
        Produto produto = new Produto("notebook", 4000.0, 5);
        carrinho.adicionarItem(produto, 5);

        var itemPedido = carrinho.getItens().getFirst();

        assertDoesNotThrow(() ->
                validadorPedidoService.validarEstoque(itemPedido));
    }

    @Test
    void validarCarrinho_quandoProdutoSemEstoque_entaoDeveLancarExcecao() {
        Produto produto = new Produto("notebook", 4000.0, 5);
        ItemPedido itemPedido = new ItemPedido(produto, 6);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                validadorPedidoService.validarEstoque(itemPedido));

        assertEquals("Produto sem estoque: " + itemPedido.getProduto().getNome(),
                exception.getMessage());
    }

}