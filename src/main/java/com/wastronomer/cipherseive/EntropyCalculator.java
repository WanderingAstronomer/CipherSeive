package com.wastronomer.cipherseive;

import java.util.HashMap;
import java.util.Map;

/*
 * This class handles entropy calculations.
 * 
 * @author Andrew Brown
 * @version 0.1
 * @date 2025-10-04
 */

public class EntropyCalculator {
    // Specify character class sizes:
    private static final int LOWERCASE_SIZE = 26; // a-z
    private static final int UPPERCASE_SIZE = 26; // A-Z
    private static final int DIGIT_SIZE = 10;     // 0-9
    private static final int SYMBOL_SIZE = 32;    // Common ASCII symbols

    // Tanh weighting parameters
    private static final double L0 = 12.0; // Inflection point (password length)
    private static final double K = 0.4;   // Steepness of transition

    /**
     * Calculate the hybrid entropy of a password.
     * @param password The password to evaluate.
     * @return The calculated entropy in bits. (0.0 if password is null or empty)
     */
    public static double calculateHybridEntropy(String password) {
        // Handle edge cases
        if (password == null || password.isEmpty()) {
            return 0.0;
        }
        int L = password.length();
        double alpha = calculateAlpha(L);
        double H_cs = calculateCharacterSpaceEntropy(password);
        double H_sh = calculateShannonEntropy(password);
        return alpha * H_cs + (1 - alpha) * H_sh;
    }

    /**
     * Calculate the character space of a password.
     * @param password The password to evaluate.
     * @return The character space size.
     */
    public static double calculateCharacterSpaceEntropy(String password) {
        // Step 1: Detect which character classes are present
        boolean hasLowercase = false;
        boolean hasUppercase = false;
        boolean hasDigits = false;
        boolean hasSymbols = false;
        
        for (char c : password.toCharArray()) {
            if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isDigit(c)) {
                hasDigits = true;
            } else {
                // Anything else is a symbol (including non-ASCII)
                hasSymbols = true;
            }
        }
        
        // Step 2: Calculate total character space size
        int N = 0;
        if (hasLowercase) N += LOWERCASE_SIZE;
        if (hasUppercase) N += UPPERCASE_SIZE;
        if (hasDigits) N += DIGIT_SIZE;
        if (hasSymbols) N += SYMBOL_SIZE;
        
        // Step 3: Apply formula H = L * log2(N)
        int L = password.length();
        if (N == 0) return 0.0;  // Edge case: empty password
        
        return L * (Math.log(N) / Math.log(2)); // log base 2
    }

    /**
     * Calculate the Shannon entropy of a password.
     * @param password The password to evaluate.
     * @return The calculated Shannon entropy in bits.
     */
    public static double calculateShannonEntropy(String password) {
        int L = password.length();

        // Step 1: Calculate frequency of each character
        Map<Character, Integer> charCounts = new HashMap<>();
        for (char c : password.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        // Step 2: Calculate entropy using Shannon formula
        double H = 0.0;
        for (int count : charCounts.values()) {
            double p_i = (double) count / L; // Probability of character i
            H -= p_i * (Math.log(p_i) / Math.log(2)); // log base 2
        }
        return H;
    }

    /**
     * Calculates the weighting factor α(L) using hyperbolic tangent.
     * α(L) = (1 + tanh(k(L - L₀))) / 2
     * 
     * Properties:
     * - α → 0 as L → 0 (favor Shannon entropy for short passwords)
     * - α → 1 as L → ∞ (favor character space for long passwords)
     * - α ≈ 0.5 when L = L₀
     * 
     * @param length Password length
     * @return Weighting factor between 0 and 1
     */
    public static double calculateAlpha(int length) {
        return (1 + Math.tanh(K * (length - L0))) / 2.0;
    }    
}
