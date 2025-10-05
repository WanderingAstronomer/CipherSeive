package com.wastronomer.cipherseive;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * CLI entrypoint for CipherSeive entropy testing.
 */
public class Main {
    public static void main(String[] args) {
        boolean debug = args.length > 0 && args[0].equals("--debug");
        boolean plot = args.length > 0 && args[0].equals("--plot");

        if (plot) {
            plotAlpha();
            return;
        }

        System.out.println("Enter password:");
        try (Scanner scanner = new Scanner(System.in)) {
            String password = scanner.nextLine();

            int L = password.length();
            double alpha = EntropyCalculator.calculateAlpha(L);
            double H_cs = EntropyCalculator.calculateCharacterSpaceEntropy(password);
            double H_sh = EntropyCalculator.calculateShannonEntropy(password);
            double H_mix = EntropyCalculator.calculateHybridEntropy(password);

            System.out.printf("Length: %d%n", L);
            System.out.printf("α(L): %.4f%n", alpha);
            System.out.printf("Hcs: %.2f bits%n", H_cs);
            System.out.printf("Hsh: %.2f bits%n", H_sh);
            System.out.printf("Hmix: %.2f bits%n", H_mix);

            if (debug) {
                System.out.println("\n--- Character Distribution ---");
                Map<Character, Integer> charCounts = new HashMap<>();
                for (char c : password.toCharArray()) {
                    charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
                }
                charCounts.entrySet().stream()
                    .sorted(Map.Entry.<Character, Integer>comparingByValue().reversed())
                    .forEach(entry -> {
                        char c = entry.getKey();
                        int count = entry.getValue();
                        double prob = (double) count / L;
                        System.out.printf("  '%c': %2d  (%.1f%%)%n", c, count, prob * 100);
                    });
            }
        }
    }

    private static void plotAlpha() {
        System.out.println("α(L) vs Length:");
        System.out.println("L   α(L)  |" + "".repeat(50) + "|");
        for (int L = 0; L <= 30; L++) {
            double alpha = EntropyCalculator.calculateAlpha(L);
            int barLength = (int) (alpha * 50);
            String bar = "#".repeat(barLength);
            System.out.printf("%2d  %.3f  |%-50s|%n", L, alpha, bar);
        }
    }
}
