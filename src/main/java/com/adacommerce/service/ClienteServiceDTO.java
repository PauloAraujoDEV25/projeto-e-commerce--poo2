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
        String docNormalizado = normalizarDocumento(clienteRequestDTO.getDocumento());
        validarCPF(docNormalizado);
        
        Cliente cliente = ClienteMapper.toEntity(clienteRequestDTO);
        cliente.setDocumento(docNormalizado);
        Cliente clienteSalvo = clienteRepository.salvar(cliente);
        
        return ClienteMapper.toDTO(clienteSalvo);
    }
    
    public Optional<ClienteDTO> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id)
            .map(ClienteMapper::toDTO);
    }
    
    public Optional<ClienteDTO> buscarPorDocumento(String documento) {
        String docNormalizado = normalizarDocumento(documento);
        return clienteRepository.buscarPorDocumento(docNormalizado)
            .map(ClienteMapper::toDTO);
    }
    
    public List<ClienteDTO> listarTodos() {
        return clienteRepository.listarTodos().stream()
            .map(ClienteMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    public ClienteDTO atualizar(Long id, ClienteRequestDTO clienteRequestDTO) {
        validarDadosObrigatorios(clienteRequestDTO);
        String docNormalizado = normalizarDocumento(clienteRequestDTO.getDocumento());
        validarCPF(docNormalizado);
        
        Cliente cliente = clienteRepository.buscarPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));
        
        ClienteMapper.updateEntity(cliente, clienteRequestDTO);
        cliente.setDocumento(docNormalizado);
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

    private String normalizarDocumento(String documento) {
        return documento.replaceAll("[^0-9]", "");
    }

    private void validarCPF(String documentoNumerico) {
        if (documentoNumerico.length() != 11) {
            throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos. Digite novamente com 11 dígitos.");
        }
        if (documentoNumerico.chars().distinct().count() == 1) {
            throw new IllegalArgumentException("CPF inválido.");
        }
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
