package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;

public class Binary implements Expression {
    private final Expression left;
    private final String operator;
    private final Expression right;

    public Binary(Expression left, String operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public String getOperator() {
        return operator;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public Object evaluate(SymbolTable symbolTable) {
        Object leftValue = left.evaluate(symbolTable);
        Object rightValue = right.evaluate(symbolTable);

        switch (operator) {
            case "+":
                if (leftValue instanceof Number && rightValue instanceof Number) {
                    return ((Number) leftValue).doubleValue() + ((Number) rightValue).doubleValue();
                }
                return leftValue.toString() + rightValue.toString();
            case "-":
                return ((Number) leftValue).doubleValue() - ((Number) rightValue).doubleValue();
            case "*":
                return ((Number) leftValue).doubleValue() * ((Number) rightValue).doubleValue();
            case "/":
                return ((Number) leftValue).doubleValue() / ((Number) rightValue).doubleValue();
            case "==":
                return leftValue.equals(rightValue);
            case "!=":
                return !leftValue.equals(rightValue);
            case "<":
                return ((Number) leftValue).doubleValue() < ((Number) rightValue).doubleValue();
            case "<=":
                return ((Number) leftValue).doubleValue() <= ((Number) rightValue).doubleValue();
            case ">":
                return ((Number) leftValue).doubleValue() > ((Number) rightValue).doubleValue();
            case ">=":
                return ((Number) leftValue).doubleValue() >= ((Number) rightValue).doubleValue();
            default:
                throw new RuntimeException("Unknown operator: " + operator);
        }
    }
} 