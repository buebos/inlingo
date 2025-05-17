package com.inlingo.components.expression;

import com.inlingo.core.SymbolTable;
import java.util.Scanner;

public class ReadStatement implements Statement {
    private final String name;
    private static final Scanner scanner = new Scanner(System.in);

    public ReadStatement(String name) {
        this.name = name;
    }

    @Override
    public void execute(SymbolTable symbolTable) {
        String input = scanner.nextLine();
        try {
            // Try to parse as number first
            double value = Double.parseDouble(input);
            symbolTable.set(name, value);
        } catch (NumberFormatException e) {
            // If not a number, store as string
            symbolTable.set(name, input);
        }
    }
} 