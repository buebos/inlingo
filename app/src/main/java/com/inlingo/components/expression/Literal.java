package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;

public class Literal implements Expression {
    private final Object value;

    public Literal(Object value) {
        this.value = value;
    }

    @Override
    public Object evaluate(SymbolTable symbolTable) {
        return value;
    }

    public Object getValue() {
        return value;
    }
} 