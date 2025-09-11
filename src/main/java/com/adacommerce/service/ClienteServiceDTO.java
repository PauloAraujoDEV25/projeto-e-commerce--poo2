package com.adacommerce.service;

import com.adacommerce.dto.ClienteDTO;
import com.adacommerce.dto.ClienteRequestDTO;
import com.adacommerce.mapper.ClienteMapper;
import com.adacommerce.model.Cliente;
import com.adacommerce.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClienteServiceDTO {
    private final ClienteRepository clienteRepository;
    
    public ClienteServiceDTO(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    public ClienteDTO cadastrar(ClienteRequestDTO clienteRequestDTO) {
        validarDadosObrigatorios(clienteRequestDTO);
        
        Cliente cliente = ClienteMapper.toEntity(clienteRequestDTO);
        Cliente clienteSalvo = clienteRepository.salvar(cliente);
        
        return ClienteMapper.toDTO(clienteSalvo);
    }
    
    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id)
            .map(ClienteMapper::toDTO);
    }
    
    public Optional<ClienteDTO> buscarPorDocumento(String documento) {
        return clienteRepository.buscarPorDocumento(documento)
            .map(ClienteMapper::toDTO);
    }
    
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.listarTodos().stream()
            .map(ClienteMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    public ClienteDTO atualizar(Long id, ClienteRequestDTO clienteRequestDTO) {
        validarDadosObrigatorios(clienteRequestDTO);
        
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        
        ClienteMapper.updateEntity(cliente, clienteRequestDTO);
        Cliente clienteAtualizado = clienteRepository.atualizar(cliente);
        
        return ClienteMapper.toDTO(clienteAtualizado);
    }
    
    private void validarDadosObrigatorios(ClienteRequestDTO clienteRequestDTO) {
        if (clienteRequestDTO.getNome() == null || clienteRequestDTO.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        
        if (clienteRequestDTO.getDocumento() == null || clienteRequestDTO.getDocumento().trim().isEmpty()) {
            throw new IllegalArgumentException("Documento é obrigatório");
        }
        
        if (clienteRequestDTO.getEmail() == null || clienteRequestDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail é obrigatório");
        }
        
        if (!isValidEmail(clienteRequestDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail inválido");
        }
    }
    
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".");
    }
}
