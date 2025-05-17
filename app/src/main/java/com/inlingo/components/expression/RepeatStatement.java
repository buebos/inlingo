package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;
import java.util.List;

public class RepeatStatement implements Statement {
    private final String variable;
    private final Expression start;
    private final Expression end;
    private final List<Statement> body;

    public RepeatStatement(String variable, Expression start, Expression end, List<Statement> body) {
        this.variable = variable;
        this.start = start;
        this.end = end;
        this.body = body;
    }

    @Override
    public void execute(SymbolTable symbolTable) {
        int startValue = ((Number) start.evaluate(symbolTable)).intValue();
        int endValue = ((Number) end.evaluate(symbolTable)).intValue();
        
        for (int i = startValue; i <= endValue; i++) {
            symbolTable.set(variable, i);
            for (Statement stmt : body) {
                stmt.execute(symbolTable);
            }
        }
    }

    public String getVariable() {
        return variable;
    }

    public Expression getStart() {
        return start;
    }

    public Expression getEnd() {
        return end;
    }

    public List<Statement> getBody() {
        return body;
    }
} 