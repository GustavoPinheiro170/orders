package com.amcom.order.utils;

public class Utils {

    // Método para verificar se o valor é nulo
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    // Método para verificar se o valor não é nulo
    public static boolean isNotNull(Object obj) {
        return obj != null;
    }

    // Método para verificar se a string é nula ou vazia
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    // Método para verificar se a string não é nula e nem vazia
    public static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    // Método para lançar exceção caso o valor seja nulo
    public static void requireNonNull(Object obj, String errorMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    // Método para lançar exceção caso a string seja nula ou vazia
    public static void requireNonNullOrEmpty(String str, String errorMessage) {
        if (isNullOrEmpty(str)) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}