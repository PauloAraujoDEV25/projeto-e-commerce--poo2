package com.adacommerce;

import com.adacommerce.controller.*;
import com.adacommerce.repository.*;
import com.adacommerce.service.*;
import com.adacommerce.util.InputUtil;

public class AdaCommerceApplication {
    private final ClienteController clienteController;
    private final ProdutoController produtoController;
    private final VendaController vendaController;
    
    public AdaCommerceApplication() {
        // Inicializar repositórios
        ClienteRepository clienteRepository = new ClienteRepositoryImpl();
        ProdutoRepository produtoRepository = new ProdutoRepositoryImpl();
        VendaRepository vendaRepository = new VendaRepositoryImpl();
        
        // Inicializar serviços
        NotificacaoService notificacaoService = new EmailNotificacaoService();
        ClienteService clienteService = new ClienteService(clienteRepository);
        ProdutoService produtoService = new ProdutoService(produtoRepository);
        VendaService vendaService = new VendaService(vendaRepository, clienteService, produtoService, notificacaoService);
        
        // Inicializar controladores
        this.clienteController = new ClienteController(clienteService);
        this.produtoController = new ProdutoController(produtoService);
        this.vendaController = new VendaController(vendaService);
        
        // Carregar dados de exemplo
        carregarDadosExemplo(clienteService, produtoService);
    }
    
