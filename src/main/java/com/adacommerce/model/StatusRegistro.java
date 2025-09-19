package com.adacommerce.model;

/**
 * Representa o status genérico de um registro que pode ser ativado/inativado logicamente.
 */
public enum StatusRegistro {
    ATIVO,
    INATIVO;

    public boolean isAtivo() { return this == ATIVO; }
}
