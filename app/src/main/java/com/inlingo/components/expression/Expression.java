package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;

public interface Expression {
    Object evaluate(SymbolTable symbolTable);
} 