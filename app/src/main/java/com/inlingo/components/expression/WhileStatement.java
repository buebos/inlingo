package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;
import java.util.List;

public class WhileStatement implements Statement {
    private final Expression condition;
    private final List<Statement> body;

    public WhileStatement(Expression condition, List<Statement> body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(SymbolTable symbolTable) {
        while (true) {
            Object result = condition.evaluate(symbolTable);
            if (!(result instanceof Boolean) || !(Boolean) result) {
                break;
            }
            for (Statement stmt : body) {
                stmt.execute(symbolTable);
            }
        }
    }

    public Expression getCondition() {
        return condition;
    }

    public List<Statement> getBody() {
        return body;
    }
} 