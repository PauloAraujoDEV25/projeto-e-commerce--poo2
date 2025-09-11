package com.adacommerce.service;

import com.adacommerce.dto.*;
import com.adacommerce.mapper.*;
import com.adacommerce.model.*;
import com.adacommerce.repository.VendaRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class VendaServiceDTO {
    private final VendaRepository vendaRepository;
    private final ClienteService clienteService;
    private final ProdutoService produtoService;
    private final NotificacaoService notificacaoService;
    private final AtomicLong itemIdGenerator = new AtomicLong(1);
    
    public VendaServiceDTO(VendaRepository vendaRepository, 
                          ClienteService clienteService,
                          ProdutoService produtoService,
                          NotificacaoService notificacaoService) {
        this.vendaRepository = vendaRepository;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
        this.notificacaoService = notificacaoService;
    }
    
    public VendaDTO criarVenda(Long clienteId) {
        Optional<Cliente> cliente = clienteService.buscarPorId(clienteId);
        if (cliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + clienteId);
        }
        
        Venda venda = new Venda();
        venda.setCliente(cliente.get());
        
        Venda vendaSalva = vendaRepository.salvar(venda);
        return VendaMapper.toDTO(vendaSalva);
    }
    
    public VendaDTO adicionarItem(Long vendaId, ItemVendaRequestDTO itemRequestDTO) {
        Venda venda = buscarVendaPorId(vendaId);
        Produto produto = buscarProdutoPorId(itemRequestDTO.getProdutoId());
        
        validarQuantidade(itemRequestDTO.getQuantidade());
        validarValorVenda(itemRequestDTO.getValorVenda());
        
        ItemVenda item = ItemVendaMapper.toEntity(itemRequestDTO, produto);
        item.setId(itemIdGenerator.getAndIncrement());
        
        venda.adicionarItem(item);
        Venda vendaAtualizada = vendaRepository.atualizar(venda);
        
        return VendaMapper.toDTO(vendaAtualizada);
    }
    
    public VendaDTO removerItem(Long vendaId, Long itemId) {
        Venda venda = buscarVendaPorId(vendaId);
        
        ItemVenda item = venda.getItens().stream()
            .filter(i -> i.getId().equals(itemId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Item não encontrado na venda"));
        
        venda.removerItem(item);
        Venda vendaAtualizada = vendaRepository.atualizar(venda);
        
        return VendaMapper.toDTO(vendaAtualizada);
    }
    
    public VendaDTO alterarQuantidadeItem(Long vendaId, Long itemId, Integer novaQuantidade) {
        validarQuantidade(novaQuantidade);
        
        Venda venda = buscarVendaPorId(vendaId);
        venda.alterarQuantidadeItem(itemId, novaQuantidade);
        
        Venda vendaAtualizada = vendaRepository.atualizar(venda);
        return VendaMapper.toDTO(vendaAtualizada);
    }
    
    public VendaDTO finalizarPedido(Long vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        venda.finalizarPedido();
        
        Venda vendaAtualizada = vendaRepository.atualizar(venda);
        
        // Notificar cliente
        notificacaoService.notificarPedidoFinalizado(venda.getCliente(), venda);
        
        return VendaMapper.toDTO(vendaAtualizada);
    }
    
    public VendaDTO realizarPagamento(Long vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        venda.realizarPagamento();
        
        Venda vendaAtualizada = vendaRepository.atualizar(venda);
        
        // Notificar cliente
        notificacaoService.notificarPagamentoRealizado(venda.getCliente(), venda);
        
        return VendaMapper.toDTO(vendaAtualizada);
    }
    
    public VendaDTO entregarPedido(Long vendaId) {
        Venda venda = buscarVendaPorId(vendaId);
        venda.entregar();
        
        Venda vendaAtualizada = vendaRepository.atualizar(venda);
        
        // Notificar cliente
        notificacaoService.notificarEntregaRealizada(venda.getCliente(), venda);
        
        return VendaMapper.toDTO(vendaAtualizada);
    }
    
    public Optional<VendaDTO> buscarPorId(Long id) {
        return vendaRepository.buscarPorId(id)
            .map(VendaMapper::toDTO);
    }
    
    public List<VendaDTO> listarTodas() {
        return vendaRepository.listarTodas().stream()
            .map(VendaMapper::toDTOSemItens)
            .collect(Collectors.toList());
    }
    
    public List<VendaDTO> buscarPorCliente(Long clienteId) {
        Optional<Cliente> cliente = clienteService.buscarPorId(clienteId);
        if (cliente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + clienteId);
        }
        
        return vendaRepository.buscarPorCliente(cliente.get()).stream()
            .map(VendaMapper::toDTOSemItens)
            .collect(Collectors.toList());
    }
    
    public List<VendaDTO> buscarPorStatus(StatusVenda status) {
        return vendaRepository.buscarPorStatus(status).stream()
            .map(VendaMapper::toDTOSemItens)
            .collect(Collectors.toList());
    }
    
    // Métodos privados auxiliares
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
