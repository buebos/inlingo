package com.inlingo.components;

import com.inlingo.contracts.LexerContract;
import com.inlingo.contracts.ParserContract;
import com.inlingo.core.TokenType;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;

public class PseudoParser extends ParserContract {
    public PseudoParser(LexerContract lexer) throws LexicalException {
        super(lexer);
    }

    @Override
    public void parse() throws LexicalException, ParserException {
        if (!match(TokenType.PROGRAM_START)) {
            throw new ParserException("No 'start program' found.");
        }

        while (current() != TokenType.PROGRAM_END) {
            if (!statement()) {
                throw new ParserException("Expected valid statement. At token: " + lexer.current().getValue());
            }
        }

        if (!match(TokenType.PROGRAM_END)) {
            throw new ParserException("No 'end program' found.");
        }
    }

    private TokenType current() {
        return lexer.current().getType();
    }

    private boolean match(TokenType expectedType) {
        if (current() != expectedType) {
            return false;
        }

        lexer.next();

        return true;
    }

    private boolean statement() {
        switch (current()) {
            case TokenType.IDENTIFIER:
                return assignment();
            case TokenType.READ:
                return readStatement();
            case TokenType.WRITE:
                return writeStatement();
            case TokenType.IF:
                return ifStatement();
            case TokenType.WHILE:
                return whileStatement();
            case TokenType.REPEAT:
                return repeatStatement();
            case TokenType.VARIABLES:
                return variablesStatement();
            default:
                return false;
        }
    }

    private boolean assignment() {
        if (!match(TokenType.IDENTIFIER)) {
            return false;
        }

        if (!match(TokenType.ASSIGNMENT)) {
            return false;
        }

        if (!expression()) {
            return false;
        }

        return true;
    }

    private boolean readStatement() {
        if (!match(TokenType.READ)) {
            return false;
        }

        return match(TokenType.IDENTIFIER);
    }

    private boolean writeStatement() {
        if (!match(TokenType.WRITE)) {
            return false;
        }

        switch (current()) {
            case TokenType.IDENTIFIER:
                return match(TokenType.IDENTIFIER);
            case TokenType.STRING:
                match(TokenType.STRING);

                if (match(TokenType.COMMA)) {
                    return expression();
                }

                return true;
            default:
                return false;
        }
    }

    private boolean ifStatement() {
        if (!(match(TokenType.IF) && comparison() && match(TokenType.THEN))) {
            return false;
        }

        while (current() != TokenType.ENDIF) {
            if (!statement()) {
                return false;
            }
        }

        return match(TokenType.ENDIF);
    }

    private boolean whileStatement() {
        if (!(match(TokenType.WHILE) && comparison())) {
            return false;
        }

        while (current() != TokenType.ENDWHILE) {
            if (!statement()) {
                return false;
            }
        }

        return match(TokenType.ENDWHILE);
    }

    private boolean repeatStatement() {
        if (!(match(TokenType.REPEAT) &&
                match(TokenType.LEFT_PAREN) &&
                match(TokenType.IDENTIFIER) &&
                match(TokenType.COMMA) &&
                value() &&
                match(TokenType.COMMA) &&
                value() &&
                match(TokenType.RIGHT_PAREN))) {
            return false;
        }

        while (current() != TokenType.ENDREPEAT) {
            if (!statement()) {
                return false;
            }
        }

        return match(TokenType.ENDREPEAT);
    }

    private boolean expression() {
        if (!term()) {
            return false;
        }

        while (current() == TokenType.ARITHMETIC_OP) {
            if (!match(TokenType.ARITHMETIC_OP)) {
                return false;
            }

            if (!term()) {
                return false;
            }
        }

        return true;
    }

    private boolean term() {
        if (current() == TokenType.LEFT_PAREN) {
            if (!match(TokenType.LEFT_PAREN)) {
                return false;
            }

            if (!expression()) {
                return false;
            }

            return match(TokenType.RIGHT_PAREN);
        }

        return value();
    }

    private boolean comparison() {
        return (match(TokenType.LEFT_PAREN) &&
                value() &&
                match(TokenType.RELATIONAL_OP) &&
                value() &&
                match(TokenType.RIGHT_PAREN)

        );
    }

    private boolean value() {
        return match(TokenType.IDENTIFIER) || match(TokenType.NUMBER);
    }

    private boolean variablesStatement() {
        if (!(match(TokenType.VARIABLES) && match(TokenType.COLON) && match(TokenType.IDENTIFIER))) {
            return false;
        }

        while (match(TokenType.COMMA)) {
            if (!match(TokenType.IDENTIFIER)) {
                return false;
            }
        }

        return true;
    }
}