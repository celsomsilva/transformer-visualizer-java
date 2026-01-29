package com.celso.swingtransformer.core;

import com.celso.swingtransformer.backend.MockBackend;
import com.celso.swingtransformer.backend.ModelBackend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecodeTraceTest {

    @Test
    void simulationShouldProduceDecodeSteps() {
        ModelBackend backend = new MockBackend();
        TransformerEngine engine = new TransformerEngine(backend);

	EngineConfig cfg = new EngineConfig(
		1L,    // seed
		5,     // maxTokens
		3,     // topK
		0.5    // temperature
	);

        TransformerRun run = engine.run("hello", cfg);

        assertNotNull(run.simulation());
        assertFalse(run.simulation().decodeTrace().isEmpty());
    }
}

