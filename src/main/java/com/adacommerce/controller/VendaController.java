package com.adacommerce.controller;

import com.adacommerce.model.*;
import com.adacommerce.service.VendaService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class VendaController {
    private final VendaService vendaService;
    
    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }
    
    public VendaService getVendaService() {
        return vendaService;
    }
    
    public void criarVenda() {
        System.out.println("\n=== CRIAR NOVA VENDA ===");
        System.out.print("ID do cliente: ");
        Long clienteId = Long.parseLong(System.console().readLine());
        
        try {
            Venda venda = vendaService.criarVenda(clienteId);
            System.out.println("Venda criada com sucesso!");
            System.out.println(venda);
        } catch (Exception e) {
            System.err.println("Erro ao criar venda: " + e.getMessage());
        }
    }
    
    public void adicionarItem() {
        System.out.println("\n=== ADICIONAR ITEM À VENDA ===");
        System.out.print("ID da venda: ");
        Long vendaId = Long.parseLong(System.console().readLine());
        
        System.out.print("ID do produto: ");
        Long produtoId = Long.parseLong(System.console().readLine());
        
        System.out.print("Quantidade: ");
        Integer quantidade = Integer.parseInt(System.console().readLine());
        
        System.out.print("Valor de venda: ");
        BigDecimal valorVenda = new BigDecimal(System.console().readLine());
        
        try {
            vendaService.adicionarItem(vendaId, produtoId, quantidade, valorVenda);
            System.out.println("Item adicionado com sucesso!");
            
            // Mostrar venda atualizada
            Optional<Venda> venda = vendaService.buscarPorId(vendaId);
            if (venda.isPresent()) {
                mostrarDetalhesVenda(venda.get());
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar item: " + e.getMessage());
        }
    }
    
    public void removerItem() {
        System.out.println("\n=== REMOVER ITEM DA VENDA ===");
        System.out.print("ID da venda: ");
        Long vendaId = Long.parseLong(System.console().readLine());
        
        // Mostrar itens da venda
        Optional<Venda> venda = vendaService.buscarPorId(vendaId);
        if (venda.isEmpty()) {
            System.err.println("Venda não encontrada!");
            return;
        }
        
        System.out.println("Itens da venda:");
        for (ItemVenda item : venda.get().getItens()) {
            System.out.println("ID: " + item.getId() + " - " + item.getProduto().getNome() + 
                             " - Qtd: " + item.getQuantidade() + " - Valor: R$ " + item.getValorVenda());
        }
        
        System.out.print("ID do item a remover: ");
        Long itemId = Long.parseLong(System.console().readLine());
        
        try {
            vendaService.removerItem(vendaId, itemId);
            System.out.println("Item removido com sucesso!");
            
            // Mostrar venda atualizada
            Optional<Venda> vendaAtualizada = vendaService.buscarPorId(vendaId);
            if (vendaAtualizada.isPresent()) {
                mostrarDetalhesVenda(vendaAtualizada.get());
            }
        } catch (Exception e) {
            System.err.println("Erro ao remover item: " + e.getMessage());
        }
    }
    
    public void alterarQuantidadeItem() {
        System.out.println("\n=== ALTERAR QUANTIDADE DO ITEM ===");
        System.out.print("ID da venda: ");
        Long vendaId = Long.parseLong(System.console().readLine());
        
        // Mostrar itens da venda
        Optional<Venda> venda = vendaService.buscarPorId(vendaId);
        if (venda.isEmpty()) {
            System.err.println("Venda não encontrada!");
            return;
        }
        
        System.out.println("Itens da venda:");
        for (ItemVenda item : venda.get().getItens()) {
            System.out.println("ID: " + item.getId() + " - " + item.getProduto().getNome() + 
                             " - Qtd: " + item.getQuantidade() + " - Valor: R$ " + item.getValorVenda());
        }
        
        System.out.print("ID do item: ");
        Long itemId = Long.parseLong(System.console().readLine());
        
        System.out.print("Nova quantidade: ");
        Integer novaQuantidade = Integer.parseInt(System.console().readLine());
        
        try {
            vendaService.alterarQuantidadeItem(vendaId, itemId, novaQuantidade);
            System.out.println("Quantidade alterada com sucesso!");
            
            // Mostrar venda atualizada
            Optional<Venda> vendaAtualizada = vendaService.buscarPorId(vendaId);
            if (vendaAtualizada.isPresent()) {
                mostrarDetalhesVenda(vendaAtualizada.get());
            }
        } catch (Exception e) {
            System.err.println("Erro ao alterar quantidade: " + e.getMessage());
        }
    }
    
    public void finalizarPedido() {
        System.out.println("\n=== FINALIZAR PEDIDO ===");
        System.out.print("ID da venda: ");
        Long vendaId = Long.parseLong(System.console().readLine());
        
        try {
            vendaService.finalizarPedido(vendaId);
            System.out.println("Pedido finalizado com sucesso!");
            
            // Mostrar venda atualizada
            Optional<Venda> venda = vendaService.buscarPorId(vendaId);
            if (venda.isPresent()) {
                mostrarDetalhesVenda(venda.get());
            }
        } catch (Exception e) {
            System.err.println("Erro ao finalizar pedido: " + e.getMessage());
        }
    }
    
    public void realizarPagamento() {
        System.out.println("\n=== REALIZAR PAGAMENTO ===");
        System.out.print("ID da venda: ");
        Long vendaId = Long.parseLong(System.console().readLine());
        
        try {
            vendaService.realizarPagamento(vendaId);
            System.out.println("Pagamento realizado com sucesso!");
            
            // Mostrar venda atualizada
            Optional<Venda> venda = vendaService.buscarPorId(vendaId);
            if (venda.isPresent()) {
                mostrarDetalhesVenda(venda.get());
            }
        } catch (Exception e) {
            System.err.println("Erro ao realizar pagamento: " + e.getMessage());
        }
    }
    
    public void entregar() {
        System.out.println("\n=== ENTREGAR PEDIDO ===");
        System.out.print("ID da venda: ");
        Long vendaId = Long.parseLong(System.console().readLine());
        
        try {
            vendaService.entregar(vendaId);
            System.out.println("Pedido entregue com sucesso!");
            
            // Mostrar venda atualizada
            Optional<Venda> venda = vendaService.buscarPorId(vendaId);
            if (venda.isPresent()) {
                mostrarDetalhesVenda(venda.get());
            }
        } catch (Exception e) {
            System.err.println("Erro ao entregar pedido: " + e.getMessage());
        }
    }
    
    public void listarVendas() {
        System.out.println("\n=== LISTA DE VENDAS ===");
        List<Venda> vendas = vendaService.listarTodas();
        
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda cadastrada.");
            return;
        }
        
        for (Venda venda : vendas) {
            System.out.println(venda);
        }
    }
    
    public void buscarVendasPorStatus() {
        System.out.println("\n=== BUSCAR VENDAS POR STATUS ===");
        System.out.println("Status disponíveis:");
        for (StatusVenda status : StatusVenda.values()) {
            System.out.println("- " + status.name() + ": " + status.getDescricao());
        }
        
        System.out.print("Digite o nome do status: ");
        String statusNome = System.console().readLine().toUpperCase();
        
        try {
            StatusVenda status = StatusVenda.valueOf(statusNome);
            List<Venda> vendas = vendaService.buscarPorStatus(status);
            
            if (vendas.isEmpty()) {
                System.out.println("Nenhuma venda encontrada com status: " + status.getDescricao());
                return;
            }
            
            System.out.println("Vendas com status " + status.getDescricao() + ":");
            for (Venda venda : vendas) {
                System.out.println(venda);
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Status inválido: " + statusNome);
        }
    }
    
    public void verDetalhesVenda() {
        System.out.println("\n=== DETALHES DA VENDA ===");
        System.out.print("ID da venda: ");
        Long vendaId = Long.parseLong(System.console().readLine());
        
        Optional<Venda> venda = vendaService.buscarPorId(vendaId);
        if (venda.isPresent()) {
            mostrarDetalhesVenda(venda.get());
        } else {
            System.err.println("Venda não encontrada com ID: " + vendaId);
        }
    }
    
    private void mostrarDetalhesVenda(Venda venda) {
        System.out.println("\n--- DETALHES DA VENDA ---");
        System.out.println("ID: " + venda.getId());
        System.out.println("Cliente: " + venda.getCliente().getNome() + " (" + venda.getCliente().getDocumento() + ")");
        System.out.println("Data de criação: " + venda.getDataCriacao());
        System.out.println("Status: " + venda.getStatus().getDescricao());
        System.out.println("Valor total: R$ " + venda.getValorTotal());
        
        if (!venda.getItens().isEmpty()) {
            System.out.println("Itens:");
            for (ItemVenda item : venda.getItens()) {
                System.out.println("  - " + item.getProduto().getNome() + 
                                 " | Qtd: " + item.getQuantidade() + 
                                 " | Valor unit.: R$ " + item.getValorVenda() + 
                                 " | Total: R$ " + item.getValorTotal());
            }
        } else {
            System.out.println("Nenhum item adicionado.");
        }
        System.out.println("------------------------");
    }
}
