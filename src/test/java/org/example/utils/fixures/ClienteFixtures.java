package org.example.utils.fixures;

import org.example.model.Cliente;
import org.example.model.Endereco;

public class ClienteFixtures {
    public static Cliente criarClientePadrao() {
        Cliente cliente = new Cliente("Maria Silva", "maria@email.com", "11987654322", true);
        cliente.adicionarEndereco(new Endereco("Rua das Árvores", "456", "Apto 202", "Jardim", "São Paulo", "SP", "01234-568"));
        return cliente;
    }
}
