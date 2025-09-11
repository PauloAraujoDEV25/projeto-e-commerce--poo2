package com.adacommerce.model;

public enum StatusVenda {
    ABERTO("Aberto"),
    AGUARDANDO_PAGAMENTO("Aguardando pagamento"),
    PAGO("Pago"),
    FINALIZADO("Finalizado");
    
    private final String descricao;
    
    StatusVenda(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}
