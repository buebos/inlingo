package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;

public interface Statement {
    void execute(SymbolTable symbolTable);
} 