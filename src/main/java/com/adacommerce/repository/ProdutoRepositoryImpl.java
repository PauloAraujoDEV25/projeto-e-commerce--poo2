package com.adacommerce.repository;

import com.adacommerce.model.Produto;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    private final Map<Long, Produto> produtos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(idGenerator.getAndIncrement());
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }
    
    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(produtos.get(id));
    }
    
    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }
    
    @Override
    public Produto atualizar(Produto produto) {
        if (produto.getId() == null || !produtos.containsKey(produto.getId())) {
            throw new IllegalArgumentException("Produto não encontrado para atualização");
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }
    
    @Override
    public List<Produto> buscarPorNome(String nome) {
        return produtos.values().stream()
            .filter(produto -> produto.getNome().toLowerCase().contains(nome.toLowerCase()))
            .collect(Collectors.toList());
    }
}
