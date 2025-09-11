package com.adacommerce.dto;

import java.math.BigDecimal;

public class ItemVendaDTO {
    private Long id;
    private Long produtoId;
    private String produtoNome;
    private Integer quantidade;
    private BigDecimal valorVenda;
    private BigDecimal valorTotal;
    
    public ItemVendaDTO() {}
    
    public ItemVendaDTO(Long id, Long produtoId, String produtoNome, Integer quantidade, 
                       BigDecimal valorVenda, BigDecimal valorTotal) {
        this.id = id;
        this.produtoId = produtoId;
        this.produtoNome = produtoNome;
        this.quantidade = quantidade;
        this.valorVenda = valorVenda;
        this.valorTotal = valorTotal;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProdutoId() {
        return produtoId;
    }
    
    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }
    
    public String getProdutoNome() {
        return produtoNome;
    }
    
    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
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
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    @Override
    public String toString() {
        return "ItemVendaDTO{" +
                "id=" + id +
                ", produtoId=" + produtoId +
                ", produtoNome='" + produtoNome + '\'' +
                ", quantidade=" + quantidade +
                ", valorVenda=" + valorVenda +
                ", valorTotal=" + valorTotal +
                '}';
    }
}
