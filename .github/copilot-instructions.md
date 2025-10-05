
## CipherSeive — Copilot / AI Agent Instructions

This repository is a focused Java tool for analyzing exported password lists. AI agents should use this guide for rapid onboarding and effective contributions.

### Big Picture Architecture
- Reads password files (CSV, JSON) and generates reports (JSON, Markdown).
- Core modules: Entropy calculation (`EntropyCalculator.java`), password reuse detection, n-gram rarity scoring.
- Data flow: Input files → analysis classes → report generation.

### Key Files & Directories
- `README.md`: Project goals, output formats, and usage.
- `DevDiary.md`: Developer notes, recent changes, and design decisions.
- `src/main/java/com/wastronomer/cipherseive/`: Main code (e.g., `EntropyCalculator.java`).
- `src/test/java/com/wastronomer/cipherseive/`: Unit tests (e.g., `EntropyCalculatorTest.java`).
- `test_files/passwords.csv`: Canonical test input (single `password` column, quoted fields, non-ASCII, edge cases).

### Developer Workflows
- Build: `mvn compile` (requires `pom.xml` at root).
- Test: `mvn test` (unit tests in `src/test/java`).
- Package: `mvn package` (JAR in `target/`).
- Run main class: `mvn exec:java -Dexec.mainClass=com.wastronomer.cipherseive.Main` (if present) or `java -jar target/CipherSeive-*.jar`.

### Project-Specific Conventions
- Use defensive CSV parsing: handle quoted fields, embedded commas, empty lines, and non-ASCII (see `test_files/passwords.csv`).
- Entropy calculation: implement as pure function `double entropy(String password)` for testability.
- Password reuse: use exact string matching only (`Map<String,List<Record>>`).
- N-gram scoring: store counts as `Map<String,Integer>`, normalize at scoring time.
- Classes should be small and single-responsibility (e.g., `PasswordReader`, `EntropyCalculator`, `NGramScorer`, `ReportWriter`).

### AI Agent Editing Constraints
- Prefer JDK APIs and minimal dependencies. If adding a dependency, update `pom.xml` and justify the change.
- Always add/extend unit tests for new algorithms and edge cases (empty, long, quoted passwords).
- Use Java 17 features only; avoid frameworks.
- Place new code in correct Maven structure (`src/main/java`, `src/test/java`).

### Integration & External Data
- No external services. If n-gram corpora needed, add small samples to `src/main/resources` and document source.

### What Not To Do
- Never process/store real credentials; use only synthetic/sanitized data.
- Avoid large refactors; prefer incremental, test-driven changes.

### Examples & Patterns
- CSV parsing: reference `test_files/passwords.csv` and RFC 4180 rules.
- Entropy tests: include cases like `"aaaa"` (low entropy), `"aA1!"` (high entropy).

### Maintenance
- Keep this file up to date with new conventions and workflows.
- Ask the repo owner before adding CI or tooling.

---
**Reviewer Questions:**
- Confirm preferred package root (current: `com.wastronomer.cipherseive`).
- Should `pom.xml` be updated incrementally or all at once?
