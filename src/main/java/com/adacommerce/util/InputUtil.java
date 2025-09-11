package com.adacommerce.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

public class InputUtil {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    
    public static String lerString(String prompt) {
        System.out.print(prompt);
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Erro ao ler entrada: " + e.getMessage());
            return "";
        }
    }
    
    public static int lerInt(String prompt) {
        while (true) {
            try {
                String input = lerString(prompt);
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Por favor, digite um número válido.");
            }
        }
    }
    
    public static long lerLong(String prompt) {
        while (true) {
            try {
                String input = lerString(prompt);
                return Long.parseLong(input);
            } catch (NumberFormatException e) {
                System.err.println("Por favor, digite um número válido.");
            }
        }
    }
    
    public static double lerDouble(String prompt) {
        while (true) {
            try {
                String input = lerString(prompt);
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.err.println("Por favor, digite um número válido.");
            }
        }
    }
    
    public static BigDecimal lerBigDecimal(String prompt) {
        while (true) {
            try {
                String input = lerString(prompt);
                return new BigDecimal(input);
            } catch (NumberFormatException e) {
                System.err.println("Por favor, digite um número válido.");
            }
        }
    }
}
