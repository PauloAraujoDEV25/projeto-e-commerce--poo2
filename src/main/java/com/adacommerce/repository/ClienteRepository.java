package com.adacommerce.repository;

import com.adacommerce.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    Optional<Cliente> buscarPorDocumento(String documento);
    List<Cliente> listarTodos();
    Cliente atualizar(Cliente cliente);
}
