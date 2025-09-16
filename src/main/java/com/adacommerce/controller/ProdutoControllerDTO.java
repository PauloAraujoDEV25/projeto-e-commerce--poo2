package com.adacommerce.controller;

import com.adacommerce.dto.ProdutoDTO;
import com.adacommerce.dto.ProdutoRequestDTO;
import com.adacommerce.service.ProdutoServiceDTO;
import com.adacommerce.util.InputUtil;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoControllerDTO {
    private final ProdutoServiceDTO produtoService;
    
    public ProdutoControllerDTO(ProdutoServiceDTO produtoService) {
        this.produtoService = produtoService;
    }
    
    public void cadastrarProduto() {
        try {
            System.out.println("=== Cadastrar Produto ===");
            
            String nome = InputUtil.lerString("Nome: ");
            String etiqueta = InputUtil.lerString("Etiqueta: ");
            BigDecimal valor = InputUtil.lerBigDecimal("Valor do produto: ");
            
            ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO(nome, etiqueta, valor);
            ProdutoDTO produtoSalvo = produtoService.cadastrar(produtoRequestDTO);
            
            System.out.println("Produto cadastrado com sucesso!");
            System.out.println(produtoSalvo);
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    public void listarProdutos() {
        try {
            System.out.println("=== Lista de Produtos ===");
            
            List<ProdutoDTO> produtos = produtoService.listarTodos();
            
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto cadastrado.");
                return;
            }
            
            produtos.forEach(produto -> {
                System.out.println("ID: " + produto.getId());
                System.out.println("Nome: " + produto.getNome());
                System.out.println("Etiqueta: " + produto.getEtiqueta());
                System.out.println("Valor: R$ " + produto.getValorProduto());
                System.out.println("---");
            });
            
        } catch (Exception e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
    }
    
    public void buscarProdutoPorId() {
        try {
            System.out.println("=== Buscar Produto por ID ===");
            
            Long id = InputUtil.lerLong("ID do produto: ");
            Optional<ProdutoDTO> produto = produtoService.buscarPorId(id);
            
            if (produto.isPresent()) {
                System.out.println("Produto encontrado:");
                System.out.println(produto.get());
            } else {
                System.out.println("Produto não encontrado com ID: " + id);
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }
    }
    
    public void buscarProdutoPorNome() {
        try {
            System.out.println("=== Buscar Produto por Nome ===");
            
            String nome = InputUtil.lerString("Nome (ou parte do nome): ");
            List<ProdutoDTO> produtos = produtoService.buscarPorNome(nome);
            
            if (produtos.isEmpty()) {
                System.out.println("Nenhum produto encontrado com o nome: " + nome);
                return;
            }
            
            System.out.println("Produtos encontrados:");
            produtos.forEach(produto -> {
                System.out.println("ID: " + produto.getId());
                System.out.println("Nome: " + produto.getNome());
                System.out.println("Etiqueta: " + produto.getEtiqueta());
                System.out.println("Valor: R$ " + produto.getValorProduto());
                System.out.println("---");
            });
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }
    }
    
    public void atualizarProduto() {
        try {
            System.out.println("=== Atualizar Produto ===");
            
            Long id = InputUtil.lerLong("ID do produto: ");
            
            // Verificar se produto existe
            Optional<ProdutoDTO> produtoExistente = produtoService.buscarPorId(id);
            if (produtoExistente.isEmpty()) {
                System.out.println("Produto não encontrado com ID: " + id);
                return;
            }
            
            System.out.println("Produto atual: " + produtoExistente.get());
            System.out.println("Digite os novos dados:");
            
            String nome = InputUtil.lerString("Nome: ");
            String etiqueta = InputUtil.lerString("Etiqueta: ");
            BigDecimal valor = InputUtil.lerBigDecimal("Valor do produto: ");
            
            ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO(nome, etiqueta, valor);
            ProdutoDTO produtoAtualizado = produtoService.atualizar(id, produtoRequestDTO);
            
            System.out.println("Produto atualizado com sucesso!");
            System.out.println(produtoAtualizado);
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }
    
    public void menuProdutos() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRODUTOS ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Buscar por Nome");
            System.out.println("5. Atualizar Produto");
            System.out.println("6. Inativar Produto");
            System.out.println("7. Ativar Produto");
            System.out.println("0. Voltar");
            
            opcao = InputUtil.lerInt("Escolha uma opção: ");
            
            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    listarProdutos();
                    break;
                case 3:
                    buscarProdutoPorId();
                    break;
                case 4:
                    buscarProdutoPorNome();
                    break;
                case 5:
                    atualizarProduto();
                    break;
                case 6:
                    inativarProduto();
                    break;
                case 7:
                    ativarProduto();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void inativarProduto() {
        try {
            System.out.println("=== Inativar Produto ===");
            Long id = InputUtil.lerLong("ID do produto: ");
            produtoService.inativar(id);
            System.out.println("Produto inativado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inativar produto: " + e.getMessage());
        }
    }

    private void ativarProduto() {
        try {
            System.out.println("=== Ativar Produto ===");
            Long id = InputUtil.lerLong("ID do produto: ");
            produtoService.ativar(id);
            System.out.println("Produto ativado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao ativar produto: " + e.getMessage());
        }
    }
}
