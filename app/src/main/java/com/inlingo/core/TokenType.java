package com.inlingo.core;

/**
 * Enum representing different types of tokens in the language.
 * Each token type has a name and a regex pattern for lexical analysis.
 */
public enum TokenType {
    NUMBER("NUMBER", "-?\\d+(\\.\\d+)?"),
    STRING("STRING", "\"[^\"]*\""),
    ARITHMETIC_OP("ARITHMETICOP", "[+*/\\-]"),
    RELATIONAL_OP("RELATIONALOP", "<=|>=|==|<|>|!="),
    ASSIGNMENT("ASSIGNMENT", "="),
    COMMA("COMMA", ","),
    LEFT_PAREN("LEFTPAREN", "\\("),
    RIGHT_PAREN("RIGHTPAREN", "\\)"),
    LEFT_SQBRACKET("LEFTSQUAREBRACKET", "\\["),
    RIGHT_SQBRACKET("RIGHTSQUAREBRACKET", "\\]"),
    PROGRAM_START("PROGRAMSTART", "begin\\s+program"),
    PROGRAM_END("PROGRAMEND", "end\\s+program"),
    READ("READ", "read"),
    WRITE("WRITE", "write"),
    IF("IF", "if"),
    THEN("THEN", "then"),
    ENDIF("ENDIF", "end\\s+if"),
    WHILE("WHILE", "while"),
    ENDWHILE("ENDWHILE", "end\\s+while"),
    VARIABLES("VARIABLES", "variables"),
    COLON("COLON", ":"),
    REPEAT("REPEAT", "repeat"),
    ENDREPEAT("ENDREPEAT", "end\\s+repeat"),
    IDENTIFIER("IDENTIFIER", "[a-zA-Z_][a-zA-Z0-9_]*"),
    WHITESPACE("WHITESPACE", "\\s+"),
    ERROR("ERROR", "[^\\s]+");

    private final String name;
    private final String pattern;

    /**
     * Constructor for TokenType enum.
     *
     * @param name    The string representation of the token type
     * @param pattern The regex pattern to match this token type
     */
    TokenType(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    /**
     * Gets the string representation of the token type.
     *
     * @return The name of the token type
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the regex pattern for this token type.
     *
     * @return The regex pattern string
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * Returns the string representation of the token type.
     *
     * @return The name of the token type
     */
    @Override
    public String toString() {
        return name;
    }
}