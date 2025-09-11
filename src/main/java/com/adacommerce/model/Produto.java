package com.adacommerce.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private Long id;
    private String nome;
    private String etiqueta;
    private BigDecimal valorProduto;
    
    public Produto() {}
    
    public Produto(Long id, String nome, String etiqueta, BigDecimal valorProduto) {
        this.id = id;
        this.nome = nome;
        this.etiqueta = etiqueta;
        this.valorProduto = valorProduto;
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Objects.equals(id, produto.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", etiqueta='" + etiqueta + '\'' +
                ", valorProduto=" + valorProduto +
                '}';
    }
}
