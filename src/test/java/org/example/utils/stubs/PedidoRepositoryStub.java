package org.example.utils.stubs;

import org.example.model.Cliente;
import org.example.model.Pedido;
import org.example.repository.PedidoRepository;

import java.util.List;

public class PedidoRepositoryStub implements PedidoRepository {

    private Pedido pedidoStub;
    private Pedido pedidoStubPadrao = new Pedido( new Cliente("Nome Padrao", true));

    public void setPedidoStub (Pedido pedido) {
        this.pedidoStub = pedido;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return pedido; //comportamento pradrão;
    }

    @Override
    public Pedido buscarPorCodigo(String codigo) {
        return pedidoStub; // devolve sempre o mesmo
    }

    @Override
    public List<Pedido> buscarPorCliente(Cliente cliente) {
        return List.of(pedidoStub); // simular resultado
    }
}
