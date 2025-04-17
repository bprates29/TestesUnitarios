package org.example.model;

import org.example.enums.FormaPagamento;

import java.time.LocalDateTime;

public class Pagamento {
    private double valor;
    private FormaPagamento formaPagamento;
    private LocalDateTime dataPagamento;
    private boolean aprovado;
    
    public Pagamento(double valor, FormaPagamento formaPagamento) {
        this.valor = valor;
        this.formaPagamento = formaPagamento;
    }
    
    public boolean processar() {
        this.aprovado = true;
        this.dataPagamento = LocalDateTime.now();
        return aprovado;
    }
    
    public boolean isAprovado() {
        return aprovado;
    }
    
    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }
    
    public double getValor() {
        return valor;
    }
    
    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }
} 