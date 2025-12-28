package com.celso.swingtransformer.core;

import java.util.*;

/**
 * Creates deterministic "fake" attention + logits.
 * The goal is to visualize the pipeline without needing a real model.
 */
public class FakeTransformerSimulator {

    public SimulationState simulate(TokenSequence inputTokens, EngineConfig config) {
        int N = Math.max(1, inputTokens.size());
        int steps = Math.max(1, Math.min(config.maxNewTokens, 32));

        Random rng = new Random(config.seed ^ (long) N * 1315423911L);

        List<double[][]> attn = new ArrayList<>(steps);
        List<TopLogits> logits = new ArrayList<>(steps);
        List<String> trace = new ArrayList<>(steps);

        String last = inputTokens.get(N - 1).text();

        for (int s = 0; s < steps; s++) {
            double[][] A = new double[N][N];

            // Make something that looks plausible: diagonal bias, a bit of recency, plus noise
            for (int i = 0; i < N; i++) {
                double rowSum = 0.0;
                for (int j = 0; j < N; j++) {
                    double diag = (i == j) ? 1.2 : 0.0;
                    double recency = 0.8 / (1.0 + Math.abs((N - 1) - j));
                    double noise = rng.nextDouble() * 0.4;
                    double v = diag + recency + noise;
                    A[i][j] = v;
                    rowSum += v;
                }
                // Normalize the row so it roughly sums to 1
                for (int j = 0; j < N; j++) A[i][j] /= rowSum;
            }

            attn.add(A);

            // Fake topK logits from a tiny "vocab" made from input + some extras
            List<String> vocab = buildTinyVocab(inputTokens);
            int K = Math.min(config.topK, vocab.size());

            List<ScoredToken> scored = new ArrayList<>();
            for (String t : vocab) {
            	// Bias things a bit toward repeating the last token, punctuation, and common words
                double base = rng.nextGaussian() * config.temperature;
                if (t.equalsIgnoreCase(last)) base += 1.0;
                if (".,!?".contains(t)) base += 0.4;
                if (t.equalsIgnoreCase("the") || t.equalsIgnoreCase("a")) base += 0.2;
                scored.add(new ScoredToken(t, base));
            }
            scored.sort(Comparator.comparingDouble(ScoredToken::logit).reversed());
            scored = scored.subList(0, K);

            // Turn logits into probabilities with a softmax
            List<Double> ls = scored.stream().map(ScoredToken::logit).toList();
            List<Double> ps = softmax(ls);

            List<String> ts = scored.stream().map(ScoredToken::token).toList();
            logits.add(new TopLogits(ts, ls, ps));

            int pick = argmax(ps);
            String chosen = ts.get(pick);
            trace.add("Step " + (s + 1) + ": chose token '" + chosen + "' (p=" + String.format(Locale.US, "%.3f", ps.get(pick)) + ")");
            last = chosen;
        }

        return new SimulationState(attn, logits, trace);
    }

    private List<String> buildTinyVocab(TokenSequence inputTokens) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        for (Token t : inputTokens.tokens()) set.add(t.text());
        // Extra tokens to make the decoding step look somewhat real
        Collections.addAll(set, "I", "think", "because", "so", "therefore", "however", ".", ",", "!", "?");
        return new ArrayList<>(set);
    }

    private List<Double> softmax(List<Double> logits) {
        double max = logits.stream().mapToDouble(Double::doubleValue).max().orElse(0.0);
        double sum = 0.0;
        double[] exps = new double[logits.size()];
        for (int i = 0; i < logits.size(); i++) {
            double e = Math.exp(logits.get(i) - max);
            exps[i] = e;
            sum += e;
        }
        List<Double> out = new ArrayList<>(logits.size());
        for (double e : exps) out.add(e / sum);
        return out;
    }

    private int argmax(List<Double> ps) {
        int best = 0;
        double v = ps.get(0);
        for (int i = 1; i < ps.size(); i++) {
            if (ps.get(i) > v) {
                v = ps.get(i);
                best = i;
            }
        }
        return best;
    }

    private record ScoredToken(String token, double logit) {}
}
