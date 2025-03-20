package com.inlingo.components;

import com.inlingo.contracts.ScannerContract;

public class ScannerString extends ScannerContract {
    private String source;
    private int cursor = 0;

    public ScannerString(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return this.source;
    }

    @Override
    public char next() {
        if (cursor >= source.length()) {
            return '\0';
        }

        return source.charAt(cursor++);
    }
}