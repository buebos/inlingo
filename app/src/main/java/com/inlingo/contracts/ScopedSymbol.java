package com.inlingo.contracts;

import java.util.Map;

public abstract class ScopedSymbol extends Symbol implements Scope {
    protected Scope enclosingScope;

    public ScopedSymbol(String name, SymbolType type, Scope enclosingScope) {
        super(name, type);
        this.enclosingScope = enclosingScope;
    }

    public ScopedSymbol(String name, Scope enclosingScope) {
        super(name);
        this.enclosingScope = enclosingScope;
    }

    @Override
    public Symbol resolve(String name) {
        Symbol s = getMembers().get(name);

        if (s != null) {
            return s;
        }

        if (getEnclosingScope() != null) {
            return getEnclosingScope().resolve(name);
        }

        return null;
    }

    public Symbol resolveType(String name) {
        return resolve(name);
    }

    @Override
    public void define(Symbol sym) {
        getMembers().put(sym.name, sym);
        sym.scope = this;
    }

    @Override
    public Scope getEnclosingScope() {
        return enclosingScope;
    }

    @Override
    public String getScopeName() {
        return name;
    }

    /**
     * Indicate how subclasses store scope members.
     * Allows us to factor out common code in this class.
     */
    public abstract Map<String, Symbol> getMembers();
}
