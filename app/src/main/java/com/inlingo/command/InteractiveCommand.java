package com.inlingo.command;

import com.inlingo.components.LexerList;
import com.inlingo.components.PseudoParser;
import com.inlingo.components.ScannerString;
import com.inlingo.core.SymbolTable;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;
import com.inlingo.contracts.Command;

import java.util.Scanner;

public class InteractiveCommand implements Command {
    private final SymbolTable symbolTable;
    private final Scanner scanner;

    public InteractiveCommand() {
        this.symbolTable = new SymbolTable();
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void execute() {
        start();
    }

    public void start() {
        System.out.println("Inlingo Interactive Interpreter");
        System.out.println("Type 'exit' to quit");
        
        while (true) {
            System.out.print(">>> ");
            String input = scanner.nextLine().trim();
            
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            
            try {
                // Create lexer
                LexerList lexer = new LexerList(input);
                
                // Parse the input
                PseudoParser parser = new PseudoParser(lexer, symbolTable);
                parser.parse();
                
                // TODO: Add interpreter execution here once the AST generation is complete
                System.out.println("Tokens: " + lexer.getTokens());
                
            } catch (LexicalException | ParserException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
} 