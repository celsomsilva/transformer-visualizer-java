# Swing Transformer — Visual, Didactic Transformer Simulator (Java)


---

## Project Status

This repository is **work-in-progress**. 

---



A **desktop-first** (Java Swing) project that makes Transformer mechanics **explicit** and **inspectable**.

This is **not** a production LLM implementation.
It is a **visual simulator** designed to help you understand the flow:

1) text input → 2) tokenization → 3) (fake) attention → 4) (fake) logits → 5) decoding → 6) final response

You can optionally plug in a real backend later (e.g., call a local LLaMA binary), but the default mode is fully offline and deterministic.

---

## Why Swing?

Swing is intentionally used here because it is:
- deterministic and great for step-by-step inspection,
- easy to ship as a single desktop tool,
- perfect for an “instrument panel” UI (tokens, matrices, logits, decoding trace).

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
- Gradle (wrapper not included in this draft)

---

## Run

```bash
gradle run
```
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
