package com.inlingo.components;

import com.inlingo.contracts.LexerContract;
import com.inlingo.contracts.ParserContract;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;
import com.inlingo.core.Token;
import com.inlingo.core.TokenType;

public class ParserLL1 extends ParserContract {
    protected Token lookahead;

    public ParserLL1(LexerContract lexer) throws LexicalException {
        super(lexer);
        this.lookahead = this.lexer.next();
    }

    @Override
    public void parse() throws ParserException, LexicalException {
        list();

        if (this.lookahead != null) {
            throw new ParserException("Unexpected token after end of list.");
        }
    }

    /**
     * Consumes the current token and advances to the next one.
     * 
     * @throws LexicalException If there's a lexical error when obtaining the next
     *                          token
     */
    protected void consume() throws LexicalException {
        lookahead = this.lexer.next();
    }

    /**
     * Verifies if the current token matches the expected type.
     * If it matches, consumes the token and advances. If not, throws an exception.
     * 
     * @param expected The expected token type
     * @throws ParserException  If the current token doesn't match the expected one
     * @throws LexicalException If there's a lexical error when consuming the token
     */
    protected void match(TokenType expected) throws ParserException, LexicalException {
        if (this.lookahead.getType() != expected) {
            throw new ParserException("Expected " + expected.getName() +
                    ", but found " + this.lookahead.toString());
        }

        this.consume();
    }

    private void list() throws ParserException, LexicalException {
        match(TokenType.LEFT_SQBRACKET);

        if (lookahead != null && lookahead.getType() == TokenType.RIGHT_SQBRACKET) {
            match(TokenType.RIGHT_SQBRACKET);
        } else {
            elements();

            if (lookahead == null) {
                throw new ParserException("Unexpected end of input. Missing closing bracket ']'");
            }

            match(TokenType.RIGHT_SQBRACKET);
        }
    }

    private void elements() throws ParserException, LexicalException {
        element();

        while (lookahead != null && lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);

            if (lookahead != null && lookahead.getType() == TokenType.RIGHT_SQBRACKET) {
                throw new ParserException("Unexpected end of list after comma. Expecting element.");
            }
            if (lookahead == null) {
                throw new ParserException("Unexpected end of input after comma. Expecting element.");
            }

            element();
        }
    }

    private void element() throws ParserException, LexicalException {
        if (lookahead == null) {
            throw new ParserException("Unexpected end of input. Expecting identifier or list.");
        }

        if (lookahead.getType() == TokenType.IDENTIFIER) {
            match(TokenType.IDENTIFIER);
        } else if (lookahead.getType() == TokenType.LEFT_SQBRACKET) {
            list();
        } else {
            throw new ParserException("Expecting identifier or list; found " + lookahead.toString());
        }
    }
}
