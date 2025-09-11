package com.adacommerce;

import com.adacommerce.controller.*;
import com.adacommerce.repository.*;
import com.adacommerce.service.*;
import com.adacommerce.util.InputUtil;
import com.adacommerce.dto.*;
import java.math.BigDecimal;

public class AdaCommerceAppDTO {
    private final ClienteControllerDTO clienteController;
    private final ProdutoControllerDTO produtoController;
    private final VendaControllerDTO vendaController;
    
    public AdaCommerceAppDTO() {
        // Inicializar repositórios
        ClienteRepository clienteRepository = new ClienteRepositoryImpl();
        ProdutoRepository produtoRepository = new ProdutoRepositoryImpl();
        VendaRepository vendaRepository = new VendaRepositoryImpl();
        
        // Inicializar serviços
        NotificacaoService notificacaoService = new EmailNotificacaoService();
        ClienteService clienteServiceOriginal = new ClienteService(clienteRepository);
        ProdutoService produtoServiceOriginal = new ProdutoService(produtoRepository);
        
        ClienteServiceDTO clienteService = new ClienteServiceDTO(clienteRepository);
        ProdutoServiceDTO produtoService = new ProdutoServiceDTO(produtoRepository);
        VendaServiceDTO vendaService = new VendaServiceDTO(vendaRepository, clienteServiceOriginal, produtoServiceOriginal, notificacaoService);
        
        // Inicializar controladores
        this.clienteController = new ClienteControllerDTO(clienteService);
        this.produtoController = new ProdutoControllerDTO(produtoService);
        this.vendaController = new VendaControllerDTO(vendaService, clienteService, produtoService);
        
        // Carregar dados de exemplo
        carregarDadosExemplo(clienteService, produtoService);
    }
    
    public void executar() {
        System.out.println("===============================================");
        System.out.println("  BEM-VINDO AO ADA COMMERCE E-COMMERCE v2.0");
        System.out.println("        (Versão com DTOs e Mappers)");
        System.out.println("===============================================");
        
        while (true) {
            mostrarMenuPrincipal();
            int opcao = InputUtil.lerInt("Escolha uma opção: ");
            
            switch (opcao) {
                case 1:
                    clienteController.menuClientes();
                    break;
                case 2:
                    produtoController.menuProdutos();
                    break;
                case 3:
                    vendaController.menuVendas();
                    break;
                case 0:
                    System.out.println("Obrigado por usar o Ada Commerce! Até logo!");
                    return;
                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    private void mostrarMenuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Gerenciar Clientes");
        System.out.println("2. Gerenciar Produtos");
        System.out.println("3. Gerenciar Vendas");
        System.out.println("0. Sair");
    }
    
    private void carregarDadosExemplo(ClienteServiceDTO clienteService, ProdutoServiceDTO produtoService) {
        try {
            System.out.println("Carregando dados de exemplo...");
            
            // Cadastrar clientes de exemplo
            clienteService.cadastrar(new ClienteRequestDTO("João Silva", "12345678901", "joao.silva@email.com"));
            clienteService.cadastrar(new ClienteRequestDTO("Maria Santos", "98765432100", "maria.santos@email.com"));
            clienteService.cadastrar(new ClienteRequestDTO("Pedro Costa", "45678912300", "pedro.costa@email.com"));
            
            // Cadastrar produtos de exemplo
            produtoService.cadastrar(new ProdutoRequestDTO("Notebook Dell", "NOTEBOOK-DELL-001", BigDecimal.valueOf(2500.00)));
            produtoService.cadastrar(new ProdutoRequestDTO("Mouse Gamer", "MOUSE-GAMER-001", BigDecimal.valueOf(150.00)));
            produtoService.cadastrar(new ProdutoRequestDTO("Teclado Mecânico", "TECLADO-MEC-001", BigDecimal.valueOf(300.00)));
            produtoService.cadastrar(new ProdutoRequestDTO("Monitor 24\"", "MONITOR-24-001", BigDecimal.valueOf(800.00)));
            produtoService.cadastrar(new ProdutoRequestDTO("Webcam HD", "WEBCAM-HD-001", BigDecimal.valueOf(200.00)));
            
            System.out.println("Dados de exemplo carregados com sucesso!");
            System.out.println("3 clientes e 5 produtos foram adicionados ao sistema.");
            
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados de exemplo: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        AdaCommerceAppDTO app = new AdaCommerceAppDTO();
        app.executar();
    }
}
