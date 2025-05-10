package org.example.service;

import org.example.model.Pedido;

public class EnviarEmailService {
    public void enviarConfirmacao(String email, Pedido pedido) {
        System.out.println("Email enviado com sucesso: " + email + pedido.getDataAtualizacao());
    }
}
