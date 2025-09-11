package com.adacommerce.controller;

import com.adacommerce.dto.*;
import com.adacommerce.model.StatusVenda;
import com.adacommerce.service.VendaServiceDTO;
import com.adacommerce.service.ClienteServiceDTO;
import com.adacommerce.service.ProdutoServiceDTO;
import com.adacommerce.util.InputUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class VendaControllerDTO {
    private final VendaServiceDTO vendaService;
    private final ClienteServiceDTO clienteService;
    private final ProdutoServiceDTO produtoService;
    
    public VendaControllerDTO(VendaServiceDTO vendaService, 
                             ClienteServiceDTO clienteService,
                             ProdutoServiceDTO produtoService) {
        this.vendaService = vendaService;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }
    
    public void criarVenda() {
        try {
            System.out.println("=== Criar Nova Venda ===");
            
            // Listar clientes disponíveis
            List<ClienteDTO> clientes = clienteService.listarTodos();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado. Cadastre um cliente primeiro.");
                return;
            }
            
            System.out.println("Clientes disponíveis:");
            clientes.forEach(c -> System.out.println("ID: " + c.getId() + " - " + c.getNome()));
            
            Long clienteId = InputUtil.lerLong("ID do cliente: ");
            
            VendaDTO vendaCriada = vendaService.criarVenda(clienteId);
            
            System.out.println("Venda criada com sucesso!");
            System.out.println(vendaCriada);
            
        } catch (Exception e) {
            System.err.println("Erro ao criar venda: " + e.getMessage());
        }
    }
    
    public void adicionarItem() {
        try {
            System.out.println("=== Adicionar Item à Venda ===");
            
            Long vendaId = InputUtil.lerLong("ID da venda: ");
            
            // Verificar se venda existe
            Optional<VendaDTO> venda = vendaService.buscarPorId(vendaId);
            if (venda.isEmpty()) {
                System.out.println("Venda não encontrada com ID: " + vendaId);
                return;
            }
            
            System.out.println("Venda atual: " + venda.get());
            
            // Listar produtos disponíveis
            List<ProdutoDTO> produtos = produtoService.listarTodos();
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado.");
                return;
            }
            
            System.out.println("\nProdutos disponíveis:");
            produtos.forEach(p -> System.out.println("ID: " + p.getId() + " - " + p.getNome() + " - R$ " + p.getValorProduto()));
            
            Long produtoId = InputUtil.lerLong("ID do produto: ");
            Integer quantidade = InputUtil.lerInt("Quantidade: ");
            BigDecimal valorVenda = InputUtil.lerBigDecimal("Valor de venda: ");
            
            ItemVendaRequestDTO itemRequest = new ItemVendaRequestDTO(produtoId, quantidade, valorVenda);
            VendaDTO vendaAtualizada = vendaService.adicionarItem(vendaId, itemRequest);
            
            System.out.println("Item adicionado com sucesso!");
            System.out.println("Venda atualizada: " + vendaAtualizada);
            
        } catch (Exception e) {
            System.err.println("Erro ao adicionar item: " + e.getMessage());
        }
    }
    
    public void removerItem() {
        try {
            System.out.println("=== Remover Item da Venda ===");
            
            Long vendaId = InputUtil.lerLong("ID da venda: ");
            
            // Verificar se venda existe e mostrar itens
            Optional<VendaDTO> venda = vendaService.buscarPorId(vendaId);
            if (venda.isEmpty()) {
                System.out.println("Venda não encontrada com ID: " + vendaId);
                return;
            }
            
            if (venda.get().getItens().isEmpty()) {
                System.out.println("A venda não possui itens.");
                return;
            }
            
            System.out.println("Itens da venda:");
            venda.get().getItens().forEach(item -> {
                System.out.println("ID: " + item.getId() + " - " + item.getProdutoNome() + 
                    " - Qtd: " + item.getQuantidade() + " - Total: R$ " + item.getValorTotal());
            });
            
            Long itemId = InputUtil.lerLong("ID do item a remover: ");
            
            VendaDTO vendaAtualizada = vendaService.removerItem(vendaId, itemId);
            
            System.out.println("Item removido com sucesso!");
            System.out.println("Venda atualizada: " + vendaAtualizada);
            
        } catch (Exception e) {
            System.err.println("Erro ao remover item: " + e.getMessage());
        }
    }
    
    public void alterarQuantidadeItem() {
        try {
            System.out.println("=== Alterar Quantidade do Item ===");
            
            Long vendaId = InputUtil.lerLong("ID da venda: ");
            
            // Verificar se venda existe e mostrar itens
            Optional<VendaDTO> venda = vendaService.buscarPorId(vendaId);
            if (venda.isEmpty()) {
                System.out.println("Venda não encontrada com ID: " + vendaId);
                return;
            }
            
            if (venda.get().getItens().isEmpty()) {
                System.out.println("A venda não possui itens.");
                return;
            }
            
            System.out.println("Itens da venda:");
            venda.get().getItens().forEach(item -> {
                System.out.println("ID: " + item.getId() + " - " + item.getProdutoNome() + 
                    " - Qtd: " + item.getQuantidade() + " - Total: R$ " + item.getValorTotal());
            });
            
            Long itemId = InputUtil.lerLong("ID do item: ");
            Integer novaQuantidade = InputUtil.lerInt("Nova quantidade: ");
            
            VendaDTO vendaAtualizada = vendaService.alterarQuantidadeItem(vendaId, itemId, novaQuantidade);
            
            System.out.println("Quantidade alterada com sucesso!");
            System.out.println("Venda atualizada: " + vendaAtualizada);
            
        } catch (Exception e) {
            System.err.println("Erro ao alterar quantidade: " + e.getMessage());
        }
    }
    
    public void finalizarPedido() {
        try {
            System.out.println("=== Finalizar Pedido ===");
            
            Long vendaId = InputUtil.lerLong("ID da venda: ");
            
            VendaDTO vendaFinalizada = vendaService.finalizarPedido(vendaId);
            
            System.out.println("Pedido finalizado com sucesso!");
            System.out.println("Status: " + vendaFinalizada.getStatus());
            System.out.println("E-mail de notificação enviado ao cliente.");
            
        } catch (Exception e) {
            System.err.println("Erro ao finalizar pedido: " + e.getMessage());
        }
    }
    
    public void realizarPagamento() {
        try {
            System.out.println("=== Realizar Pagamento ===");
            
            Long vendaId = InputUtil.lerLong("ID da venda: ");
            
            VendaDTO vendaPaga = vendaService.realizarPagamento(vendaId);
            
            System.out.println("Pagamento realizado com sucesso!");
            System.out.println("Status: " + vendaPaga.getStatus());
            System.out.println("E-mail de confirmação enviado ao cliente.");
            
        } catch (Exception e) {
            System.err.println("Erro ao realizar pagamento: " + e.getMessage());
        }
    }
    
    public void entregarPedido() {
        try {
            System.out.println("=== Entregar Pedido ===");
            
            Long vendaId = InputUtil.lerLong("ID da venda: ");
            
            VendaDTO vendaEntregue = vendaService.entregarPedido(vendaId);
            
            System.out.println("Pedido entregue com sucesso!");
            System.out.println("Status: " + vendaEntregue.getStatus());
            System.out.println("E-mail de confirmação de entrega enviado ao cliente.");
            
        } catch (Exception e) {
            System.err.println("Erro ao entregar pedido: " + e.getMessage());
        }
    }
    
    public void listarVendas() {
        try {
            System.out.println("=== Lista de Vendas ===");
            
            List<VendaDTO> vendas = vendaService.listarTodas();
            
            if (vendas.isEmpty()) {
                System.out.println("Nenhuma venda cadastrada.");
                return;
            }
            
            vendas.forEach(venda -> {
                System.out.println("ID: " + venda.getId());
                System.out.println("Cliente: " + venda.getClienteNome());
                System.out.println("Data: " + venda.getDataCriacao());
                System.out.println("Status: " + venda.getStatus());
                System.out.println("Valor Total: R$ " + venda.getValorTotal());
                System.out.println("---");
            });
            
        } catch (Exception e) {
            System.err.println("Erro ao listar vendas: " + e.getMessage());
        }
    }
    
    public void buscarVendaPorId() {
        try {
            System.out.println("=== Buscar Venda por ID ===");
            
            Long id = InputUtil.lerLong("ID da venda: ");
            Optional<VendaDTO> venda = vendaService.buscarPorId(id);
            
            if (venda.isPresent()) {
                VendaDTO v = venda.get();
                System.out.println("Venda encontrada:");
                System.out.println("ID: " + v.getId());
                System.out.println("Cliente: " + v.getClienteNome());
                System.out.println("Data: " + v.getDataCriacao());
                System.out.println("Status: " + v.getStatus());
                System.out.println("Valor Total: R$ " + v.getValorTotal());
                
                if (v.getItens() != null && !v.getItens().isEmpty()) {
                    System.out.println("Itens:");
                    v.getItens().forEach(item -> {
                        System.out.println("  - " + item.getProdutoNome() + 
                            " (Qtd: " + item.getQuantidade() + 
                            ", Valor: R$ " + item.getValorVenda() + 
                            ", Total: R$ " + item.getValorTotal() + ")");
                    });
                }
            } else {
                System.out.println("Venda não encontrada com ID: " + id);
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar venda: " + e.getMessage());
        }
    }
    
    public void buscarVendasPorStatus() {
        try {
            System.out.println("=== Buscar Vendas por Status ===");
            System.out.println("1. Aberto");
            System.out.println("2. Aguardando Pagamento");
            System.out.println("3. Pago");
            System.out.println("4. Finalizado");
            
            int opcao = InputUtil.lerInt("Escolha o status: ");
            
            StatusVenda status;
            switch (opcao) {
                case 1:
                    status = StatusVenda.ABERTO;
                    break;
                case 2:
                    status = StatusVenda.AGUARDANDO_PAGAMENTO;
                    break;
                case 3:
                    status = StatusVenda.PAGO;
                    break;
                case 4:
                    status = StatusVenda.FINALIZADO;
                    break;
                default:
                    System.out.println("Opção inválida!");
                    return;
            }
            
            List<VendaDTO> vendas = vendaService.buscarPorStatus(status);
            
            if (vendas.isEmpty()) {
                System.out.println("Nenhuma venda encontrada com status: " + status.getDescricao());
                return;
            }
            
            System.out.println("Vendas com status " + status.getDescricao() + ":");
            vendas.forEach(venda -> {
                System.out.println("ID: " + venda.getId());
                System.out.println("Cliente: " + venda.getClienteNome());
                System.out.println("Data: " + venda.getDataCriacao());
                System.out.println("Valor Total: R$ " + venda.getValorTotal());
                System.out.println("---");
            });
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar vendas: " + e.getMessage());
        }
    }
    
    public void menuVendas() {
        int opcao;
        do {
            System.out.println("\n=== MENU VENDAS ===");
            System.out.println("1. Criar Venda");
            System.out.println("2. Adicionar Item");
            System.out.println("3. Remover Item");
            System.out.println("4. Alterar Quantidade Item");
            System.out.println("5. Finalizar Pedido");
            System.out.println("6. Realizar Pagamento");
            System.out.println("7. Entregar Pedido");
            System.out.println("8. Listar Vendas");
            System.out.println("9. Buscar por ID");
            System.out.println("10. Buscar por Status");
            System.out.println("0. Voltar");
            
            opcao = InputUtil.lerInt("Escolha uma opção: ");
            
            switch (opcao) {
                case 1:
                    criarVenda();
                    break;
                case 2:
                    adicionarItem();
                    break;
                case 3:
                    removerItem();
                    break;
                case 4:
                    alterarQuantidadeItem();
                    break;
                case 5:
                    finalizarPedido();
                    break;
                case 6:
                    realizarPagamento();
                    break;
                case 7:
                    entregarPedido();
                    break;
                case 8:
                    listarVendas();
                    break;
                case 9:
                    buscarVendaPorId();
                    break;
                case 10:
                    buscarVendasPorStatus();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
