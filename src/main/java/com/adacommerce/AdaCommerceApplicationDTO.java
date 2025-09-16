package com.adacommerce;

import com.adacommerce.controller.*;
import com.adacommerce.repository.*;
import com.adacommerce.service.*;
import com.adacommerce.util.InputUtil;

public class AdaCommerceApplicationDTO {
    private static ClienteControllerDTO clienteController;
    private static ProdutoControllerDTO produtoController;
    private static VendaControllerDTO vendaController;
    
    public static void main(String[] args) {
        System.out.println("=== Ada Commerce ===");
        System.out.println("Sistema de vendas online");
        
        inicializarSistema();
        menuPrincipal();
    }
    
    private static void inicializarSistema() {
        // Inicializar repositórios
        ClienteRepository clienteRepository = new ClienteRepositoryImpl();
        ProdutoRepository produtoRepository = new ProdutoRepositoryImpl();
        VendaRepository vendaRepository = new VendaRepositoryImpl();
        
        // Inicializar serviços
        NotificacaoService notificacaoService = new EmailNotificacaoService();
        
        // Serviços originais (necessários para o VendaServiceDTO)
        ClienteService clienteService = new ClienteService(clienteRepository);
        ProdutoService produtoService = new ProdutoService(produtoRepository);
        
        // Serviços DTO
        ClienteServiceDTO clienteServiceDTO = new ClienteServiceDTO(clienteRepository);
        ProdutoServiceDTO produtoServiceDTO = new ProdutoServiceDTO(produtoRepository);
        VendaServiceDTO vendaServiceDTO = new VendaServiceDTO(vendaRepository, clienteService, produtoService, notificacaoService);
        
        // Inicializar controladores
        clienteController = new ClienteControllerDTO(clienteServiceDTO);
        produtoController = new ProdutoControllerDTO(produtoServiceDTO);
        vendaController = new VendaControllerDTO(vendaServiceDTO, clienteServiceDTO, produtoServiceDTO);
    }
    
    private static void menuPrincipal() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRINCIPAL - Ada Commerce DTO ===");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Vendas");
            System.out.println("0. Sair");
            
            opcao = InputUtil.lerInt("Escolha uma opção: ");
            
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
                    System.out.println("Encerrando o sistema...");
                    System.out.println("Obrigado por usar o Ada Commerce!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        } while (opcao != 0);
    }
}
