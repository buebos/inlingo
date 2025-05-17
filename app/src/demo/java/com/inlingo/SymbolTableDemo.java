package com.inlingo;

import com.inlingo.core.SymbolTable;

public class SymbolTableDemo {
    public static void main(String[] args) {
        SymbolTable symbolTable = new SymbolTable();

        // Define variables
        symbolTable.define("x", 42);
        symbolTable.define("y", 3.14);
        symbolTable.define("name", "Alice");
        symbolTable.define("flag", true);

        // Print initial values
        System.out.println("x = " + symbolTable.get("x"));
        System.out.println("y = " + symbolTable.get("y"));
        System.out.println("name = " + symbolTable.get("name"));
        System.out.println("flag = " + symbolTable.get("flag"));

        // Set new values
        symbolTable.set("x", 100);
        symbolTable.set("y", 2.718);
        symbolTable.set("name", "Bob");
        symbolTable.set("flag", false);

        // Print updated values
        System.out.println("Updated x = " + symbolTable.get("x"));
        System.out.println("Updated y = " + symbolTable.get("y"));
        System.out.println("Updated name = " + symbolTable.get("name"));
        System.out.println("Updated flag = " + symbolTable.get("flag"));

        // Check existence
        System.out.println("Has x? " + symbolTable.has("x"));
        System.out.println("Has z? " + symbolTable.has("z"));
    }
}