package org.example.service;

import org.example.enums.StatusPedido;
import org.example.model.Cliente;
import org.example.model.Pedido;
import org.example.repository.PedidoRepository;
import org.example.utils.fakes.PedidoRepositoryFake;
import org.example.utils.stubs.PedidoRepositoryStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class ServicoPedidoTest {

    private Cliente dummyCliente;

    @BeforeEach
    void setup() {
        dummyCliente = new Cliente("Dummy Cliente", true);
    }

    @Test
    void deveCriarPedidoComSucessoComClienteAtico() {
        // Dummy: Só para satisfazer o parametro, sem lógica extra
        Cliente dummyCliente = new Cliente("Dummy Cliente", true);

        PedidoRepositoryFake fake = new PedidoRepositoryFake();
        ServicoPedido servicoPedido = new ServicoPedido(fake);
        Pedido pedido = servicoPedido.criarPedido(dummyCliente);

        assertNotNull(pedido);
        assertEquals(dummyCliente, pedido.getCliente());
        assertNotNull(pedido.getCodigo());
    }

    @Test
    void deveCriarEPersistirPedidoComFakeRepository() {
        PedidoRepositoryFake fake = new PedidoRepositoryFake();
        ServicoPedido servicoPedido = new ServicoPedido(fake);

        Pedido pedidoCriado = servicoPedido.criarPedido(dummyCliente);

        Pedido pedidoBuscado = servicoPedido.buscarPedido(pedidoCriado.getCodigo());

        assertEquals(pedidoCriado, pedidoBuscado);
        assertEquals(dummyCliente, pedidoBuscado.getCliente());
    }

    @Test
    void deveAtualizarStatusPedidoUsandoStub() {
        Pedido pedido = new Pedido(dummyCliente);
        pedido.atualizarStatus(StatusPedido.CRIADO);

        PedidoRepositoryStub stub = new PedidoRepositoryStub();
        stub.setPedidoStub2(pedido);

        ServicoPedido servicoPedido = new ServicoPedido(stub);

        servicoPedido.atualizarStatusPedido(pedido.getCodigo(), StatusPedido.ENVIADO);

        assertEquals(StatusPedido.ENVIADO, pedido.getStatus());
    }

    @Test
    void deveChamarSalvarOuAtualizarStatus() {
        Pedido pedido = new Pedido(dummyCliente);
        PedidoRepositoryFake repositoryFake = new PedidoRepositoryFake();
        repositoryFake.salvar(pedido);

        PedidoRepository spy = Mockito.spy(repositoryFake);
        ServicoPedido servicoPedido = new ServicoPedido(spy);

        servicoPedido.atualizarStatusPedido(pedido.getCodigo(), StatusPedido.ENVIADO);

        Mockito.verify(spy).salvar(pedido);
        Mockito.verify(spy).buscarPorCodigo(pedido.getCodigo());
    }

}