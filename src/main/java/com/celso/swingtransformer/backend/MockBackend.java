package com.celso.swingtransformer.backend;

import com.celso.swingtransformer.core.EngineConfig;
import com.celso.swingtransformer.core.SimulationState;
import com.celso.swingtransformer.core.TopLogits;

import java.util.Locale;

/**
 * Generates a response by reading the simulated logits and building a short text.
 * This keeps the project fully offline and runnable.
 */
public class MockBackend implements ModelBackend {

    @Override
    public String generateText(String prompt, SimulationState sim, EngineConfig config) {
        StringBuilder sb = new StringBuilder();
        sb.append("Mock response (didactic):\n\n");

        // Pick a few tokens and stitch them together as a fake reply
        int steps = Math.min(sim.steps(), Math.max(6, Math.min(config.maxNewTokens, 14)));
        for (int s = 0; s < steps; s++) {
            TopLogits tl = sim.logitsAt(s);
            if (tl.tokens().isEmpty()) continue;
            String tok = tl.tokens().get(0); // just take the top token for now
            if (needsSpace(sb, tok)) sb.append(' ');
            sb.append(tok);
        }

        sb.append("\n\n");
        sb.append("— (This text is generated from simulated logits, not a real LLM.)\n");
        sb.append(String.format(Locale.US, "seed=%d, topK=%d, temperature=%.2f", config.seed, config.topK, config.temperature));
        return sb.toString();
    }

    private boolean needsSpace(StringBuilder sb, String tok) {
        if (sb.length() == 0) return false;
        // Small cleanup so punctuation doesn't look weird
        return !(".,!?".contains(tok));
    }
}
