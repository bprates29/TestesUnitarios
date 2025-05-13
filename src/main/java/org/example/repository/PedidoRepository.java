package org.example.repository;

import org.example.model.Cliente;
import org.example.model.Pedido;

import java.util.List;

public interface PedidoRepository {
    Pedido salvar(Pedido pedido);
    Pedido buscarPorCodigo(String codigo);
    List<Pedido> buscarPorCliente(Cliente cliente);
}
