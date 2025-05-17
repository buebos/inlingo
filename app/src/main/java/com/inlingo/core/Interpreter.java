package com.inlingo.core;

import com.inlingo.components.expression.Statement;
import java.util.List;

public class Interpreter {
    private final SymbolTable symbolTable;

    public Interpreter(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void interpret(List<Statement> statements) {
        for (Statement statement : statements) {
            statement.execute(symbolTable);
        }
    }
} 