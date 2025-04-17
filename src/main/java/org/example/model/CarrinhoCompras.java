package org.example.model;

import org.example.enums.FormaPagamento;

import java.util.ArrayList;
import java.util.List;

public class CarrinhoCompras {
    private List<ItemPedido> itens = new ArrayList<>();
    private Cliente cliente;
    
    public CarrinhoCompras(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public void adicionarItem(Produto produto, int quantidade) {
        // Verifica se já existe o produto no carrinho
        for (ItemPedido item : itens) {
            if (item.getProduto().equals(produto)) {
                throw new IllegalArgumentException("Produto já existe no carrinho. Remova-o antes de adicionar novamente.");
            }
        }
        
        itens.add(new ItemPedido(produto, quantidade));
    }
    
    public void removerItem(Produto produto) {
        itens.removeIf(item -> item.getProduto().equals(produto));
    }
    
    public void limpar() {
        itens.clear();
    }
    
    public double getTotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }
    
    public boolean isEmpty() {
        return itens.isEmpty();
    }
    
    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }
    
    public Pedido finalizarCompra(Endereco enderecoEntrega, FormaPagamento formaPagamento) {
        if (isEmpty()) {
            throw new IllegalStateException("Não é possível finalizar uma compra com carrinho vazio");
        }
        
        if (!cliente.isAtivo()) {
            throw new IllegalStateException("Cliente inativo não pode realizar compras");
        }
        
        Pedido pedido = new Pedido(cliente);
        pedido.definirEnderecoEntrega(enderecoEntrega);
        
        for (ItemPedido item : itens) {
            pedido.adicionarItem(item);
            item.baixarEstoque();
        }
        
        // Processa o pagamento
        pedido.realizarPagamento(formaPagamento);
        
        // Limpa o carrinho após finalizar a compra
        limpar();
        
        return pedido;
    }
} 