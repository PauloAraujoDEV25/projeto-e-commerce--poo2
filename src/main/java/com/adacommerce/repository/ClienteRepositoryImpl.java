package com.adacommerce.repository;

import com.adacommerce.model.Cliente;
import com.adacommerce.model.StatusRegistro;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class ClienteRepositoryImpl implements ClienteRepository {
    private final Map<Long, Cliente> clientes = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(idGenerator.getAndIncrement());
        }
        
        // Verificar se já existe cliente com o mesmo documento
        if (buscarPorDocumento(cliente.getDocumento()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente com o documento: " + cliente.getDocumento());
        }
        
        if (cliente.getStatus() == null) {
            cliente.setStatus(StatusRegistro.ATIVO);
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }
    
    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }
    
    @Override
    public Optional<Cliente> buscarPorDocumento(String documento) {
        return clientes.values().stream()
            .filter(cliente -> documento.equals(cliente.getDocumento()))
            .findFirst();
    }
    
    @Override
    public List<Cliente> listarTodos() {
        return clientes.values().stream()
            .filter(Cliente::isAtivo)
            .collect(Collectors.toList());
    }
    
    @Override
    public Cliente atualizar(Cliente cliente) {
        if (cliente.getId() == null || !clientes.containsKey(cliente.getId())) {
            throw new IllegalArgumentException("Cliente não encontrado para atualização");
        }
        
        // Verificar se documento não está sendo usado por outro cliente
        Optional<Cliente> clienteExistente = buscarPorDocumento(cliente.getDocumento());
        if (clienteExistente.isPresent() && !clienteExistente.get().getId().equals(cliente.getId())) {
            throw new IllegalArgumentException("Já existe outro cliente com o documento: " + cliente.getDocumento());
        }
        
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }
}
