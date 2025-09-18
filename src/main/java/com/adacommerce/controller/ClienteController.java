package com.adacommerce.controller;

import com.adacommerce.model.Cliente;
import com.adacommerce.service.ClienteService;
import java.util.List;
import java.util.Optional;

public class ClienteController {
    private final ClienteService clienteService;
    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }
    
    public ClienteService getClienteService() {
        return clienteService;
    }
    
    public void cadastrarCliente() {
        System.out.println("\n=== CADASTRAR CLIENTE ===");
        System.out.print("Nome: ");
        String nome = System.console().readLine();
        
        System.out.print("Documento: ");
        String documento = System.console().readLine();
        
        System.out.print("Email: ");
        String email = System.console().readLine();
        
        try {
            Cliente cliente = clienteService.cadastrar(nome, documento, email);
            System.out.println("Cliente cadastrado com sucesso!");
            System.out.println(cliente);
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }
    
    public void listarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        List<Cliente> clientes = clienteService.listar();
        
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        
        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }
    
    public void atualizarCliente() {
        System.out.println("\n=== ATUALIZAR CLIENTE ===");
        System.out.print("ID do cliente: ");
        Long id = Long.parseLong(System.console().readLine());
        
        Optional<Cliente> clienteExistente = clienteService.buscarPorId(id);
        if (clienteExistente.isEmpty()) {
            System.err.println("Cliente não encontrado com ID: " + id);
            return;
        }
        
        System.out.println("Cliente atual: " + clienteExistente.get());
        
        System.out.print("Novo nome: ");
        String nome = System.console().readLine();
        
        System.out.print("Novo documento: ");
        String documento = System.console().readLine();
        
        System.out.print("Novo email: ");
        String email = System.console().readLine();
        
        try {
            Cliente cliente = clienteService.atualizar(id, nome, documento, email);
            System.out.println("Cliente atualizado com sucesso!");
            System.out.println(cliente);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }
    
    public void buscarClientePorDocumento() {
        System.out.println("\n=== BUSCAR CLIENTE POR DOCUMENTO ===");
        System.out.print("Documento: ");
        String documento = System.console().readLine();
        
        Optional<Cliente> cliente = clienteService.buscarPorDocumento(documento);
        if (cliente.isPresent()) {
            System.out.println("Cliente encontrado:");
            Cliente c = cliente.get();
            String marker = c.isAtivo() ? "" : " [INATIVO]";
            System.out.println(c + marker);
        } else {
            System.out.println("Cliente não encontrado com documento: " + documento);
        }
    }
}
