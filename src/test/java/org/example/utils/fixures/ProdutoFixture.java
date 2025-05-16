package org.example.utils.fixures;

import org.example.model.Produto;
import org.example.utils.builders.PedidoBuilder;
import org.example.utils.builders.ProdutoBuilder;

public class ProdutoFixture {
    public static Produto camisetaBarata() {
        return new ProdutoBuilder()
                .comNome("Camiseta")
                .comPreco(50.0)
                .build();
    }

}
