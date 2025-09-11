package com.adacommerce.dto;

public class ClienteRequestDTO {
    private String nome;
    private String documento;
    private String email;
    
    public ClienteRequestDTO() {}
    
    public ClienteRequestDTO(String nome, String documento, String email) {
        this.nome = nome;
        this.documento = documento;
        this.email = email;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getDocumento() {
        return documento;
    }
    
    public void setDocumento(String documento) {
        this.documento = documento;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "ClienteRequestDTO{" +
                "nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
