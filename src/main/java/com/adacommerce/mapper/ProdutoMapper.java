package com.adacommerce.mapper;

import com.adacommerce.dto.ProdutoDTO;
import com.adacommerce.dto.ProdutoRequestDTO;
import com.adacommerce.model.Produto;

public class ProdutoMapper {
    
    public static ProdutoDTO toDTO(Produto produto) {
        if (produto == null) {
            return null;
        }
        
        return new ProdutoDTO(
            produto.getId(),
            produto.getNome(),
            produto.getEtiqueta(),
            produto.getValorProduto(),
            produto.getStatus() != null ? produto.getStatus().name() : null
        );
    }
    
    public static Produto toEntity(ProdutoRequestDTO produtoRequestDTO) {
        if (produtoRequestDTO == null) {
            return null;
        }
        
        Produto produto = new Produto();
        produto.setNome(produtoRequestDTO.getNome());
        produto.setEtiqueta(produtoRequestDTO.getEtiqueta());
        produto.setValorProduto(produtoRequestDTO.getValorProduto());
        
        return produto;
    }
    
    public static Produto toEntity(ProdutoDTO produtoDTO) {
        if (produtoDTO == null) {
            return null;
        }
        
        return new Produto(
            produtoDTO.getId(),
            produtoDTO.getNome(),
            produtoDTO.getEtiqueta(),
            produtoDTO.getValorProduto()
        );
    }
    
    public static void updateEntity(Produto produto, ProdutoRequestDTO produtoRequestDTO) {
        if (produto == null || produtoRequestDTO == null) {
            return;
        }
        
        produto.setNome(produtoRequestDTO.getNome());
        produto.setEtiqueta(produtoRequestDTO.getEtiqueta());
        produto.setValorProduto(produtoRequestDTO.getValorProduto());
    }
}
