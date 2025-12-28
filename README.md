# Swing Transformer — Visual, Didactic Transformer Simulator (Java)



A **desktop-first** (Java Swing) project that makes Transformer mechanics **explicit** and **inspectable**.

This project is intentionally not built with PyTorch, JAX, or Transformers libraries.
It is meant to make the mechanics visible — not hidden behind abstractions.

This is **not** a production LLM implementation.

Tokenization, attention and logits are intentionally simulated
and are **not compatible with real trained models**.

It is a **visual simulator** designed to help you understand the flow:

1) text input  
2) tokenization  
3) (simulated) attention  
4) (simulated) logits  
5) decoding  
6) final response


---

## Why Swing (Yes, Really)

Yes, Swing is old.

That’s fine.

Modern frameworks are great at hiding complexity.
This project does the opposite: it tries to show what’s going on.

Swing keeps state changes visible.
Nothing happens “by magic” behind the scenes.

That makes it a good fit for a step-by-step, inspectable simulator.

It’s also:
- deterministic,
- easy to ship as a single desktop tool,
- well suited for an “instrument panel” style UI (tokens, matrices, logits, decoding trace).

---

## Features

- **Token view**: shows tokens and ids.
- **Attention view (simulated)**: displays an attention matrix (NxN) per step.
- **Logits view (simulated)**: displays top-k “logits” and probabilities.
- **Decode trace**: shows decoding decisions step-by-step.
- **Reproducible**: seeded simulation so the same input yields the same “fake” internals.

---

## Project Structure

- `src/main/java/.../ui` — Swing UI panels
- `src/main/java/.../core` — tokenizer, simulator, decoding logic
- `src/main/java/.../backend` — pluggable backend interface (mock by default)

---

## Requirements

- Java 17+  
- Gradle 

Gradle is only required for building the project.
End users do **not** need Gradle.

---

## Run (Developers)

```bash
gradle run
```
---

## Download & Run (End Users)

1. Go to the **Releases** section of this repository.
2. Download the file: `swing-transformer.jar`.
3. Run it using one of the options below.

**Option A — Any OS**

```bash
java -jar swing-transformer.jar
```
**Option B — Shortcut**

Windows: double-click run.bat 

Linux / macOS: ./run.sh

> Windows users,
> 
> `run.bat` only executes:
> 
> java -jar swing-transformer.jar
> 
> No files are modified, no network access is performed,
> and no administrative privileges are required.


---

## What this project is NOT

This project is intentionally **not**:
- a full LLM implementation
- a training framework
- a performance benchmark
- a replacement for real inference engines

Its goal is **understanding**, not optimization.

---

## How to demo this project (3 minutes)

1. Run the application.
2. Enter a short prompt (one sentence is enough).
3. Click **Run**.
4. Inspect:
   - how text is tokenized,
   - how attention matrices change per step,
   - how logits influence decoding,
   - how the final response is formed token by token.

---

## Why this exists

Many modern frameworks optimize for performance and abstraction.
This project goes in the opposite direction.

It exists to make internal states visible,
even if that means being slower, simpler,
or less "realistic" than production systems.


---

## Author

This project was developed by an engineer and data scientist with a background in:

* Postgraduate degree in **Data Science and Analytics (USP)**
* Bachelor's degree in **Computer Engineering (UERJ)**
* Special interest in statistical models, interpretability, and applied AI

---

## Contact  

- [LinkedIn](https://linkedin.com/in/celso-m-silva)  
- Or open an [issue](https://github.com/celsomsilva/transformer-visualizer-java/issues)
