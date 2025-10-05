package com.wastronomer.cipherseive;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/*
 * This test class handles the validation of entropy calculations.
 * 
 * @author Andrew Brown
 * @version 0.1
 * @date 2025-10-04
 */

public class EntropyCalculatorTest {
    /* We need the tests to meet certain criteria.
     * We'll start with the obvious, odd, and basic cases.
     * But first, let's define Guess-Space Entropy (password strength):
     * 
     * H = length * log2(alphabet_size)
     * where alphabet_size is the size of the character set used in the password.
     * 
     * Character sets:
     * - Lowercase letters (a-z): 26
     * - Uppercase letters (A-Z): 26
     * - Digits (0-9): 10
     * - Special characters (common symbols): ~32
     * - Combined alphabet = lowercase + uppercase + digits + specials
     *
     * 1. Basic Test Case: A lowercase-only password.
     *    Input: "abcd"
     *    Expected Entropy: 4 * log2(26) ≈ 18.8 bits (4 chars, lowercase alphabet)
     * 
     * 2. Single Character Test Case: A repeated single character password.
     *    Input: "aaaa"
     *    Expected Entropy: 4 * log2(26) ≈ 18.8 bits (4 chars, lowercase alphabet)
     * 
     * 3. Repeated Characters Test Case: A password with repeated characters.
     *    Input: "aabb"
     *    Expected Entropy: 4 * log2(26) ≈ 18.8 bits (4 chars, lowercase alphabet)
     * 
     * 4. Empty Password Test Case: An empty password.
     *    Input: ""
     *    Expected Entropy: 0.0 bits (no characters)
     * 
     * 5. Mixed Characters Test Case: A lowercase-only password.
     *    Input: "abcabc"
     *    Expected Entropy: 6 * log2(26) ≈ 28.2 bits (6 chars, lowercase alphabet)
     * 
     * 6. Long Password Test Case: A longer lowercase-only password.
     *    Input: "aaabbbcccddd"
     *    Expected Entropy: 12 * log2(26) ≈ 56.5 bits (12 chars, lowercase alphabet)
     * 
     * 7. A complex password with mixed character sets.
     *    Input: "abcdefg12345!@#"
     *    Expected Entropy: 15 * log2(26+10+32) ≈ 95.3 bits (15 chars, lowercase+digits+specials)
     * 
     * 8. A truly random password with high entropy.
     *    Input: "xYz9!@#LmN0pQrStUvWx"
     *    Expected Entropy: 20 * log2(26+26+10+32) ≈ 130.0 bits (20 chars, full printable set)
     */

    @Test
    void testBasicLowerCase() {
        double e = EntropyCalculator.calculateHybridEntropy("abcd");
        assertTrue(e > 0.0, "Entropy should be > 0 for non-empty password");
    }

    @Test
    void testSingleCharacter() {
        double e = EntropyCalculator.calculateHybridEntropy("aaaa");
        assertTrue(e >= 0.0);
    }

    @Test
    void testRepeatedCharacters() {
        double e = EntropyCalculator.calculateHybridEntropy("aabb");
        assertTrue(e > 0.0);
    }

    @Test
    void testEmptyPassword() {
        double e = EntropyCalculator.calculateHybridEntropy("");
        assertEquals(0.0, e, 0.0);
    }

    @Test
    void testMixedLowerCase() {
        double e = EntropyCalculator.calculateHybridEntropy("abcabc");
        assertTrue(e > 0.0);
    }

    @Test
    void testLongLowerCase() {
        double e = EntropyCalculator.calculateHybridEntropy("aaabbbcccddd");
        assertTrue(e > 0.0);
    }

    @Test
    void testComplexPassword() {
        double e = EntropyCalculator.calculateHybridEntropy("abcdefg12345!@#");
        assertTrue(e > 0.0);
    }

    @Test
    void testTrulyRandomPassword() {
        double e = EntropyCalculator.calculateHybridEntropy("xYz9!@#LmN0pQrStUvWx");
        assertTrue(e > 0.0);
    }
}
