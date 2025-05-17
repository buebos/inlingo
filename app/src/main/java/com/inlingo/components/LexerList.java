package com.inlingo.components;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inlingo.core.Token;
import com.inlingo.core.TokenType;
import com.inlingo.exception.LexicalException;

public class LexerList {
    private final List<Token> tokens;
    private int current;
    private int line;

    // Regex pattern for tokenization
    private static final String TOKEN_PATTERN = 
        "(?<MULTIWORD>begin\\s+program|end\\s+program|end\\s+while|end\\s+if|end\\s+repeat|read\\s+variables|write\\s+variables)" +
        "|(?<KEYWORD>while|if|then|repeat|from|to|do|variables|read|write)" +
        "|(?<NUMBER>-?\\d+)" +
        "|(?<STRING>\"[^\"]*\")" +
        "|(?<ASSIGNMENT>\\s*=\\s*)" +
        "|(?<OPERATOR>[+*/<>!&|])" +
        "|(?<PUNCTUATION>[;:()\\[\\],])" +
        "|(?<IDENTIFIER>[a-zA-Z][a-zA-Z0-9]*)" +
        "|(?<WHITESPACE>\\s+)";

    public LexerList(String input) throws LexicalException {
        this.tokens = new ArrayList<>();
        this.current = 0;
        this.line = 1;
        tokenize(input);
    }

    private void tokenize(String input) throws LexicalException {
        int pos = 0;
        int length = input.length();
        while (pos < length) {
            // Skip whitespace and track line numbers
            if (Character.isWhitespace(input.charAt(pos))) {
                if (input.charAt(pos) == '\n') line++;
                pos++;
                continue;
            }
            Matcher matcher = Pattern.compile(TOKEN_PATTERN).matcher(input.substring(pos));
            if (matcher.lookingAt()) {
                if (matcher.group("MULTIWORD") != null) {
                    String multi = matcher.group("MULTIWORD");
                    TokenType type = TokenType.keywords.get(multi);
                    tokens.add(new Token(type, multi, line));
                    pos += multi.length();
                    continue;
                }
                if (matcher.group("KEYWORD") != null) {
                    String word = matcher.group("KEYWORD");
                    TokenType type = TokenType.keywords.get(word);
                    tokens.add(new Token(type, word, line));
                    pos += word.length();
                    continue;
                }
                if (matcher.group("STRING") != null) {
                    String str = matcher.group("STRING");
                    tokens.add(new Token(TokenType.STRING, str.substring(1, str.length() - 1), line));
                    pos += str.length();
                    continue;
                }
                if (matcher.group("NUMBER") != null) {
                    String numStr = matcher.group("NUMBER");
                    tokens.add(new Token(TokenType.NUMBER, numStr, line));
                    pos += numStr.length();
                    continue;
                }
                if (matcher.group("ASSIGNMENT") != null) {
                    tokens.add(new Token(TokenType.ASSIGNMENT, "=", line));
                    pos += matcher.group("ASSIGNMENT").length();
                    continue;
                }
                if (matcher.group("OPERATOR") != null) {
                    String op = matcher.group("OPERATOR");
                    TokenType type;
                    if (op.matches("[<>!]")) {
                        type = TokenType.RELATIONALOP;
                    } else if (op.matches("[+*/]")) {
                        type = TokenType.ARITHMETIC_OP;
                    } else {
                        type = TokenType.LOGICAL_OP;
                    }
                    tokens.add(new Token(type, op, line));
                    pos += op.length();
                    continue;
                }
                if (matcher.group("PUNCTUATION") != null) {
                    String punct = matcher.group("PUNCTUATION");
                    TokenType type = null;
                    switch (punct) {
                        case "[": type = TokenType.LEFT_SQBRACKET; break;
                        case "]": type = TokenType.RIGHT_SQBRACKET; break;
                        case ",": type = TokenType.COMMA; break;
                        case ":": type = TokenType.COLON; break;
                        case "(": type = TokenType.LEFT_PAREN; break;
                        case ")": type = TokenType.RIGHT_PAREN; break;
                        case ";": type = TokenType.SEMICOLON; break;
                    }
                    tokens.add(new Token(type, punct, line));
                    pos += punct.length();
                    continue;
                }
                if (matcher.group("IDENTIFIER") != null) {
                    String word = matcher.group("IDENTIFIER");
                    if (word.equals("true") || word.equals("false")) {
                        tokens.add(new Token(TokenType.BOOLEAN, word, line));
                    } else {
                        tokens.add(new Token(TokenType.IDENTIFIER, word, line));
                    }
                    pos += word.length();
                    continue;
                }
            }
            // Unrecognized character
            throw new LexicalException("Invalid token: '" + input.charAt(pos) + "' at line " + line);
        }
    }

    public Token next() {
        if (current >= tokens.size()) {
            return null;
        }
        return tokens.get(current++);
    }

    public Token current() {
        if (current >= tokens.size()) {
            return tokens.get(tokens.size() - 1);
        }
        return tokens.get(current);
    }

    public List<Token> getTokens() {
        return tokens;
    }
}