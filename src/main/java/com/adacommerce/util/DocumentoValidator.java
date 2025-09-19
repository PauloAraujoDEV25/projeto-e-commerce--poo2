package com.adacommerce.util;

/**
 * Valida documentos brasileiros (implementação atual: apenas CPF).
 */
public final class DocumentoValidator {
    private DocumentoValidator() {}

    public static boolean isCpfValido(String cpfRaw) {
        if (cpfRaw == null) return false;
        String cpf = cpfRaw.replaceAll("\\D", "");
        // deve ter exatamente 11 dígitos numéricos
        if (!cpf.matches("\\d{11}")) return false;
        // Rejeita sequências repetidas
        if (cpf.chars().distinct().count() == 1) return false;
        int d1 = calcularDigito(cpf, 9);
        int d2 = calcularDigito(cpf, 10);
        return d1 == cpf.charAt(9) - '0' && d2 == cpf.charAt(10) - '0';
    }

    private static int calcularDigito(String cpf, int length) {
        int soma = 0;
        int peso = length + 1;
        for (int i = 0; i < length; i++) {
            soma += (cpf.charAt(i) - '0') * (peso--);
        }
        int mod = soma % 11;
        return (mod < 2) ? 0 : 11 - mod;
    }
}
