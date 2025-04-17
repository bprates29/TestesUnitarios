package org.example.model;

public class ItemPedido {
    private Produto produto;
    private int quantidade;
    
    public ItemPedido(Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        
        if (!produto.temEstoque(quantidade)) {
            throw new IllegalArgumentException("Produto sem estoque suficiente");
        }
        
        this.produto = produto;
        this.quantidade = quantidade;
    }
    
    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public void baixarEstoque() {
        produto.baixarEstoque(quantidade);
    }
} 