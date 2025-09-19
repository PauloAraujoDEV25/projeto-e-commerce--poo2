package com.adacommerce.controller;

import com.adacommerce.model.Produto;
import com.adacommerce.service.ProdutoService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoController {
    private final ProdutoService produtoService;
    
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }
    
    public ProdutoService getProdutoService() {
        return produtoService;
    }
    
    public void cadastrarProduto() {
        System.out.println("\n=== CADASTRAR PRODUTO ===");
        System.out.print("Nome: ");
        String nome = System.console().readLine();
        
        System.out.print("Etiqueta: ");
        String etiqueta = System.console().readLine();
        
        System.out.print("Valor do produto: ");
        BigDecimal valorProduto = new BigDecimal(System.console().readLine());
        
        try {
            Produto produto = produtoService.cadastrar(nome, etiqueta, valorProduto);
            System.out.println("Produto cadastrado com sucesso!");
            System.out.println(produto);
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }
    
    public void listarProdutos() {
        System.out.println("\n=== LISTA DE PRODUTOS ===");
        List<Produto> produtos = produtoService.listar();
        
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }
    
    public void atualizarProduto() {
        System.out.println("\n=== ATUALIZAR PRODUTO ===");
        System.out.print("ID do produto: ");
        Long id = Long.parseLong(System.console().readLine());
        
        Optional<Produto> produtoExistente = produtoService.buscarPorId(id);
        if (produtoExistente.isEmpty()) {
            System.err.println("Produto não encontrado com ID: " + id);
            return;
        }
        
        System.out.println("Produto atual: " + produtoExistente.get());
        
        System.out.print("Novo nome: ");
        String nome = System.console().readLine();
        
        System.out.print("Nova etiqueta: ");
        String etiqueta = System.console().readLine();
        
        System.out.print("Novo valor: ");
        BigDecimal valorProduto = new BigDecimal(System.console().readLine());
        
        try {
            Produto produto = produtoService.atualizar(id, nome, etiqueta, valorProduto);
            System.out.println("Produto atualizado com sucesso!");
            System.out.println(produto);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
        }
    }
    
    public void buscarProdutoPorNome() {
        System.out.println("\n=== BUSCAR PRODUTO POR NOME ===");
        System.out.print("Nome (ou parte do nome): ");
        String nome = System.console().readLine();
        
        List<Produto> produtos = produtoService.buscarPorNome(nome);
        if (!produtos.isEmpty()) {
            System.out.println("Produtos encontrados:");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        } else {
            System.out.println("Nenhum produto encontrado com nome: " + nome);
        }
    }

    public void inativarProduto() {
        System.out.println("\n=== INATIVAR PRODUTO ===");
        System.out.print("ID do produto: ");
        Long id = Long.parseLong(System.console().readLine());
        try {
            produtoService.inativar(id);
            System.out.println("Produto inativado (se existia e estava ativo).");
        } catch (Exception e) {
            System.err.println("Erro ao inativar: " + e.getMessage());
        }
    }

    public void reativarProduto() {
        System.out.println("\n=== REATIVAR PRODUTO ===");
        System.out.print("ID do produto: ");
        Long id = Long.parseLong(System.console().readLine());
        try {
            produtoService.reativar(id);
            System.out.println("Produto reativado (se existia e estava inativo).");
        } catch (Exception e) {
            System.err.println("Erro ao reativar: " + e.getMessage());
        }
    }

    public void listarTodosProdutosIncluindoInativos() {
        System.out.println("\n=== LISTA DE TODOS OS PRODUTOS (ATIVOS E INATIVOS) ===");
        List<Produto> produtos = produtoService.listarTodosIncluindoInativos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }
        for (Produto produto : produtos) {
            System.out.println(produto + " | status=" + (produto.isAtivo()?"ATIVO":"INATIVO"));
        }
    }
}
