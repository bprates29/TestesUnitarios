package org.example.service;

import org.example.enums.StatusPedido;
import org.example.model.Cliente;
import org.example.model.Pedido;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServicoPedido {
    private Map<String, Pedido> pedidos = new HashMap<>();
    
    public Pedido criarPedido(Cliente cliente) {
        if (!cliente.isAtivo()) {
            throw new IllegalArgumentException("Cliente inativo não pode criar pedidos");
        }
        
        Pedido pedido = new Pedido(cliente);
        pedidos.put(pedido.getCodigo(), pedido);
        
        return pedido;
    }
    
    public Pedido buscarPedido(String codigo) {
        Pedido pedido = pedidos.get(codigo);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado: " + codigo);
        }
        return pedido;
    }
    
    public List<Pedido> buscarPedidosCliente(Cliente cliente) {
        List<Pedido> pedidosCliente = new ArrayList<>();
        
        for (Pedido pedido : pedidos.values()) {
            if (pedido.getCliente().equals(cliente)) {
                pedidosCliente.add(pedido);
            }
        }
        
        return pedidosCliente;
    }
    
    public void atualizarStatusPedido(String codigoPedido, StatusPedido novoStatus) {
        Pedido pedido = buscarPedido(codigoPedido);
        pedido.atualizarStatus(novoStatus);
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