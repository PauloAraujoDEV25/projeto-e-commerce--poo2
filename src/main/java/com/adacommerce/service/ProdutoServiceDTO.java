package com.adacommerce.service;

import com.adacommerce.dto.ProdutoDTO;
import com.adacommerce.dto.ProdutoRequestDTO;
import com.adacommerce.mapper.ProdutoMapper;
import com.adacommerce.model.Produto;
import com.adacommerce.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProdutoServiceDTO {
    private final ProdutoRepository produtoRepository;
    
    public ProdutoServiceDTO(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }
    
    public ProdutoDTO cadastrar(ProdutoRequestDTO produtoRequestDTO) {
        validarDadosObrigatorios(produtoRequestDTO);
        
        Produto produto = ProdutoMapper.toEntity(produtoRequestDTO);
        Produto produtoSalvo = produtoRepository.salvar(produto);
        
        return ProdutoMapper.toDTO(produtoSalvo);
    }
    
    public Optional<ProdutoDTO> buscarPorId(Long id) {
        return produtoRepository.buscarPorId(id)
            .map(ProdutoMapper::toDTO);
    }
    
    public List<ProdutoDTO> listarTodos() {
        return produtoRepository.listarTodos().stream()
            .map(ProdutoMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    public List<ProdutoDTO> buscarPorNome(String nome) {
        return produtoRepository.buscarPorNome(nome).stream()
            .map(ProdutoMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    public ProdutoDTO atualizar(Long id, ProdutoRequestDTO produtoRequestDTO) {
        validarDadosObrigatorios(produtoRequestDTO);
        
        Produto produto = produtoRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));
        
        ProdutoMapper.updateEntity(produto, produtoRequestDTO);
        Produto produtoAtualizado = produtoRepository.atualizar(produto);
        
        return ProdutoMapper.toDTO(produtoAtualizado);
    }
    
    private void validarDadosObrigatorios(ProdutoRequestDTO produtoRequestDTO) {
        if (produtoRequestDTO.getNome() == null || produtoRequestDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (produtoRequestDTO.getEtiqueta() == null || produtoRequestDTO.getEtiqueta().trim().isEmpty()) {
            throw new IllegalArgumentException("Etiqueta é obrigatória");
        }
        
        if (produtoRequestDTO.getValorProduto() == null || 
            produtoRequestDTO.getValorProduto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do produto deve ser maior que zero");
        }
    }
}
