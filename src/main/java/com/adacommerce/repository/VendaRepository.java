package com.adacommerce.repository;

import com.adacommerce.model.Venda;
import com.adacommerce.model.Cliente;
import com.adacommerce.model.StatusVenda;
import java.util.List;
import java.util.Optional;

public interface VendaRepository {
    Venda salvar(Venda venda);
    Optional<Venda> buscarPorId(Long id);
    List<Venda> listarTodas();
    List<Venda> buscarPorCliente(Cliente cliente);
    List<Venda> buscarPorStatus(StatusVenda status);
    Venda atualizar(Venda venda);
}
