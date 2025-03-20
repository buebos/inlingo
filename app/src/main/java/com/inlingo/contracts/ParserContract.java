package com.inlingo.contracts;

import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;

/**
 * Abstract base class for recursive descent parsers.
 * Provides common infrastructure for all parsers.
 */
public abstract class ParserContract {
    protected LexerContract lexer; // Token source

    /**
     * Constructor for a basic parser.
     * 
     * @param input Lexer that provides the tokens
     * @throws LexicalException If there's a lexical error when obtaining the first
     *                          token
     */
    public ParserContract(LexerContract lexer) throws LexicalException {
        this.lexer = lexer;
    }

    public abstract void parse() throws LexicalException, ParserException;
}
