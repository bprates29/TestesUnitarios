package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Cliente {
    private String nome;
    private boolean ativo;
    private String email;
    private String telefone;
    private List<Endereco> enderecos;

    public Cliente(String nome, boolean ativo) {
        this.nome = nome;
        this.ativo = ativo;
        this.enderecos = new ArrayList<>();
    }
    
    public Cliente(String nome, String email, String telefone, boolean ativo) {
        this(nome, ativo);
        this.email = email;
        this.telefone = telefone;
    }
    
    public void adicionarEndereco(Endereco endereco) {
        this.enderecos.add(endereco);
    }
    
    public List<Endereco> getEnderecos() {
        return new ArrayList<>(enderecos);
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }

    public boolean isAtivo() {
        return ativo;
    }
} 