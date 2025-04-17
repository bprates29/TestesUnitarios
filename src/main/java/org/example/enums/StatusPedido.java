package org.example.enums;

public enum StatusPedido {
    CRIADO,
    AGUARDANDO_PAGAMENTO,
    PAGO,
    EM_PREPARACAO,
    ENVIADO,
    ENTREGUE,
    CANCELADO;
    
    public boolean permiteAlteracao() {
        return this == CRIADO || this == AGUARDANDO_PAGAMENTO;
    }
    
    public boolean estaConcluido() {
        return this == ENTREGUE;
    }
    
    public boolean estaCancelado() {
        return this == CANCELADO;
    }
} 