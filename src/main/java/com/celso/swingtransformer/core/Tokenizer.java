package com.celso.swingtransformer.core;

import java.util.*;

public class Tokenizer {

    /**
     * A very simple whitespace tokenizer + punctuation splitting.
     * This is intentionally naive (didactic), and can be replaced later.
     */
    public TokenSequence tokenize(String text) {
        if (text == null) text = "";
        List<String> raw = split(text.trim());
        if (raw.isEmpty()) raw = List.of("<EMPTY>");

        // Create deterministic token ids just so the UI has something to show.
        // These ids are NOT compatible with real models.
        List<Token> tokens = new ArrayList<>();
        for (String t : raw) {
            int id = stableId(t);
            tokens.add(new Token(t, id));
        }
        return new TokenSequence(tokens);
    }

    private List<String> split(String s) {
        if (s.isEmpty()) return new ArrayList<>();
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) {
                flush(cur, out);
            } else if (isPunct(c)) {
                flush(cur, out);
                out.add(String.valueOf(c));
            } else {
                cur.append(c);
            }
        }
        flush(cur, out);
        return out;
    }

    private boolean isPunct(char c) {
        return ".,;:!?()[]{}\"'".indexOf(c) >= 0;
    }

    private void flush(StringBuilder cur, List<String> out) {
        if (cur.length() > 0) {
            out.add(cur.toString());
            cur.setLength(0);
        }
    }

    private int stableId(String token) {
        // Stable across runs + platforms: a simple FNV-1a style hash.
        long h = 1469598103934665603L;
        for (int i = 0; i < token.length(); i++) {
            h ^= token.charAt(i);
            h *= 1099511628211L;
        }
        // Reduce to a readable positive int range
        h = Math.abs(h);
        return (int) (h % 50000);
    }
}
