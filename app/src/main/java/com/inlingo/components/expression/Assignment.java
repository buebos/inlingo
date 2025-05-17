package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;

public class Assignment implements Statement {
    private final String name;
    private final Expression value;

    public Assignment(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void execute(SymbolTable symbolTable) {
        symbolTable.set(name, value.evaluate(symbolTable));
    }
} 