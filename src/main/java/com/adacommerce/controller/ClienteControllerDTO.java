package com.adacommerce.controller;

import com.adacommerce.dto.ClienteDTO;
import com.adacommerce.dto.ClienteRequestDTO;
import com.adacommerce.service.ClienteServiceDTO;
import com.adacommerce.util.InputUtil;
import java.util.List;
import java.util.Optional;

public class ClienteControllerDTO {
    private final ClienteServiceDTO clienteService;
    
    public ClienteControllerDTO(ClienteServiceDTO clienteService) {
        this.clienteService = clienteService;
    }
    
    public void cadastrarCliente() {
        try {
            System.out.println("=== Cadastrar Cliente ===");
            
            String nome = InputUtil.lerString("Nome: ");
            String documento = InputUtil.lerString("Documento (CPF - 11 dígitos): ");
            String email = InputUtil.lerString("E-mail: ");
            
            ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(nome, documento, email);
            ClienteDTO clienteSalvo = clienteService.cadastrar(clienteRequestDTO);
            
            System.out.println("Cliente cadastrado com sucesso!");
            System.out.println(clienteSalvo);
            
        } catch (Exception e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }
    
    public void listarClientes() {
        try {
            System.out.println("=== Lista de Clientes ===");
            
            List<ClienteDTO> clientes = clienteService.listarTodos();
            
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
                return;
            }
            
            clientes.forEach(cliente -> {
                System.out.println("ID: " + cliente.getId());
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("Documento: " + cliente.getDocumento());
                System.out.println("E-mail: " + cliente.getEmail());
                if (cliente.getStatus() != null && !"ATIVO".equals(cliente.getStatus())) {
                    System.out.println("Status: " + cliente.getStatus());
                }
                System.out.println("---");
            });
            
        } catch (Exception e) {
            System.err.println("Erro ao listar clientes: " + e.getMessage());
        }
    }
    
    public void buscarClientePorId() {
        try {
            System.out.println("=== Buscar Cliente por ID ===");
            
            Long id = InputUtil.lerLong("ID do cliente: ");
            Optional<ClienteDTO> cliente = clienteService.buscarPorId(id);
            
            if (cliente.isPresent()) {
                System.out.println("Cliente encontrado:");
                ClienteDTO c = cliente.get();
                System.out.println(c + (c.getStatus() != null && !"ATIVO".equals(c.getStatus()) ? " [INATIVO]" : ""));
            } else {
                System.out.println("Cliente não encontrado com ID: " + id);
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
    }
    
    public void buscarClientePorDocumento() {
        try {
            System.out.println("=== Buscar Cliente por Documento ===");
            
            String documento = InputUtil.lerString("Documento (CPF - 11 dígitos): ");
            Optional<ClienteDTO> cliente = clienteService.buscarPorDocumento(documento);
            
            if (cliente.isPresent()) {
                System.out.println("Cliente encontrado:");
                ClienteDTO c = cliente.get();
                System.out.println(c + (c.getStatus() != null && !"ATIVO".equals(c.getStatus()) ? " [INATIVO]" : ""));
            } else {
                System.out.println("Cliente não encontrado com documento: " + documento);
            }
            
        } catch (Exception e) {
            System.err.println("Erro ao buscar cliente: " + e.getMessage());
        }
    }
    
    public void atualizarCliente() {
        try {
            System.out.println("=== Atualizar Cliente ===");
            
            Long id = InputUtil.lerLong("ID do cliente: ");
            
            // Verificar se cliente existe
            Optional<ClienteDTO> clienteExistente = clienteService.buscarPorId(id);
            if (clienteExistente.isEmpty()) {
                System.out.println("Cliente não encontrado com ID: " + id);
                return;
            }
            
            System.out.println("Cliente atual: " + clienteExistente.get());
            System.out.println("Digite os novos dados:");
            
            String nome = InputUtil.lerString("Nome: ");
            String documento = InputUtil.lerString("Documento: ");
            String email = InputUtil.lerString("E-mail: ");
            
            ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO(nome, documento, email);
            ClienteDTO clienteAtualizado = clienteService.atualizar(id, clienteRequestDTO);
            
            System.out.println("Cliente atualizado com sucesso!");
            System.out.println(clienteAtualizado);
            
        } catch (Exception e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
        }
    }
    
    public void menuClientes() {
        int opcao;
        do {
            System.out.println("\n=== MENU CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Buscar por Documento");
            System.out.println("5. Atualizar Cliente");
            System.out.println("6. Inativar Cliente");
            System.out.println("7. Ativar Cliente");
            System.out.println("0. Voltar");
            
            opcao = InputUtil.lerInt("Escolha uma opção: ");
            
            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    buscarClientePorId();
                    break;
                case 4:
                    buscarClientePorDocumento();
                    break;
                case 5:
                    atualizarCliente();
                    break;
                case 6:
                    inativarCliente();
                    break;
                case 7:
                    ativarCliente();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    private void inativarCliente() {
        try {
            System.out.println("=== Inativar Cliente ===");
            Long id = InputUtil.lerLong("ID do cliente: ");
            clienteService.inativar(id);
            System.out.println("Cliente inativado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao inativar cliente: " + e.getMessage());
        }
    }

    private void ativarCliente() {
        try {
            System.out.println("=== Ativar Cliente ===");
            Long id = InputUtil.lerLong("ID do cliente: ");
            clienteService.ativar(id);
            System.out.println("Cliente ativado com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao ativar cliente: " + e.getMessage());
        }
    }
}
