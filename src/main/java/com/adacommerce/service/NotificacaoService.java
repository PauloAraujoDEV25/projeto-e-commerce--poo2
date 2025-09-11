package com.adacommerce.service;

import com.adacommerce.model.Cliente;
import com.adacommerce.model.Venda;

public interface NotificacaoService {
    void notificarPedidoFinalizado(Cliente cliente, Venda venda);
    void notificarPagamentoRealizado(Cliente cliente, Venda venda);
    void notificarEntregaRealizada(Cliente cliente, Venda venda);
}
