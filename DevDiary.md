# CipherSeive Developer Diary

### *A comprehensive record of development events comprising the creation of CipherSeive.*

#### September 30, 2025:
Work begins on CipherServe. The goal is to create a program that can parse input, typically sourced from a web browser, containing stored passwords, then analyze the strength of each password. The program will then report findings to the user.

Learnt:
- Maven fundamentals
- Shannon entropy
- N-gram rarity


#### October 4, 2025:
Researched Character Space entropy; I have decided to use it in conjunction with Shannon entropy for the best results. The first to classes were created: the EntropyCalculator class, and it's test class, EntropyCalculatorTest. I organized the class files as well; they're now properly sorted.

Learnt:
- Character Space entropy
- Dusted off my TDD skills by using JUnit 5.