package com.inlingo.components.expression;

import com.inlingo.contracts.SymbolType;

public class VariableDeclaration implements Statement {
    private final String name;
    private final SymbolType type;

    public VariableDeclaration(String name, SymbolType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public SymbolType getType() {
        return type;
    }

    @Override
    public void execute(com.inlingo.core.SymbolTable symbolTable) {
        // Implementation for variable declaration execution (if needed)
    }
} 