package org.example.utils.fixures;

import org.example.model.Cliente;
import org.example.model.Endereco;

public class ClienteFixures {
    public static Cliente criarClientePadrao() {
        Cliente cliente = new Cliente("Marina Silva", "marina@matina", "1231231546", true);
        cliente.adicionarEndereco(new Endereco("Rua das Flores", "123", "Apto 101", "Centro", "São Paulo", "SP", "01234-567"));
        return cliente;
    }

    public static Cliente criarClienteInativo() {
        Cliente cliente = new Cliente("Marina Silva", "marina@matina", "1231231546", false);
        cliente.adicionarEndereco(new Endereco("Rua das Flores", "123", "Apto 101", "Centro", "São Paulo", "SP", "01234-567"));
        return cliente;
    }
}
