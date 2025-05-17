package com.inlingo.command;

import com.inlingo.components.LexerList;
import com.inlingo.components.PseudoParser;
import com.inlingo.core.SymbolTable;
import com.inlingo.core.Interpreter;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;
import com.inlingo.contracts.Command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RunCommand implements Command {
    private String filePath;

    public RunCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        try {
            executeFile(filePath);
        } catch (IOException | LexicalException | ParserException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void executeFile(String filePath) throws IOException, LexicalException, ParserException {
        String sourceCode = Files.readString(Path.of(filePath));
        executeSource(sourceCode);
    }

    public void executeSource(String sourceCode) throws LexicalException, ParserException {
        // Create scanner and lexer
        LexerList lexer = new LexerList(sourceCode);

        // Parse the input and build AST
        SymbolTable symbolTable = new SymbolTable();
        PseudoParser parser = new PseudoParser(lexer, symbolTable);
        parser.parse();

        // Execute the program
        Interpreter interpreter = new Interpreter(symbolTable);
        interpreter.interpret(parser.getStatements());
    }
}
