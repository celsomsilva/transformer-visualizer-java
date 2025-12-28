package com.celso.swingtransformer.core;

public record TransformerRun(
        String input,
        TokenSequence tokens,
        SimulationState simulation,
        String output
) {}
