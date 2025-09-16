package com.adacommerce.service;

import com.adacommerce.model.Produto;
import com.adacommerce.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoService {
    private final ProdutoRepository produtoRepository;
    
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    
    public Produto cadastrar(String nome, String etiqueta, BigDecimal valorProduto) {
        validarDadosProduto(nome, etiqueta, valorProduto);
        
        Produto produto = new Produto();
        produto.setNome(nome);
        produto.setEtiqueta(etiqueta);
        produto.setValorProduto(valorProduto);
        
        return produtoRepository.salvar(produto);
    }
    
    public List<Produto> listar() {
        return produtoRepository.listarTodos();
    }
    
    public Produto atualizar(Long id, String nome, String etiqueta, BigDecimal valorProduto) {
        validarDadosProduto(nome, etiqueta, valorProduto);
        
        Optional<Produto> produtoExistente = produtoRepository.buscarPorId(id);
        if (produtoExistente.isEmpty()) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }
        
        Produto produto = produtoExistente.get();
        produto.setNome(nome);
        produto.setEtiqueta(etiqueta);
        produto.setValorProduto(valorProduto);
        
        return produtoRepository.atualizar(produto);
    }
    
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.buscarPorId(id);
    }
    
    public List<Produto> buscarPorNome(String nome) {
        return produtoRepository.buscarPorNome(nome);
    }
    
    private void validarDadosProduto(String nome, String etiqueta, BigDecimal valorProduto) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (etiqueta == null || etiqueta.trim().isEmpty()) {
            throw new IllegalArgumentException("Etiqueta do produto é obrigatória");
        }
        if (valorProduto == null || valorProduto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do produto deve ser maior que zero");
        }
    }

    public void inativar(Long id) {
        Produto produto = produtoRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
        produto.inativar();
        produtoRepository.atualizar(produto);
    }

    public void ativar(Long id) {
        Produto produto = produtoRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
        produto.ativar();
        produtoRepository.atualizar(produto);
    }
}
