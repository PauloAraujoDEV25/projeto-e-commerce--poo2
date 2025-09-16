package com.adacommerce.service;

import com.adacommerce.model.Cliente;
import com.adacommerce.repository.ClienteRepository;
import java.util.List;
import java.util.Optional;

public class ClienteService {
    private final ClienteRepository clienteRepository;
    
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    
    public Cliente cadastrar(String nome, String documento, String email) {
        validarDadosCliente(nome, documento, email);
        String docNormalizado = normalizarDocumento(documento);
        validarCPF(docNormalizado);
        
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setDocumento(docNormalizado);
        cliente.setEmail(email);
        
        return clienteRepository.salvar(cliente);
    }
    
    public List<Cliente> listar() {
        return clienteRepository.listarTodos();
    }
    
    public Cliente atualizar(Long id, String nome, String documento, String email) {
        validarDadosCliente(nome, documento, email);
        String docNormalizado = normalizarDocumento(documento);
        validarCPF(docNormalizado);
        
        Optional<Cliente> clienteExistente = clienteRepository.buscarPorId(id);
        if (clienteExistente.isEmpty()) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }
        
        Cliente cliente = clienteExistente.get();
        cliente.setNome(nome);
        cliente.setDocumento(docNormalizado);
        cliente.setEmail(email);
        
        return clienteRepository.atualizar(cliente);
    }
    
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id);
    }
    
    public Optional<Cliente> buscarPorDocumento(String documento) {
        String docNormalizado = normalizarDocumento(documento);
        return clienteRepository.buscarPorDocumento(docNormalizado);
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
    }

    private String normalizarDocumento(String documento) {
        return documento.replaceAll("[^0-9]", "");
    }

    private void validarCPF(String documentoNumerico) {
        if (documentoNumerico.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos. Digite novamente com 11 dígitos.");
        }
        // rejeita sequências com todos dígitos iguais
        if (documentoNumerico.chars().distinct().count() == 1) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        // valida dígitos verificadores
        int dv1 = calcularDV(documentoNumerico.substring(0, 9), 10);
        int dv2 = calcularDV(documentoNumerico.substring(0, 9) + dv1, 11);
        if (dv1 != Character.getNumericValue(documentoNumerico.charAt(9)) ||
            dv2 != Character.getNumericValue(documentoNumerico.charAt(10))) {
            throw new IllegalArgumentException("CPF inválido.");
        }
    }

    private int calcularDV(String base, int pesoInicial) {
        int soma = 0;
        int peso = pesoInicial;
        for (int i = 0; i < base.length(); i++) {
            soma += Character.getNumericValue(base.charAt(i)) * peso--;
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }

    public void inativar(Long id) {
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        cliente.inativar();
        clienteRepository.atualizar(cliente);
    }

    public void ativar(Long id) {
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        cliente.ativar();
        clienteRepository.atualizar(cliente);
    }
}
