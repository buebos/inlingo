package com.inlingo.contracts;

public abstract class Expression {
    private SymbolType type;

    public Expression() {
    }

    public Expression(SymbolType type) {
        this.type = type;
    }

    public SymbolType getType() {
        return type;
    }

    public void setType(SymbolType type) {
        this.type = type;
    }
} 