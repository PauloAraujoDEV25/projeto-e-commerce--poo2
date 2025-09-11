package com.adacommerce.repository;

import com.adacommerce.model.Venda;
import com.adacommerce.model.Cliente;
import com.adacommerce.model.StatusVenda;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class VendaRepositoryImpl implements VendaRepository {
    private final Map<Long, Venda> vendas = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    @Override
    public Venda salvar(Venda venda) {
        if (venda.getId() == null) {
            venda.setId(idGenerator.getAndIncrement());
        }
        vendas.put(venda.getId(), venda);
        return venda;
    }
    
    @Override
    public Optional<Venda> buscarPorId(Long id) {
        return Optional.ofNullable(vendas.get(id));
    }
    
    @Override
    public List<Venda> listarTodas() {
        return new ArrayList<>(vendas.values());
    }
    
    @Override
    public List<Venda> buscarPorCliente(Cliente cliente) {
        return vendas.values().stream()
            .filter(venda -> venda.getCliente().equals(cliente))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Venda> buscarPorStatus(StatusVenda status) {
        return vendas.values().stream()
            .filter(venda -> venda.getStatus() == status)
            .collect(Collectors.toList());
    }
    
    @Override
    public Venda atualizar(Venda venda) {
        if (venda.getId() == null || !vendas.containsKey(venda.getId())) {
            throw new IllegalArgumentException("Venda não encontrada para atualização");
        }
        vendas.put(venda.getId(), venda);
        return venda;
    }
}
