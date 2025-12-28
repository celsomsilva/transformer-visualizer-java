package com.celso.swingtransformer.core;

import java.util.List;

public record TopLogits(List<String> tokens, List<Double> logits, List<Double> probs) {}