    public void executar() {
        System.out.println("===========================================");
        System.out.println("    BEM-VINDO AO ADA COMMERCE E-COMMERCE");
        System.out.println("===========================================");
        
        while (true) {
            mostrarMenuPrincipal();
            int opcao = InputUtil.lerInt("Escolha uma opção: ");
            
            switch (opcao) {
                case 1:
                    menuClientes();
                    break;
                case 2:
                    menuProdutos();
                    break;
                case 3:
                    menuVendas();
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
    
    private void menuClientes() {
        while (true) {
            System.out.println("\n=== MENU CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Atualizar Cliente");
            System.out.println("4. Buscar Cliente por Documento");
            System.out.println("5. Inativar Cliente");
            System.out.println("6. Ativar Cliente");
            System.out.println("0. Voltar ao Menu Principal");
            
            int opcao = InputUtil.lerInt("Escolha uma opção: ");
            
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    clienteController.listarClientes();
                    break;
                case 3:
                    atualizarCliente();
                    break;
                case 4:
                    buscarClientePorDocumento();
                    break;
                case 5:
                    inativarCliente();
                    break;
                case 6:
                    ativarCliente();
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    private void menuProdutos() {
        while (true) {
            System.out.println("\n=== MENU PRODUTOS ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Atualizar Produto");
            System.out.println("4. Buscar Produto por Nome");
            System.out.println("5. Inativar Produto");
            System.out.println("6. Ativar Produto");
            System.out.println("0. Voltar ao Menu Principal");
            
            int opcao = InputUtil.lerInt("Escolha uma opção: ");
            
            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    produtoController.listarProdutos();
                    break;
                case 3:
                    atualizarProduto();
                    break;
                case 4:
                    buscarProdutoPorNome();
                    break;
                case 5:
                    inativarProduto();
                    break;
                case 6:
                    ativarProduto();
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    private void menuVendas() {
        while (true) {
            System.out.println("\n=== MENU VENDAS ===");
            System.out.println("1. Criar Nova Venda");
            System.out.println("2. Adicionar Item à Venda");
            System.out.println("3. Remover Item da Venda");
            System.out.println("4. Alterar Quantidade do Item");
            System.out.println("5. Finalizar Pedido");
            System.out.println("6. Realizar Pagamento");
            System.out.println("7. Entregar Pedido");
            System.out.println("8. Listar Todas as Vendas");
            System.out.println("9. Buscar Vendas por Status");
            System.out.println("10. Ver Detalhes da Venda");
            System.out.println("0. Voltar ao Menu Principal");
            
            int opcao = InputUtil.lerInt("Escolha uma opção: ");
            
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
                    vendaController.listarVendas();
                    break;
                case 9:
                    vendaController.buscarVendasPorStatus();
                    break;
                case 10:
                    vendaController.verDetalhesVenda();
                    break;
                case 0:
                    return;
                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }
    
    // Métodos auxiliares para entrada de dados
    private void cadastrarCliente() {
        System.out.println("\n=== CADASTRAR CLIENTE ===");
        String nome = InputUtil.lerString("Nome: ");
    String documento = InputUtil.lerString("Documento (CPF - 11 dígitos, somente números ou com máscara): ");
        String email = InputUtil.lerString("Email: ");
        
        try {
            clienteController.getClienteService().cadastrar(nome, documento, email);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }
    
    private void atualizarCliente() {
        System.out.println("\n=== ATUALIZAR CLIENTE ===");
        long id = InputUtil.lerLong("ID do cliente: ");
        String nome = InputUtil.lerString("Novo nome: ");
    String documento = InputUtil.lerString("Novo documento (CPF - 11 dígitos): ");
        String email = InputUtil.lerString("Novo email: ");
        
        try {
            clienteController.getClienteService().atualizar(id, nome, documento, email);
            System.out.println("Cliente atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }
    
    private void buscarClientePorDocumento() {
        System.out.println("\n=== BUSCAR CLIENTE POR DOCUMENTO ===");
    String documento = InputUtil.lerString("Documento (CPF - 11 dígitos): ");
        clienteController.getClienteService().buscarPorDocumento(documento)
            .ifPresentOrElse(
                cliente -> System.out.println("Cliente encontrado: " + cliente),
                () -> System.out.println("Cliente não encontrado.")
            );
    }

    private void inativarCliente() {
        System.out.println("\n=== INATIVAR CLIENTE ===");
        long id = InputUtil.lerLong("ID do cliente: ");
        try {
            clienteController.getClienteService().inativar(id);
            System.out.println("Cliente inativado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inativar cliente: " + e.getMessage());
        }
    }

    private void ativarCliente() {
        System.out.println("\n=== ATIVAR CLIENTE ===");
        long id = InputUtil.lerLong("ID do cliente: ");
        try {
            clienteController.getClienteService().ativar(id);
            System.out.println("Cliente ativado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao ativar cliente: " + e.getMessage());
        }
    }
    
    private void cadastrarProduto() {
        System.out.println("\n=== CADASTRAR PRODUTO ===");
        String nome = InputUtil.lerString("Nome: ");
        String etiqueta = InputUtil.lerString("Etiqueta: ");
        double valor = InputUtil.lerDouble("Valor do produto: ");
        
        try {
            produtoController.getProdutoService().cadastrar(nome, etiqueta, java.math.BigDecimal.valueOf(valor));
            System.out.println("Produto cadastrado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    private void atualizarProduto() {
        System.out.println("\n=== ATUALIZAR PRODUTO ===");
        long id = InputUtil.lerLong("ID do produto: ");
        String nome = InputUtil.lerString("Novo nome: ");
        String etiqueta = InputUtil.lerString("Nova etiqueta: ");
        double valor = InputUtil.lerDouble("Novo valor: ");
        
        try {
            produtoController.getProdutoService().atualizar(id, nome, etiqueta, java.math.BigDecimal.valueOf(valor));
            System.out.println("Produto atualizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }
    
    private void buscarProdutoPorNome() {
        System.out.println("\n=== BUSCAR PRODUTO POR NOME ===");
        String nome = InputUtil.lerString("Nome (ou parte do nome): ");
        produtoController.getProdutoService().buscarPorNome(nome).forEach(System.out::println);
    }

    private void inativarProduto() {
        System.out.println("\n=== INATIVAR PRODUTO ===");
        long id = InputUtil.lerLong("ID do produto: ");
        try {
            produtoController.getProdutoService().inativar(id);
            System.out.println("Produto inativado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inativar produto: " + e.getMessage());
        }
    }

    private void ativarProduto() {
        System.out.println("\n=== ATIVAR PRODUTO ===");
        long id = InputUtil.lerLong("ID do produto: ");
        try {
            produtoController.getProdutoService().ativar(id);
            System.out.println("Produto ativado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao ativar produto: " + e.getMessage());
        }
    }
    
    private void criarVenda() {
        System.out.println("\n=== CRIAR NOVA VENDA ===");
        long clienteId = InputUtil.lerLong("ID do cliente: ");
        
        try {
            vendaController.getVendaService().criarVenda(clienteId);
            System.out.println("Venda criada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao criar venda: " + e.getMessage());
        }
    }
    
    private void adicionarItem() {
        System.out.println("\n=== ADICIONAR ITEM À VENDA ===");
        long vendaId = InputUtil.lerLong("ID da venda: ");
        long produtoId = InputUtil.lerLong("ID do produto: ");
        int quantidade = InputUtil.lerInt("Quantidade: ");
        double valorVenda = InputUtil.lerDouble("Valor de venda: ");
        
        try {
            vendaController.getVendaService().adicionarItem(vendaId, produtoId, quantidade, java.math.BigDecimal.valueOf(valorVenda));
            System.out.println("Item adicionado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao adicionar item: " + e.getMessage());
        }
    }
    
    private void removerItem() {
        System.out.println("\n=== REMOVER ITEM DA VENDA ===");
        long vendaId = InputUtil.lerLong("ID da venda: ");
        long itemId = InputUtil.lerLong("ID do item: ");
        
        try {
            vendaController.getVendaService().removerItem(vendaId, itemId);
            System.out.println("Item removido com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao remover item: " + e.getMessage());
        }
    }
    
    private void alterarQuantidadeItem() {
        System.out.println("\n=== ALTERAR QUANTIDADE DO ITEM ===");
        long vendaId = InputUtil.lerLong("ID da venda: ");
        long itemId = InputUtil.lerLong("ID do item: ");
        int novaQuantidade = InputUtil.lerInt("Nova quantidade: ");
        
        try {
            vendaController.getVendaService().alterarQuantidadeItem(vendaId, itemId, novaQuantidade);
            System.out.println("Quantidade alterada com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao alterar quantidade: " + e.getMessage());
        }
    }
    
    private void finalizarPedido() {
        System.out.println("\n=== FINALIZAR PEDIDO ===");
        long vendaId = InputUtil.lerLong("ID da venda: ");
        
        try {
            vendaController.getVendaService().finalizarPedido(vendaId);
            System.out.println("Pedido finalizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao finalizar pedido: " + e.getMessage());
        }
    }
    
    private void realizarPagamento() {
        System.out.println("\n=== REALIZAR PAGAMENTO ===");
        long vendaId = InputUtil.lerLong("ID da venda: ");
        
        try {
            vendaController.getVendaService().realizarPagamento(vendaId);
            System.out.println("Pagamento realizado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao realizar pagamento: " + e.getMessage());
        }
    }
    
    private void entregarPedido() {
        System.out.println("\n=== ENTREGAR PEDIDO ===");
        long vendaId = InputUtil.lerLong("ID da venda: ");
        
        try {
            vendaController.getVendaService().entregar(vendaId);
            System.out.println("Pedido entregue com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao entregar pedido: " + e.getMessage());
        }
    }
    
    private void carregarDadosExemplo(ClienteService clienteService, ProdutoService produtoService) {
        try {
            // Cadastrar clientes de exemplo
            clienteService.cadastrar("João Silva", "12345678901", "joao.silva@email.com");
            clienteService.cadastrar("Maria Santos", "98765432100", "maria.santos@email.com");
            clienteService.cadastrar("Pedro Costa", "45678912300", "pedro.costa@email.com");
            
            // Cadastrar produtos de exemplo
            produtoService.cadastrar("Notebook Dell", "NOTEBOOK-DELL-001", java.math.BigDecimal.valueOf(2500.00));
            produtoService.cadastrar("Mouse Gamer", "MOUSE-GAMER-001", java.math.BigDecimal.valueOf(150.00));
            produtoService.cadastrar("Teclado Mecânico", "TECLADO-MEC-001", java.math.BigDecimal.valueOf(300.00));
            produtoService.cadastrar("Monitor 24\"", "MONITOR-24-001", java.math.BigDecimal.valueOf(800.00));
            produtoService.cadastrar("Webcam HD", "WEBCAM-HD-001", java.math.BigDecimal.valueOf(200.00));
            
            System.out.println("Dados de exemplo carregados com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados de exemplo: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        AdaCommerceApplication app = new AdaCommerceApplication();
        app.executar();
    }
}
