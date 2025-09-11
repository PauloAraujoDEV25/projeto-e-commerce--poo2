package com.adacommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VendaDTO {
    private Long id;
    private LocalDateTime dataCriacao;
    private Long clienteId;
    private String clienteNome;
    private String status;
    private BigDecimal valorTotal;
    private List<ItemVendaDTO> itens;
    
    public VendaDTO() {}
    
    public VendaDTO(Long id, LocalDateTime dataCriacao, Long clienteId, String clienteNome,
                   String status, BigDecimal valorTotal, List<ItemVendaDTO> itens) {
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.status = status;
        this.valorTotal = valorTotal;
        this.itens = itens;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    public Long getClienteId() {
        return clienteId;
    }
    
    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }
    
    public String getClienteNome() {
        return clienteNome;
    }
    
    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
    
    public List<ItemVendaDTO> getItens() {
        return itens;
    }
    
    public void setItens(List<ItemVendaDTO> itens) {
        this.itens = itens;
    }
    
    @Override
    public String toString() {
        return "VendaDTO{" +
                "id=" + id +
                ", dataCriacao=" + dataCriacao +
                ", clienteId=" + clienteId +
                ", clienteNome='" + clienteNome + '\'' +
                ", status='" + status + '\'' +
                ", valorTotal=" + valorTotal +
                ", quantidadeItens=" + (itens != null ? itens.size() : 0) +
                '}';
    }
}
