package com.adacommerce.dto;

import java.math.BigDecimal;

public class ProdutoRequestDTO {
    private String nome;
    private String etiqueta;
    private BigDecimal valorProduto;
    
    public ProdutoRequestDTO() {}
    
    public ProdutoRequestDTO(String nome, String etiqueta, BigDecimal valorProduto) {
        this.nome = nome;
        this.etiqueta = etiqueta;
        this.valorProduto = valorProduto;
    }
    
    // Getters e Setters
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
    
    @Override
    public String toString() {
        return "ProdutoRequestDTO{" +
                "nome='" + nome + '\'' +
                ", etiqueta='" + etiqueta + '\'' +
                ", valorProduto=" + valorProduto +
                '}';
    }
}
