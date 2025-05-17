package com.inlingo.core;

import com.inlingo.contracts.SymbolType;

public class BuiltinTypeSymbol implements SymbolType {
    public static final BuiltinTypeSymbol NUMBER = new BuiltinTypeSymbol("NUMBER");
    public static final BuiltinTypeSymbol STRING = new BuiltinTypeSymbol("STRING");
    public static final BuiltinTypeSymbol BOOLEAN = new BuiltinTypeSymbol("BOOLEAN");
    public static final BuiltinTypeSymbol VOID = new BuiltinTypeSymbol("VOID");

    private final String name;

    public BuiltinTypeSymbol(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
} 