package com.inlingo.contracts;

import com.inlingo.core.Token;
import com.inlingo.core.TokenType;
import com.inlingo.exception.LexicalException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public abstract class LexerContract {
    protected final Scanner scanner;
    protected int line;
    protected static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("if", TokenType.IF);
        keywords.put("else", TokenType.ELSE);
        keywords.put("while", TokenType.WHILE);
        keywords.put("read", TokenType.READ);
        keywords.put("write", TokenType.WRITE);
        keywords.put("true", TokenType.BOOLEAN);
        keywords.put("false", TokenType.BOOLEAN);
    }

    protected LexerContract(Scanner scanner) {
        this.scanner = scanner;
        this.line = 1;
    }

    public abstract Token next() throws LexicalException;
    public abstract Token current();
}
