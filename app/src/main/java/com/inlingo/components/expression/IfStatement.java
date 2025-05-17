package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;
import java.util.List;

public class IfStatement implements Statement {
    private final Expression condition;
    private final List<Statement> thenBranch;
    private final List<Statement> elseBranch;

    public IfStatement(Expression condition, List<Statement> thenBranch, List<Statement> elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    public void execute(SymbolTable symbolTable) {
        Object result = condition.evaluate(symbolTable);
        if (result instanceof Boolean && (Boolean) result) {
            for (Statement stmt : thenBranch) {
                stmt.execute(symbolTable);
            }
        } else {
            for (Statement stmt : elseBranch) {
                stmt.execute(symbolTable);
            }
        }
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getThenBranch() {
        return thenBranch;
    }

    public List<Statement> getElseBranch() {
        return elseBranch;
    }
} 