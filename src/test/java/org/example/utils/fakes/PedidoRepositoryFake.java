package org.example.utils.fakes;

import org.example.model.Cliente;
import org.example.model.Pedido;
import org.example.repository.PedidoRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PedidoRepositoryFake implements PedidoRepository {

    private final Map<String, Pedido> pedidos = new HashMap<>();

    @Override
    public Pedido salvar(Pedido pedido) {
        pedidos.put(pedido.getCodigo(), pedido);
        return pedido;
    }

    @Override
    public Pedido buscarPorCodigo(String codigo) {
        Pedido pedido = pedidos.get(codigo);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado!");
        }
        return pedido;
    }

    @Override
    public List<Pedido> buscarPorCliente(Cliente cliente) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido pedido : pedidos.values()) {
            if (pedido.getCliente().equals(cliente)){
                resultado.add(pedido);
            }
        }
        return resultado;
    }
}
