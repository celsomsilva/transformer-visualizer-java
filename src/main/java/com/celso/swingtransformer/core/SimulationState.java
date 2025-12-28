package com.celso.swingtransformer.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Contains a per-step simulated attention matrix + logits top-k.
 */
public class SimulationState {
    private final List<double[][]> attentionPerStep; // step -> [N][N]
    private final List<TopLogits> logitsPerStep;      // step -> topK
    private final List<String> decodeTrace;           // step-by-step decisions

    public SimulationState(List<double[][]> attentionPerStep, List<TopLogits> logitsPerStep, List<String> decodeTrace) {
        this.attentionPerStep = Collections.unmodifiableList(new ArrayList<>(attentionPerStep));
        this.logitsPerStep = Collections.unmodifiableList(new ArrayList<>(logitsPerStep));
        this.decodeTrace = Collections.unmodifiableList(new ArrayList<>(decodeTrace));
    }

    public int steps() {
        return Math.min(attentionPerStep.size(), logitsPerStep.size());
    }

    public double[][] attentionAt(int step) {
        return attentionPerStep.get(step);
    }

    public TopLogits logitsAt(int step) {
        return logitsPerStep.get(step);
    }

    public List<String> decodeTrace() {
        return decodeTrace;
    }
}
