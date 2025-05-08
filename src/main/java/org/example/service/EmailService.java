package org.example.service;

import org.example.model.Pedido;

public class EmailService {

    public void enviarEmail(String email, Pedido pedido) {
        System.out.println("Enviando email de confirmação..." + email + pedido);
    }

}
