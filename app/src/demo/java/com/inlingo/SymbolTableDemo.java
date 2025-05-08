package com.inlingo;

import com.inlingo.contracts.Scope;

public class SymbolTableDemo {
    public static void main(String[] args) {
        Scope currentScope;

        currentScope = new GlobalScope();

        currentScope.define(new BuiltInTypeSymbol("int"));
        currentScope.define(new BuiltInTypeSymbol("float"));
        currentScope.define(new BuiltInTypeSymbol("void"));

        StructSymbol ss = new StructSymbol("A", currentScope);
        currentScope.define(ss);
        currentScope = ss;

        BuiltInTypeSymbol t = (BuiltInTypeSymbol) currentScope.resolve("int");
        if (t == null) {
            throw new RuntimeException("Tipo 'int' no encontrado");
        }
        currentScope.define(new VariableSymbol("x", t));

        t = (BuiltInTypeSymbol) currentScope.resolve("float");
        if (t == null) {
            throw new RuntimeException("Tipo 'float' no encontrado");
        }
        currentScope.define(new VariableSymbol("y", t));

        currentScope = currentScope.getEnclosingScope();

        BuiltInTypeSymbol rt = (BuiltInTypeSymbol) currentScope.resolve("void");
        if (rt == null) {
            throw new RuntimeException("Tipo 'void' no encontrado");
        }

        MethodSymbol m = new MethodSymbol("f", rt, currentScope);
        currentScope.define(m);

        currentScope = m;

        currentScope = new LocalScope(currentScope);

        ss = (StructSymbol) currentScope.resolve("A");
        if (ss == null) {
            throw new RuntimeException("Struct 'A' no encontrado");
        }
        currentScope.define(new VariableSymbol("a", ss));

        VariableSymbol v = (VariableSymbol) currentScope.resolve("a");
        if (v == null) {
            throw new RuntimeException("Variable 'a' no encontrada");
        }
        ss = (StructSymbol) v.type;
        v = (VariableSymbol) ss.resolveMember("x");
        if (v == null) {
            throw new RuntimeException("Campo 'x' no encontrado en struct 'A'");
        }

        currentScope = currentScope.getEnclosingScope();
        currentScope = currentScope.getEnclosingScope();
    }
}
