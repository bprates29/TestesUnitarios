package org.example.service;

import org.example.model.CarrinhoCompras;
import org.example.model.Cliente;
import org.example.model.ItemPedido;

public class ValidadorPedidoService {

    public void validarCliente(Cliente cliente) {
        if (!cliente.isAtivo()) {
            throw new IllegalStateException("Cliente inativo.");
        }
    }

    public void validarCarrinho(CarrinhoCompras carrinho) {
        if (carrinho.getItens().isEmpty()) {
            throw new IllegalStateException("Carrinho vazio.");
        }
    }

    public void validarEstoque(ItemPedido item) {
        if (!item.getProduto().temEstoque(item.getQuantidade())) {
            throw new IllegalStateException("Produto sem estoque: " + item.getProduto().getNome());
        }
    }
}
