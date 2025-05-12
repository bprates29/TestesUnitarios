package org.example.utils.stubs;

import org.example.model.Cliente;
import org.example.model.Pedido;
import org.example.repository.PedidoRepository;

import java.util.List;

public class PedidoRepositoryStub implements PedidoRepository {

    private Pedido pedidoStub1 = new Pedido(new Cliente("Dummy Cliente", true));
    private Pedido pedidoStub2;

    public void setPedidoStub2(Pedido pedido) {
        this.pedidoStub2 = pedido;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return pedido; // comportamento pradrão
    }

    @Override
    public Pedido buscarPorCodigo(String codigo) {
        return pedidoStub2;
    }

    @Override
    public List<Pedido> buscarPorCliente(Cliente cliente) {
        return List.of(pedidoStub2);
    }
}
