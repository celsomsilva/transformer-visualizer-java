package com.celso.swingtransformer.backend;

import com.celso.swingtransformer.core.EngineConfig;
import com.celso.swingtransformer.core.SimulationState;

/**
 * A pluggable backend. In a real version, this might call:
 * - a local llama.cpp binary
 * - an HTTP service
 * - an embedded Java model
 *
 * For this draft, MockBackend returns a deterministic "response".
 */
public interface ModelBackend {
    String generateText(String prompt, SimulationState sim, EngineConfig config);
}
