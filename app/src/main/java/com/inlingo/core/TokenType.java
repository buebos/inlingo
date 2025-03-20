package com.inlingo.core;

public class TokenType {
    private final String name;
    private final String pattern;

    public TokenType(String name, String pattern) {
        this.name = name;
        this.pattern = pattern;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public static final String NUMBER = "NUMBER";
    public static final String STRING = "STRING";
    public static final String ARITHMETIC_OP = "ARITHMETICOP";
    public static final String RELATIONAL_OP = "RELATIONALOP";
    public static final String ASSIGNMENT = "ASSIGNMENT";
    public static final String COMMA = "COMMA";
    public static final String LEFT_PAREN = "LEFTPAREN";
    public static final String RIGHT_PAREN = "RIGHTPAREN";
    public static final String PROGRAM_START = "PROGRAMSTART";
    public static final String PROGRAM_END = "PROGRAMEND";
    public static final String READ = "READ";
    public static final String WRITE = "WRITE";
    public static final String IF = "IF";
    public static final String THEN = "THEN";
    public static final String ENDIF = "ENDIF";
    public static final String WHILE = "WHILE";
    public static final String ENDWHILE = "ENDWHILE";
    public static final String IDENTIFIER = "IDENTIFIER";
    public static final String WHITESPACE = "WHITESPACE";
    public static final String ERROR = "ERROR";

    public static final String VARIABLES = "VARIABLES";
    public static final String COLON = "COLON";

    public static final String REPEAT = "REPEAT";
    public static final String ENDREPEAT = "ENDREPEAT";
}
