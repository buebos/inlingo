package com.inlingo.core;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Object> symbols;

    public SymbolTable() {
        this.symbols = new HashMap<>();
    }

    public void define(String name, Object value) {
        symbols.put(name, value);
    }

    public void set(String name, Object value) {
        if (!has(name)) {
            throw new RuntimeException("Variable '" + name + "' is not defined");
        }
        symbols.put(name, value);
    }

    public Object get(String name) {
        if (!has(name)) {
            throw new RuntimeException("Variable '" + name + "' is not defined");
        }
        return symbols.get(name);
    }

    public boolean has(String name) {
        return symbols.containsKey(name);
    }
} 