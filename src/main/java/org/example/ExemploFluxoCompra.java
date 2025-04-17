package org.example;

import org.example.enums.FormaPagamento;
import org.example.enums.StatusPedido;
import org.example.model.*;

public class ExemploFluxoCompra {

    public static void main(String[] args) {
        // Criando produtos
        Produto camiseta = new Produto("Camiseta", "Camiseta de algodão", 50.0, 10, "Vestuário");
        Produto calca = new Produto("Calça Jeans", "Calça jeans slim", 120.0, 5, "Vestuário");
        Produto tenis = new Produto("Tênis", "Tênis esportivo", 200.0, 3, "Calçados");

        // Criando cliente
        Cliente cliente = new Cliente("João Silva", "joao@email.com", "11987654321", true);

        // Adicionando endereço ao cliente
        Endereco endereco = new Endereco("Rua das Flores", "123", "Apto 101", "Centro", "São Paulo", "SP", "01234-567");
        cliente.adicionarEndereco(endereco);

        // Criando carrinho de compras
        CarrinhoCompras carrinho = new CarrinhoCompras(cliente);

        // Adicionando produtos ao carrinho
        System.out.println("Adicionando produtos ao carrinho...");
        carrinho.adicionarItem(camiseta, 2);
        carrinho.adicionarItem(calca, 1);
        System.out.println("Total do carrinho: R$" + carrinho.getTotal());

        // Finalizando a compra
        System.out.println("\nFinalizando a compra...");
        Pedido pedido = carrinho.finalizarCompra(endereco, FormaPagamento.CARTAO_CREDITO);

        // Exibindo informações do pedido
        System.out.println("\nPedido realizado com sucesso!");
        System.out.println("Código do pedido: " + pedido.getCodigo());
        System.out.println("Status: " + pedido.getStatus());
        System.out.println("Data: " + pedido.getDataCriacao());
        System.out.println("Endereço de entrega: " + pedido.getEnderecoEntrega().getEnderecoCompleto());
        System.out.println("Forma de pagamento: " + pedido.getPagamento().getFormaPagamento());
        System.out.println("Total: R$" + pedido.getTotal());

        System.out.println("\nItens do pedido:");
        for (ItemPedido item : pedido.getItens()) {
            System.out.println("- " + item.getProduto().getNome() +
                    " (x" + item.getQuantidade() + "): R$" +
                    item.getSubtotal());
        }

        // Atualizando status do pedido
        System.out.println("\nAtualizando status do pedido...");
        pedido.atualizarStatus(StatusPedido.ENVIADO);
        System.out.println("Novo status: " + pedido.getStatus());

        // Finalizando pedido (entrega)
        System.out.println("\nFinalizando pedido (entrega realizada)...");
        pedido.finalizar();
        System.out.println("Status final: " + pedido.getStatus());
    }
}