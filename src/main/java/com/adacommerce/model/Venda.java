package com.adacommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Venda {
    private Long id;
    private LocalDateTime dataCriacao;
    private Cliente cliente;
    private StatusVenda status;
    private BigDecimal valorTotal;
    private List<ItemVenda> itens;
    
    public Venda() {
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusVenda.ABERTO;
        this.itens = new ArrayList<>();
        this.valorTotal = BigDecimal.ZERO;
    }
    
    public Venda(Long id, Cliente cliente) {
        this();
        this.id = id;
        this.cliente = cliente;
    }
    
    public void adicionarItem(ItemVenda item) {
        if (this.status != StatusVenda.ABERTO) {
            throw new IllegalStateException("Só é possível adicionar itens em vendas com status ABERTO");
        }
        this.itens.add(item);
        calcularValorTotal();
    }
    
    public void removerItem(ItemVenda item) {
        if (this.status != StatusVenda.ABERTO) {
            throw new IllegalStateException("Só é possível remover itens em vendas com status ABERTO");
        }
        this.itens.remove(item);
        calcularValorTotal();
    }
    
    public void alterarQuantidadeItem(Long itemId, Integer novaQuantidade) {
        if (this.status != StatusVenda.ABERTO) {
            throw new IllegalStateException("Só é possível alterar quantidade em vendas com status ABERTO");
        }
        
        ItemVenda item = this.itens.stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Item não encontrado na venda"));
            
        item.setQuantidade(novaQuantidade);
        calcularValorTotal();
    }
    
    public void finalizarPedido() {
        if (this.itens.isEmpty() || this.valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalStateException("Para finalizar o pedido deve ter ao menos um item e valor maior que zero");
        }
        this.status = StatusVenda.AGUARDANDO_PAGAMENTO;
    }
    
    public void realizarPagamento() {
        if (this.status != StatusVenda.AGUARDANDO_PAGAMENTO) {
            throw new IllegalStateException("Só é possível pagar vendas com status AGUARDANDO_PAGAMENTO");
        }
        this.status = StatusVenda.PAGO;
    }
    
    public void entregar() {
        if (this.status != StatusVenda.PAGO) {
            throw new IllegalStateException("Só é possível entregar vendas com status PAGO");
        }
        this.status = StatusVenda.FINALIZADO;
    }
    
    private void calcularValorTotal() {
        this.valorTotal = this.itens.stream()
            .map(ItemVenda::getValorTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
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
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public StatusVenda getStatus() {
        return status;
    }
    
    public void setStatus(StatusVenda status) {
        this.status = status;
    }
    
    public BigDecimal getValorTotal() {
        return valorTotal;
    }
    
    public List<ItemVenda> getItens() {
        return new ArrayList<>(itens);
    }
    
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens != null ? new ArrayList<>(itens) : new ArrayList<>();
        calcularValorTotal();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Objects.equals(id, venda.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", dataCriacao=" + dataCriacao +
                ", cliente=" + cliente +
                ", status=" + status +
                ", valorTotal=" + valorTotal +
                ", quantidadeItens=" + itens.size() +
                '}';
    }
}
