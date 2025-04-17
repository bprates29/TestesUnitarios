package org.example.model;

public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco(String rua, String numero, String bairro, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public Endereco(String rua, String numero, String complemento, String bairro, String cidade, String estado, String cep) {
        this(rua, numero, bairro, cidade, estado, cep);
        this.complemento = complemento;
    }

    public String getEnderecoCompleto() {
        return rua + ", " + numero + (complemento != null ? ", " + complemento : "") +
               " - " + bairro + ", " + cidade + " - " + estado + ", " + cep;
    }
} 