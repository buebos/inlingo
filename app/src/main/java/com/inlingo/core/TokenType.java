package com.inlingo.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Enum representing different types of tokens in the language.
 * Each token type has a name and a regex pattern for lexical analysis.
 */
public enum TokenType {
    // Keywords
    IF("if"),
    ELSE("else"),
    END_IF("end if"),
    WHILE("while"),
    END_WHILE("end while"),
    REPEAT("repeat"),
    END_REPEAT("end repeat"),
    READ("read"),
    WRITE("write"),
    PROGRAMSTART("begin program"),
    PROGRAMEND("end program"),
    VARIABLES("variables"),
    THEN("then"),
    FROM("from"),
    TO("to"),
    DO("do"),

    // Operators
    ARITHMETIC_OP("+", "-", "*", "/"),
    RELATIONALOP("==", "!=", "<", "<=", ">", ">="),
    LOGICAL_OP("and", "or", "not"),
    OPERATOR("&", "|"),

    // Punctuation
    LEFT_PAREN("("),
    RIGHT_PAREN(")"),
    LEFT_SQBRACKET("["),
    RIGHT_SQBRACKET("]"),
    COMMA(","),
    COLON(":"),
    SEMICOLON(";"),
    ASSIGNMENT("="),

    // Literals
    NUMBER,
    STRING,
    BOOLEAN,

    // Special
    IDENTIFIER,
    EOF;

    private final List<String> values;
    public static final Map<String, TokenType> keywords = new HashMap<>();

    static {
        keywords.put("if", IF);
        keywords.put("else", ELSE);
        keywords.put("end if", END_IF);
        keywords.put("while", WHILE);
        keywords.put("end while", END_WHILE);
        keywords.put("repeat", REPEAT);
        keywords.put("end repeat", END_REPEAT);
        keywords.put("read", READ);
        keywords.put("write", WRITE);
        keywords.put("true", BOOLEAN);
        keywords.put("false", BOOLEAN);
        keywords.put("begin program", PROGRAMSTART);
        keywords.put("end program", PROGRAMEND);
        keywords.put("variables", VARIABLES);
        keywords.put("then", THEN);
        keywords.put("end while", END_WHILE);
        keywords.put("if", IF);
        keywords.put("then", THEN);
        keywords.put("end if", END_IF);
        keywords.put("repeat", REPEAT);
        keywords.put("end repeat", END_REPEAT);
        keywords.put("from", FROM);
        keywords.put("to", TO);
        keywords.put("do", DO);
    }

    TokenType(String... values) {
        this.values = values.length > 0 ? Arrays.asList(values) : new ArrayList<>();
    }

    public List<String> getValues() {
        return values;
    }

    /**
     * Gets the string representation of the token type.
     *
     * @return The name of the token type
     */
    public String getName() {
        return name();
    }

    /**
     * Gets the regex pattern for this token type.
     *
     * @return The regex pattern string
     */
    public String getPattern() {
        return values.get(0);
    }

    /**
     * Returns the string representation of the token type.
     *
     * @return The name of the token type
     */
    @Override
    public String toString() {
        return name();
    }
}