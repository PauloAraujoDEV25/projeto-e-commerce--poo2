package com.adacommerce.dto;

import java.math.BigDecimal;

public class ItemVendaRequestDTO {
    private Long produtoId;
    private Integer quantidade;
    private BigDecimal valorVenda;
    
    public ItemVendaRequestDTO() {}
    
    public ItemVendaRequestDTO(Long produtoId, Integer quantidade, BigDecimal valorVenda) {
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
    }
    
    // Getters e Setters
    public Long getProdutoId() {
        return produtoId;
    }
    
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
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
    public String toString() {
        return "ItemVendaRequestDTO{" +
                "produtoId=" + produtoId +
                ", quantidade=" + quantidade +
                ", valorVenda=" + valorVenda +
                '}';
    }
}
