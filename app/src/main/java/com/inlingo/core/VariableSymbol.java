package com.inlingo.core;

public class VariableSymbol extends Symbol {
    private Object value;

    public VariableSymbol(String name, Type type) {
        super(name, type);
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString() + " = " + value;
    }
} 