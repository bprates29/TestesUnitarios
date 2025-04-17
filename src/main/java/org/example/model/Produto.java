package org.example.model;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.Objects;

public class Produto {
    private String codigo;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private String categoria;
    
    public Produto(String nome, double preco, int estoque) {
        this.codigo = UUID.randomUUID().toString().substring(0, 8);
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.dataCadastro = LocalDateTime.now();
        this.dataAtualizacao = this.dataCadastro;
    }
    
    public Produto(String nome, String descricao, double preco, int estoque, String categoria) {
        this(nome, preco, estoque);
        this.descricao = descricao;
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }
    
    public String getNome() {
        return nome;
    }
    
    public int getEstoque() {
        return estoque;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public boolean temEstoque(int qtd) {
        return estoque >= qtd;
    }

    public void baixarEstoque(int qtd) {
        if (!temEstoque(qtd)) {
            throw new IllegalArgumentException("Estoque insuficiente");
        }
        this.estoque -= qtd;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public void adicionarEstoque(int qtd) {
        if (qtd <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.estoque += qtd;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public void atualizarPreco(double novoPreco) {
        if (novoPreco <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        this.preco = novoPreco;
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return codigo.equals(produto.codigo);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
} 