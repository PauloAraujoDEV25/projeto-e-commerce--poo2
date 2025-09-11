package com.adacommerce.repository;

import com.adacommerce.model.Produto;
import java.util.List;
import java.util.Optional;

public interface ProdutoRepository {
    Produto salvar(Produto produto);
    Optional<Produto> buscarPorId(Long id);
    List<Produto> listarTodos();
    Produto atualizar(Produto produto);
    List<Produto> buscarPorNome(String nome);
}
