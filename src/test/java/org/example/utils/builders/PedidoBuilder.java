package org.example.utils.builders;

import org.example.enums.FormaPagamento;
import org.example.model.*;

import java.util.ArrayList;
import java.util.List;

public class PedidoBuilder {
    private Cliente cliente;
    private Endereco endereco;
    private List<ItemPedido> itens = new ArrayList<>();
    private FormaPagamento formaPagamento = FormaPagamento.CARTAO_CREDITO;

    public PedidoBuilder comCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public PedidoBuilder comEndereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public PedidoBuilder comItem(Produto produto, int quantidade) {
        this.itens.add(new ItemPedido(produto, quantidade));
        return this;
    }

    public PedidoBuilder comFormaDePagamento(FormaPagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
        return this;
    }

    public Pedido build() {
        CarrinhoCompras carrinhoCompras = new CarrinhoCompras(cliente);
        for (ItemPedido itemPedido : this.itens) {
            carrinhoCompras.adicionarItem(itemPedido.getProduto(), itemPedido.getQuantidade());
        }
        return carrinhoCompras.finalizarCompra(endereco, formaPagamento);
    }
}
