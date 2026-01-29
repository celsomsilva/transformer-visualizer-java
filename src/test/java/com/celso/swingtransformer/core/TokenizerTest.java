package com.celso.swingtransformer.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {

    @Test
    void shouldTokenizeSimpleSentence() {
        Tokenizer tokenizer = new Tokenizer();

        TokenSequence seq = tokenizer.tokenize("hello world");

        assertEquals(2, seq.size());
        assertEquals("hello", seq.get(0).text());
        assertEquals("world", seq.get(1).text());
    }

    @Test
    void shouldSplitPunctuation() {
        Tokenizer tokenizer = new Tokenizer();

        TokenSequence seq = tokenizer.tokenize("hello!");

        assertEquals(2, seq.size());
        assertEquals("hello", seq.get(0).text());
        assertEquals("!", seq.get(1).text());
    }
}

