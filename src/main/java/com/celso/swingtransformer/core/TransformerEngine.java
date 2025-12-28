package com.celso.swingtransformer.core;

import com.celso.swingtransformer.backend.ModelBackend;

import java.util.Objects;

public class TransformerEngine {
    private final ModelBackend backend;
    private final Tokenizer tokenizer;
    private final FakeTransformerSimulator simulator;

    public TransformerEngine(ModelBackend backend) {
        this.backend = Objects.requireNonNull(backend, "backend");
        this.tokenizer = new Tokenizer();
        this.simulator = new FakeTransformerSimulator();
    }

    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    public FakeTransformerSimulator getSimulator() {
        return simulator;
    }

    public ModelBackend getBackend() {
        return backend;
    }

    /**
     * End-to-end pipeline: tokenize -> simulate internals -> "generate" response.
     * In mock mode, response is produced by a simple decoding heuristic.
     * Later you can swap backend with a real LLaMA caller.
     */
    public TransformerRun run(String input, EngineConfig config) {
        if (input == null) input = "";
        TokenSequence tokens = tokenizer.tokenize(input);
        SimulationState sim = simulator.simulate(tokens, config);
        String output = backend.generateText(input, sim, config);
        return new TransformerRun(input, tokens, sim, output);
    }
}
