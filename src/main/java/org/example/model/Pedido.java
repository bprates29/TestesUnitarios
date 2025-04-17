package org.example.model;

import org.example.enums.FormaPagamento;
import org.example.enums.StatusPedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Pedido {
    private String codigo;
    private Cliente cliente;
    private List<ItemPedido> itens = new ArrayList<>();
    private StatusPedido status;
    private Endereco enderecoEntrega;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private Pagamento pagamento;
    
    public Pedido(Cliente cliente) {
        this.cliente = cliente;
        this.codigo = UUID.randomUUID().toString().substring(0, 8);
        this.status = StatusPedido.CRIADO;
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = this.dataCriacao;
    }
    
    public void definirEnderecoEntrega(Endereco endereco) {
        this.enderecoEntrega = endereco;
    }

    public void adicionarItem(ItemPedido item) {
        if (!this.status.permiteAlteracao()) {
            throw new IllegalStateException("Não é possível alterar um pedido com status: " + this.status);
        }
        this.itens.add(item);
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public void removerItem(ItemPedido item) {
        if (!this.status.permiteAlteracao()) {
            throw new IllegalStateException("Não é possível alterar um pedido com status: " + this.status);
        }
        this.itens.remove(item);
        this.dataAtualizacao = LocalDateTime.now();
    }

    public double getTotal() {
        return itens.stream().mapToDouble(ItemPedido::getSubtotal).sum();
    }

    public boolean isValido() {
        return cliente.isAtivo() && !itens.isEmpty() && enderecoEntrega != null;
    }
    
    public void realizarPagamento(FormaPagamento formaPagamento) {
        if (this.status != StatusPedido.CRIADO) {
            throw new IllegalStateException("Não é possível realizar pagamento para um pedido com status: " + this.status);
        }
        
        this.pagamento = new Pagamento(getTotal(), formaPagamento);
        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        
        boolean pagamentoAprovado = this.pagamento.processar();
        
        if (pagamentoAprovado) {
            this.status = StatusPedido.PAGO;
            this.atualizarStatus(StatusPedido.EM_PREPARACAO);
        }
        
        this.dataAtualizacao = LocalDateTime.now();
    }
    
    public void atualizarStatus(StatusPedido novoStatus) {
        this.status = novoStatus;
        this.dataAtualizacao = LocalDateTime.now();
        
        // Aqui poderia ser implementado um sistema de notificação
        notificarCliente();
    }
    
    private void notificarCliente() {
        // Simulação de envio de e-mail
        System.out.println("Enviando e-mail para: " + cliente.getEmail());
        System.out.println("Assunto: Atualização do Pedido " + codigo);
        System.out.println("Seu pedido está com status: " + status);
    }
    
    public void finalizar() {
        if (this.status != StatusPedido.ENVIADO) {
            throw new IllegalStateException("Não é possível finalizar um pedido que não foi enviado");
        }
        
        this.atualizarStatus(StatusPedido.ENTREGUE);
    }
    
    public void cancelar() {
        if (!this.status.permiteAlteracao()) {
            throw new IllegalStateException("Não é possível cancelar um pedido com status: " + this.status);
        }
        
        this.atualizarStatus(StatusPedido.CANCELADO);
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }
    
    public List<ItemPedido> getItens() {
        return new ArrayList<>(itens);
    }
    
    public Endereco getEnderecoEntrega() {
        return enderecoEntrega;
    }
    
    public Pagamento getPagamento() {
        return pagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }
} 