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
        Cliente clienteAtivo = new Cliente("teste", true);

        assertDoesNotThrow(() ->
                validadorPedidoService.validarCliente(clienteAtivo));
    }

    @Test
    void validarCliente_quandoClienteInativo_entaoDeveLancarExcecao() {
        Cliente clienteInativo = new Cliente("teste", false);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                validadorPedidoService.validarCliente(clienteInativo));

        assertEquals("Cliente inativo.", exception.getMessage());
    }

    @Test
    void validarCarrinho_quandoCarrinhoComItens_entaoNaoDeveLancarExcecao() {
        Cliente cliente = new Cliente("teste", true);
        CarrinhoCompras carrinhoCompras = new CarrinhoCompras(cliente);
        Produto produto = new Produto("Notebook", 4000.0, 5);
        carrinhoCompras.adicionarItem(produto, 1);

        assertDoesNotThrow(() ->
                validadorPedidoService.validarCarrinho(carrinhoCompras));
    }

    @Test
    void validarCarrinho_quandoCarrinhoVazio_entaoDeveLancarExcecao() {
        Cliente cliente = new Cliente("teste", true);
        CarrinhoCompras carrinhoCompras = new CarrinhoCompras(cliente);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                validadorPedidoService.validarCarrinho(carrinhoCompras));

        assertEquals("Carrinho vazio.", exception.getMessage());
    }

    @Test
    void validarEstoque_quandoProdutoComEstoqueSuficiente_entaoNaoDeveLancarExcecao() {
        Produto produto = new Produto("Notebook", 4000.0, 5);
        ItemPedido itemPedido = new ItemPedido(produto, 5);

        assertDoesNotThrow(() ->
                validadorPedidoService.validarEstoque(itemPedido));
    }

    @Test
    void validarEstoque_quandoProduoSemEstoque_entaoDeveLancarExcecao() {
        Produto produto = new Produto("Notebook", 4000.0, 5);
        ItemPedido itemPedido = new ItemPedido(produto, 6);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                validadorPedidoService.validarEstoque(itemPedido));

        assertEquals("Produto sem estoque: " + itemPedido.getProduto().getNome(),
                exception.getMessage());
    }
  
}