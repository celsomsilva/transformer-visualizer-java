package com.celso.swingtransformer.core;

import java.util.Collections;
import java.util.List;

public class TokenSequence {
    private final List<Token> tokens;

    public TokenSequence(List<Token> tokens) {
        this.tokens = List.copyOf(tokens);
    }

    public List<Token> tokens() {
        return Collections.unmodifiableList(tokens);
    }

    public int size() {
        return tokens.size();
    }

    public Token get(int i) {
        return tokens.get(i);
    }
}
