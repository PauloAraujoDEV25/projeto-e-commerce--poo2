package com.adacommerce.mapper;

import com.adacommerce.dto.ClienteDTO;
import com.adacommerce.dto.ClienteRequestDTO;
import com.adacommerce.model.Cliente;

public class ClienteMapper {
    
    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        
        return new ClienteDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getDocumento(),
            cliente.getEmail(),
            cliente.getStatus() != null ? cliente.getStatus().name() : null
        );
    }
    
    public static Cliente toEntity(ClienteRequestDTO clienteRequestDTO) {
        if (clienteRequestDTO == null) {
            return null;
        }
        
        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setDocumento(clienteRequestDTO.getDocumento());
        cliente.setEmail(clienteRequestDTO.getEmail());
        
        return cliente;
    }
    
    public static Cliente toEntity(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        
        return new Cliente(
            clienteDTO.getId(),
            clienteDTO.getNome(),
            clienteDTO.getDocumento(),
            clienteDTO.getEmail()
        );
    }
    
    public static void updateEntity(Cliente cliente, ClienteRequestDTO clienteRequestDTO) {
        if (cliente == null || clienteRequestDTO == null) {
            return;
        }
        
        cliente.setNome(clienteRequestDTO.getNome());
        cliente.setDocumento(clienteRequestDTO.getDocumento());
        cliente.setEmail(clienteRequestDTO.getEmail());
    }
}
