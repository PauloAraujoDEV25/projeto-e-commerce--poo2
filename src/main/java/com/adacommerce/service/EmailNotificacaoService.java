package com.adacommerce.service;

import com.adacommerce.model.Cliente;
import com.adacommerce.model.Venda;

public class EmailNotificacaoService implements NotificacaoService {
    
    @Override
    public void notificarPedidoFinalizado(Cliente cliente, Venda venda) {
        System.out.println("=== EMAIL ENVIADO ===");
        System.out.println("Para: " + cliente.getEmail());
        System.out.println("Assunto: Pedido Finalizado - Aguardando Pagamento");
        System.out.println("Mensagem: Olá " + cliente.getNome() + ",");
        System.out.println("Seu pedido #" + venda.getId() + " foi finalizado e está aguardando pagamento.");
        System.out.println("Valor total: R$ " + venda.getValorTotal());
        System.out.println("==================");
    }
    
    @Override
    public void notificarPagamentoRealizado(Cliente cliente, Venda venda) {
        System.out.println("=== EMAIL ENVIADO ===");
        System.out.println("Para: " + cliente.getEmail());
        System.out.println("Assunto: Pagamento Confirmado");
        System.out.println("Mensagem: Olá " + cliente.getNome() + ",");
        System.out.println("O pagamento do seu pedido #" + venda.getId() + " foi confirmado!");
        System.out.println("Valor pago: R$ " + venda.getValorTotal());
        System.out.println("Seu pedido será preparado para entrega.");
        System.out.println("==================");
    }
    
    @Override
    public void notificarEntregaRealizada(Cliente cliente, Venda venda) {
        System.out.println("=== EMAIL ENVIADO ===");
        System.out.println("Para: " + cliente.getEmail());
        System.out.println("Assunto: Pedido Entregue");
        System.out.println("Mensagem: Olá " + cliente.getNome() + ",");
        System.out.println("Seu pedido #" + venda.getId() + " foi entregue com sucesso!");
        System.out.println("Obrigado por comprar conosco!");
        System.out.println("==================");
    }
}
