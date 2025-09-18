package com.adacommerce.dto;

import java.math.BigDecimal;

public class ProdutoDTO {
    private Long id;
    private String nome;
    private String etiqueta;
    private BigDecimal valorProduto;
    private String status; // ATIVO / INATIVO
    
    public ProdutoDTO() {}
    
    public ProdutoDTO(Long id, String nome, String etiqueta, BigDecimal valorProduto) {
        this.id = id;
        this.nome = nome;
        this.etiqueta = etiqueta;
        this.valorProduto = valorProduto;
    }
    public ProdutoDTO(Long id, String nome, String etiqueta, BigDecimal valorProduto, String status) {
        this(id, nome, etiqueta, valorProduto);
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
    
    public String getEtiqueta() {
        return etiqueta;
    }
    
    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
    
    public BigDecimal getValorProduto() {
        return valorProduto;
    }
    
    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "ProdutoDTO{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", etiqueta='" + etiqueta + '\'' +
        ", valorProduto=" + valorProduto +
        ", status=" + status +
                '}';
    }
}
