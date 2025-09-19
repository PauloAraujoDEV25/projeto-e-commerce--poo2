package com.adacommerce.service;

import com.adacommerce.model.*;
import com.adacommerce.repository.VendaRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class VendaService {
    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final NotificacaoService notificacaoService;
    private final AtomicLong itemIdGenerator = new AtomicLong(1);
    
    public VendaService(VendaRepository vendaRepository, 
                       ClienteService clienteService,
                       ProdutoService produtoService,
                       NotificacaoService notificacaoService) {
        this.vendaRepository = vendaRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.notificacaoService = notificacaoService;
    }
    
    public Venda criarVenda(Long clienteId) {
        Optional<Cliente> cliente = clienteService.buscarPorId(clienteId);
        if (cliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + clienteId);
        }
        
        Venda venda = new Venda();
        venda.setCliente(cliente.get());
        
        return vendaRepository.salvar(venda);
    }
    
    public void adicionarItem(Long vendaId, Long produtoId, Integer quantidade, BigDecimal valorVenda) {
        Venda venda = buscarVendaPorId(vendaId);
        Produto produto = buscarProdutoPorId(produtoId);
        
        validarQuantidade(quantidade);
        validarValorVenda(valorVenda);
        
        ItemVenda item = new ItemVenda();
        item.setId(itemIdGenerator.getAndIncrement());
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        item.setValorVenda(valorVenda);
        
        venda.adicionarItem(item);
        vendaRepository.atualizar(venda);
    }
    
    public void removerItem(Long vendaId, Long itemId) {
        Venda venda = buscarVendaPorId(vendaId);
        
        ItemVenda itemParaRemover = venda.getItens().stream()
            .filter(item -> item.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Item não encontrado na venda"));
            
        venda.removerItem(itemParaRemover);
        vendaRepository.atualizar(venda);
    }
    
    public void alterarQuantidadeItem(Long vendaId, Long itemId, Integer novaQuantidade) {
        Venda venda = buscarVendaPorId(vendaId);
        validarQuantidade(novaQuantidade);
        
        venda.alterarQuantidadeItem(itemId, novaQuantidade);
        vendaRepository.atualizar(venda);
    }
    
    public void finalizarPedido(Long vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        venda.finalizarPedido();
        vendaRepository.atualizar(venda);
        
        // Notificar cliente
        notificacaoService.notificarPedidoFinalizado(venda.getCliente(), venda);
    }
    
    public void realizarPagamento(Long vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        venda.realizarPagamento();
        vendaRepository.atualizar(venda);
        
        // Notificar cliente
        notificacaoService.notificarPagamentoRealizado(venda.getCliente(), venda);
    }
    
    public void entregar(Long vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        venda.entregar();
        vendaRepository.atualizar(venda);
        
        // Notificar cliente
        notificacaoService.notificarEntregaRealizada(venda.getCliente(), venda);
    }
    
    public List<Venda> listarTodas() {
        return vendaRepository.listarTodas();
    }
    
    public List<Venda> buscarPorCliente(Long clienteId) {
        Optional<Cliente> cliente = clienteService.buscarPorId(clienteId);
        if (cliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + clienteId);
        }
        return vendaRepository.buscarPorCliente(cliente.get());
    }
    
    public List<Venda> buscarPorStatus(StatusVenda status) {
        return vendaRepository.buscarPorStatus(status);
    }
    
    public Optional<Venda> buscarPorId(Long id) {
        return vendaRepository.buscarPorId(id);
    }
    
    private Venda buscarVendaPorId(Long vendaId) {
        return vendaRepository.buscarPorId(vendaId)
            .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada com ID: " + vendaId));
    }
    
    private Produto buscarProdutoPorId(Long produtoId) {
        return produtoService.buscarPorId(produtoId)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + produtoId));
    }
    
    private void validarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
    }
    
    private void validarValorVenda(BigDecimal valorVenda) {
        if (valorVenda == null || valorVenda.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de venda deve ser maior que zero");
        }
    }
}
