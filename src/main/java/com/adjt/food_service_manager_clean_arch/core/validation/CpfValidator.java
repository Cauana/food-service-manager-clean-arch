package com.adjt.food_service_manager_clean_arch.core.validation;

public class CpfValidator {
    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int[] digits = cpf.chars().map(c -> c - '0').toArray();

            // 1º dígito verificador
            int sum = 0;
            for (int i = 0; i < 9; i++) sum += digits[i] * (10 - i);
            int firstCheck = 11 - (sum % 11);
            if (firstCheck >= 10) firstCheck = 0;
            if (firstCheck != digits[9]) return false;

            // 2º dígito verificador
            sum = 0;
            for (int i = 0; i < 10; i++) sum += digits[i] * (11 - i);
            int secondCheck = 11 - (sum % 11);
            if (secondCheck >= 10) secondCheck = 0;
            return secondCheck == digits[10];
        } catch (Exception e) {
            return false;
        }
    }
}
