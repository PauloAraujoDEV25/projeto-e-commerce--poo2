package com.adacommerce.model;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemVenda {
    private Long id;
    private Produto produto;
    private Integer quantidade;
    private BigDecimal valorVenda;
    
    public ItemVenda() {}
    
    public ItemVenda(Long id, Produto produto, Integer quantidade, BigDecimal valorVenda) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
    }
    
    public BigDecimal getValorTotal() {
        if (quantidade != null && valorVenda != null) {
            return valorVenda.multiply(BigDecimal.valueOf(quantidade));
        }
        return BigDecimal.ZERO;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public Integer getQuantidade() {
        return quantidade;
    }
    
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
    
    public BigDecimal getValorVenda() {
        return valorVenda;
    }
    
    public void setValorVenda(BigDecimal valorVenda) {
        this.valorVenda = valorVenda;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemVenda itemVenda = (ItemVenda) o;
        return Objects.equals(id, itemVenda.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "ItemVenda{" +
                "id=" + id +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", valorVenda=" + valorVenda +
                ", valorTotal=" + getValorTotal() +
                '}';
    }
}
