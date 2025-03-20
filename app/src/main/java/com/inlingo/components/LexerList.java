package com.inlingo.components;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.inlingo.contracts.LexerContract;
import com.inlingo.contracts.ScannerContract;
import com.inlingo.core.Token;
import com.inlingo.core.TokenType;
import com.inlingo.exception.LexicalException;

public class LexerList extends LexerContract {
    private final ArrayList<Token> tokens = new ArrayList<>();
    private int cursor = -1;

    public LexerList(ScannerContract scanner) throws LexicalException {
        super(scanner);

        String sourceCode = this.scanner.toString();
        StringBuilder patternBuilder = new StringBuilder();

        for (TokenType tokenType : TokenType.values()) {
            patternBuilder.append(String.format("|(?<%s>%s)", tokenType.getName(), tokenType.getPattern()));
        }

        String pattern = patternBuilder.substring(1); // Remove the first '|' character

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(sourceCode);

        while (matcher.find()) {
            for (TokenType tokenType : TokenType.values()) {
                String match = matcher.group(tokenType.getName());

                if (match == null) {
                    continue;
                }

                // Skip whitespace tokens
                if (tokenType == TokenType.WHITESPACE) {
                    break;
                }

                // Throw exception for error tokens
                if (tokenType == TokenType.ERROR) {
                    throw new LexicalException(match);
                }

                String value = match;

                // Remove quotes from string literals
                if (tokenType == TokenType.STRING) {
                    value = value.substring(1, value.length() - 1);
                }

                tokens.add(new Token(tokenType, value));
                break;
            }
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    @Override
    public Token next() {
        if (this.cursor + 1 > tokens.size() - 1) {
            return null;
        }

        return this.tokens.get(++this.cursor);
    }

    @Override
    public Token current() {
        if (this.cursor < 0 || this.cursor > tokens.size() - 1) {
            return null;
        }

        return this.tokens.get(this.cursor);
    }
}