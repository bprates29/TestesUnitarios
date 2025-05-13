package org.example.service;

import org.example.enums.StatusPedido;
import org.example.model.Cliente;
import org.example.model.Pedido;
import org.example.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.List;

public class ServicoPedido {

    private final PedidoRepository repository;

    public ServicoPedido(PedidoRepository repository) {
        this.repository = repository;
    }
    
    public Pedido criarPedido(Cliente cliente) {
        if (!cliente.isAtivo()) {
            throw new IllegalArgumentException("Cliente inativo não pode criar pedidos");
        }
        
        Pedido pedido = new Pedido(cliente);
        return repository.salvar(pedido);
    }
    
    public Pedido buscarPedido(String codigo) {
        return repository.buscarPorCodigo(codigo);
    }
    
    public List<Pedido> buscarPedidosCliente(Cliente cliente) {
        return repository.buscarPorCliente(cliente);
    }
    
    public void atualizarStatusPedido(String codigoPedido, StatusPedido novoStatus) {
        Pedido pedido = buscarPedido(codigoPedido);
        pedido.atualizarStatus(novoStatus);
        repository.salvar(pedido);
    }
    
    public void cancelarPedido(String codigoPedido) {
        Pedido pedido = buscarPedido(codigoPedido);
        pedido.cancelar();
    }
    
    public void finalizarPedido(String codigoPedido) {
        Pedido pedido = buscarPedido(codigoPedido);
        pedido.finalizar();
    }
} 