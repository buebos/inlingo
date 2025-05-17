package com.inlingo.contracts;

public interface Scope {
    String getScopeName();

    Scope getEnclosingScope();

    Symbol resolve(String name);

    void define(Symbol sym);
}
