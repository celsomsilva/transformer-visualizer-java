package com.celso.swingtransformer.core;

import com.celso.swingtransformer.backend.MockBackend;
import com.celso.swingtransformer.backend.ModelBackend;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimulationDeterminismTest {

        @Test
	void sameInputProducesSameOutputWithSameConfig() {
	    ModelBackend backend = new MockBackend();
	    TransformerEngine engine = new TransformerEngine(backend);

	    EngineConfig cfg = new EngineConfig(42L, 10, 5, 0.2);

	    TransformerRun run1 = engine.run("test input", cfg);
	    TransformerRun run2 = engine.run("test input", cfg);

	    assertEquals(run1.output(), run2.output());

	    assertEquals(run1.tokens().size(), run2.tokens().size());
	    for (int i = 0; i < run1.tokens().size(); i++) {
		assertEquals(
		    run1.tokens().get(i).text(),
		    run2.tokens().get(i).text()
		);
	    }
	}

}

