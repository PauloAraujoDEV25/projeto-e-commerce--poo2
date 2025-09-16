package com.adacommerce.model;

import java.util.Objects;

public class Cliente {
    private Long id;
    private String nome;
    private String documento;
    private String email;
    private StatusRegistro status = StatusRegistro.ATIVO;
    private TipoDocumento tipoDocumento = TipoDocumento.CPF;
    
    public Cliente() {}
    
    public Cliente(Long id, String nome, String documento, String email) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
        this.email = email;
        this.status = StatusRegistro.ATIVO;
        this.tipoDocumento = TipoDocumento.CPF;
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
    
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public StatusRegistro getStatus() {
        return status;
    }
    
    public void setStatus(StatusRegistro status) {
        this.status = status;
    }
    
    public boolean isAtivo() {
        return this.status == StatusRegistro.ATIVO;
    }
    
    public void ativar() {
        this.status = StatusRegistro.ATIVO;
    }
    
    public void inativar() {
        this.status = StatusRegistro.INATIVO;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) && Objects.equals(documento, cliente.documento);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, documento);
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", documento='" + documento + '\'' +
        ", email='" + email + '\'' +
        ", status=" + status +
        ", tipoDocumento=" + tipoDocumento +
                '}';
    }
}
