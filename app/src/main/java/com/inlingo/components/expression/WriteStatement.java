package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;
import java.util.List;

public class WriteStatement implements Statement {
    private final List<Expression> expressions;

    public WriteStatement(List<Expression> expressions) {
        this.expressions = expressions;
    }

    @Override
    public void execute(SymbolTable symbolTable) {
        for (Expression expr : expressions) {
            System.out.print(expr.evaluate(symbolTable));
        }
        System.out.println();
    }
} 