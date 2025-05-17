package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;

public class Variable implements Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public Object evaluate(SymbolTable symbolTable) {
        if (!symbolTable.has(name)) {
            throw new RuntimeException("Variable '" + name + "' is not defined");
        }
        return symbolTable.get(name);
    }
} 