package com.inlingo.components;

import com.inlingo.contracts.LexerContract;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;
import com.inlingo.core.TokenType;
import com.inlingo.contracts.ParserContract;
import com.inlingo.core.Token;

/**
 * Implementation of a recursive descent LL(k) parser for lists and assignment
 * statements.
 * This parser uses multiple lookahead tokens for more powerful parsing.
 */
public class ParserLLK extends ParserContract {
    protected final Token[] lookahead;
    protected final int k;
    protected int p = 0;

    public ParserLLK(LexerContract lexer, int k) throws LexicalException {
        super(lexer);
        this.k = k;
        this.lookahead = new Token[k];

        for (int i = 0; i < k; i++) {
            this.consume();
        }

        p = 0;
    }

    protected void consume() throws LexicalException {
        lookahead[p] = lexer.next();
        p = (p + 1) % k;
    }

    protected void match(TokenType expected) throws ParserException, LexicalException {
        if (LA(1) != expected) {
            throw new ParserException("Expected " + expected.getName() +
                    ", but found " + LA(1).getName());
        }

        consume();
    }

    /**
     * Gets the type of the token at a specific depth.
     * 
     * @param i Token depth (1 for the current token, 2 for the next one, etc.)
     * @return The type of the token at the specified depth
     */
    protected TokenType LA(int i) {
        Token token = LT(i);
        return token != null ? token.getType() : null;
    }

    /**
     * Gets the token at a specific depth.
     * 
     * @param i Token depth (1 for the current token, 2 for the next one, etc.)
     * @return The token at the specified depth
     */
    protected Token LT(int i) {
        if (i <= 0 || i > k) {
            throw new IllegalArgumentException("Token depth must be between 1 and " + k);
        }

        return lookahead[(p + i - 1) % k];
    }

    @Override
    public void parse() throws ParserException, LexicalException {
        if (LT(1) == null) {
            return;
        }

        list();

        if (LA(1) != null) {
            throw new ParserException("Unexpected token after end of list.");
        }
    }

    private void list() throws ParserException, LexicalException {
        match(TokenType.LEFT_SQBRACKET);

        if (LA(1) != null && LA(1) == TokenType.RIGHT_SQBRACKET) {
            match(TokenType.RIGHT_SQBRACKET);
        } else {
            elements();

            if (LA(1) == null) {
                throw new ParserException("Unexpected end of input. Missing closing bracket ']'");
            }

            match(TokenType.RIGHT_SQBRACKET);
        }
    }

    private void elements() throws ParserException, LexicalException {
        element();

        while (lookahead != null && LA(1) == TokenType.COMMA) {
            match(TokenType.COMMA);

            if (LA(1) != null && LA(1) == TokenType.RIGHT_SQBRACKET) {
                throw new ParserException("Unexpected end of list after comma. Expecting element.");
            }
            if (LA(1) == null) {
                throw new ParserException("Unexpected end of input after comma. Expecting element.");
            }

            element();
        }
    }

    private void element() throws ParserException, LexicalException {
        if (LA(1) == TokenType.IDENTIFIER && LA(2) == TokenType.ASSIGNMENT) {
            match(TokenType.IDENTIFIER);
            match(TokenType.ASSIGNMENT);
            match(TokenType.IDENTIFIER);
        } else if (LA(1) == TokenType.IDENTIFIER) {
            match(TokenType.IDENTIFIER);
        } else if (LA(1) == TokenType.LEFT_SQBRACKET) {
            list();
        } else {
            throw new ParserException("expecting identifier or list; found " + LT(1));
        }
    }
}
