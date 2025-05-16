package org.example.utils.asserts;

import org.example.enums.StatusPedido;
import org.example.model.Pedido;
import org.junit.jupiter.api.Assertions;

public class PedidoAssertions {

    public static void assertStatus(Pedido pedido, StatusPedido esperado) {
        Assertions.assertEquals(esperado, pedido.getStatus(),
                () -> String.format("Esperava status <%s>, mas era <%s>", esperado, pedido.getStatus()));
    }

    public static void assertTotal(Pedido pedido, double esperado) {
        double delta = 0.0001;
        Assertions.assertEquals(esperado, pedido.getTotal(), delta,
                () -> String.format("Esperava total %.2f, mas era %.2f", esperado, pedido.getTotal()));
    }

    public static void assertPedido(Pedido pedido, StatusPedido statusEsperado, double totalEsperado) {
        Assertions.assertAll("validando pedido",
                () -> assertStatus(pedido, statusEsperado),
                () -> assertTotal(pedido, totalEsperado));
    }

}
