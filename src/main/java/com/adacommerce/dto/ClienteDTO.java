package com.adacommerce.dto;

public class ClienteDTO {
    private Long id;
    private String nome;
    private String documento;
    private String email;
    private String status; // ATIVO / INATIVO
    
    public ClienteDTO() {}
    
    public ClienteDTO(Long id, String nome, String documento, String email) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.email = email;
    }
    public ClienteDTO(Long id, String nome, String documento, String email, String status) {
        this(id, nome, documento, email);
        this.status = status;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "ClienteDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
        ", email='" + email + '\'' +
        ", status=" + status +
                '}';
    }
}
