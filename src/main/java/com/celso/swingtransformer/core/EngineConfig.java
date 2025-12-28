package com.celso.swingtransformer.core;

public class EngineConfig {
    public final long seed;
    public final int maxNewTokens;
    public final int topK;
    public final double temperature;

    public EngineConfig(long seed, int maxNewTokens, int topK, double temperature) {
        this.seed = seed;
        this.maxNewTokens = maxNewTokens;
        this.topK = topK;
        this.temperature = temperature;
    }

    public static EngineConfig defaults() {
        return new EngineConfig(42L, 24, 8, 0.9);
    }
}
