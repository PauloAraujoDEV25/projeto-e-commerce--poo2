package com.adacommerce.service;

import com.adacommerce.model.Cliente;
import com.adacommerce.repository.ClienteRepository;
import com.adacommerce.util.DocumentoValidator;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private final ClienteRepository clienteRepository;
    
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    public Cliente cadastrar(String nome, String documento, String email) {
        validarDadosCliente(nome, documento, email);
        
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setDocumento(documento);
        cliente.setEmail(email);
        
        return clienteRepository.salvar(cliente);
    }
    
    public List<Cliente> listar() {
        return clienteRepository.listarTodos();
    }
    
    public Cliente atualizar(Long id, String nome, String documento, String email) {
        validarDadosCliente(nome, documento, email);
        
        Optional<Cliente> clienteExistente = clienteRepository.buscarPorId(id);
        if (clienteExistente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }
        
        Cliente cliente = clienteExistente.get();
        cliente.setNome(nome);
        cliente.setDocumento(documento);
        cliente.setEmail(email);
        
        return clienteRepository.atualizar(cliente);
    }
    
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id);
    }
    
    public Optional<Cliente> buscarPorDocumento(String documento) {
        return clienteRepository.buscarPorDocumento(documento);
    }
    
    private void validarDadosCliente(String nome, String documento, String email) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (documento == null || documento.trim().isEmpty()) {
            throw new IllegalArgumentException("Documento é obrigatório");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email deve ser válido");
        }
        // Validação de CPF básica (11 dígitos e DV). Caso queira suportar CNPJ, expandir futuramente.
        if (!DocumentoValidator.isCpfValido(documento)) {
            throw new IllegalArgumentException("CPF inválido");
        }
    }
}
