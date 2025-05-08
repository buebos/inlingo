package com.inlingo.contracts;

public class Symbol {
    String name;
    SymbolType type;
    Scope scope;

    public Symbol(String name) {
        this.name = name;
    }

    public Symbol(String name, SymbolType type) {
        this(name);
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        if (type != null) {
            return '<' + getName() + ":" + type + '>';
        }

        return getName();
    }
}