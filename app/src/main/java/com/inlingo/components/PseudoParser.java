package com.inlingo.components;

import java.util.ArrayList;
import java.util.List;

import com.inlingo.components.expression.Assignment;
import com.inlingo.components.expression.Binary;
import com.inlingo.components.expression.Expression;
import com.inlingo.components.expression.IfStatement;
import com.inlingo.components.expression.Literal;
import com.inlingo.components.expression.ReadStatement;
import com.inlingo.components.expression.RepeatStatement;
import com.inlingo.components.expression.Statement;
import com.inlingo.components.expression.Variable;
import com.inlingo.components.expression.WhileStatement;
import com.inlingo.components.expression.WriteStatement;
import com.inlingo.core.SymbolTable;
import com.inlingo.core.Token;
import com.inlingo.core.TokenType;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;

public class PseudoParser {
    private final LexerList lexer;
    private final SymbolTable symbolTable;
    private final List<Statement> statements;
    private Token currentToken;

    public PseudoParser(LexerList lexer, SymbolTable symbolTable) {
        if (lexer == null)
            throw new IllegalArgumentException("Lexer cannot be null");
        if (symbolTable == null)
            throw new IllegalArgumentException("SymbolTable cannot be null");
        this.lexer = lexer;
        this.symbolTable = symbolTable;
        this.statements = new ArrayList<>();
        this.currentToken = null;
    }

    public void parse() throws LexicalException, ParserException {
        currentToken = lexer.next();
        // Skip 'begin program'
        if (currentToken != null && currentToken.getType() == TokenType.PROGRAMSTART) {
            currentToken = lexer.next();
        }
        // Skip 'variables' section
        if (currentToken != null && currentToken.getType() == TokenType.VARIABLES) {
            currentToken = lexer.next();
            // Skip colon first
            if (currentToken != null && currentToken.getType() == TokenType.COLON) {
                currentToken = lexer.next();
            }
            // Skip variable list (IDENTIFIER, COMMA, ...)
            while (currentToken != null && currentToken.getType() == TokenType.IDENTIFIER) {
                currentToken = lexer.next();
                if (currentToken != null && currentToken.getType() == TokenType.COMMA) {
                    currentToken = lexer.next();
                } else {
                    break;
                }
            }
        }
        // Main parse loop
        while (currentToken != null && currentToken.getType() != TokenType.PROGRAMEND
                && currentToken.getType() != TokenType.EOF) {
            statements.add(parseStatement());
        }
        // Optionally consume 'end program'
        if (currentToken != null && currentToken.getType() == TokenType.PROGRAMEND) {
            currentToken = lexer.next();
        }
    }

    private Statement parseStatement() throws LexicalException, ParserException {
        if (currentToken == null)
            throw new ParserException("Unexpected end of input");
        switch (currentToken.getType()) {
            case READ:
                return parseReadStatement();
            case WRITE:
                return parseWriteStatement();
            case IF:
                return parseIfStatement();
            case WHILE:
                return parseWhileStatement();
            case REPEAT:
                return parseRepeatStatement();
            case IDENTIFIER:
                return parseAssignment();
            case END_WHILE:
            case END_IF:
            case END_REPEAT:
                return null; // Signal end of block
            default:
                throw new ParserException("Unexpected token: " + currentToken);
        }
    }

    private Statement parseReadStatement() throws LexicalException, ParserException {
        consume(TokenType.READ);
        if (currentToken == null || currentToken.getType() != TokenType.IDENTIFIER)
            throw new ParserException("Expected identifier after 'read'");
        String variableName = currentToken.getValue().toString();
        consume(TokenType.IDENTIFIER);
        return new ReadStatement(variableName);
    }

    private Statement parseWriteStatement() throws LexicalException, ParserException {
        consume(TokenType.WRITE);
        List<Expression> expressions = new ArrayList<>();
        expressions.add(parseExpression());
        while (currentToken != null && currentToken.getType() == TokenType.COMMA) {
            consume(TokenType.COMMA);
            expressions.add(parseExpression());
        }
        return new WriteStatement(expressions);
    }

    private Statement parseIfStatement() throws LexicalException, ParserException {
        consume(TokenType.IF);
        Expression condition = parseExpression();
        consume(TokenType.THEN); // Consume the THEN token
        List<Statement> thenBranch = parseBlock();
        List<Statement> elseBranch = new ArrayList<>();
        if (currentToken != null && currentToken.getType() == TokenType.ELSE) {
            consume(TokenType.ELSE);
            elseBranch = parseBlock();
        }
        return new IfStatement(condition, thenBranch, elseBranch);
    }

    private Statement parseWhileStatement() throws LexicalException, ParserException {
        consume(TokenType.WHILE);
        Expression condition = parseExpression();
        List<Statement> body = parseBlock();
        // The END_WHILE token was already consumed by parseBlock
        return new WhileStatement(condition, body);
    }

    private Statement parseRepeatStatement() throws LexicalException, ParserException {
        consume(TokenType.REPEAT);
        consume(TokenType.LEFT_PAREN);
        String variable = currentToken.getValue().toString();
        consume(TokenType.IDENTIFIER);
        consume(TokenType.COMMA);
        Expression start = parseExpression();
        consume(TokenType.COMMA);
        Expression end = parseExpression();
        consume(TokenType.RIGHT_PAREN);

        List<Statement> body = new ArrayList<>();
        while (currentToken != null && currentToken.getType() != TokenType.END_REPEAT) {
            Statement stmt = parseStatement();
            if (stmt != null) {
                body.add(stmt);
            }
        }
        consume(TokenType.END_REPEAT);

        return new RepeatStatement(variable, start, end, body);
    }

    private Statement parseAssignment() throws LexicalException, ParserException {
        if (currentToken == null || currentToken.getType() != TokenType.IDENTIFIER) {
            throw new ParserException("Expected identifier but got " + 
                (currentToken == null ? "null" : currentToken.getType()));
        }
        String name = currentToken.getValue().toString();
        currentToken = lexer.next();  // Consume identifier
        
        if (currentToken == null || currentToken.getType() != TokenType.ASSIGNMENT) {
            throw new ParserException("Expected assignment operator '=' but got " + 
                (currentToken == null ? "null" : currentToken.getType()));
        }
        currentToken = lexer.next();  // Consume assignment operator
        
        Expression value = parseExpression();
        return new Assignment(name, value);
    }

    private Expression parseExpression() throws LexicalException, ParserException {
        Expression expr = parseTerm();
        while (currentToken != null && isRelationalOperator(currentToken.getType())) {
            String operator = currentToken.getValue().toString();
            consume(currentToken.getType());
            expr = new Binary(expr, operator, parseTerm());
        }
        return expr;
    }

    private Expression parseTerm() throws LexicalException, ParserException {
        Expression expr = parseFactor();
        while (currentToken != null && isArithmeticOperator(currentToken.getType())) {
            String operator = currentToken.getValue().toString();
            consume(currentToken.getType());
            expr = new Binary(expr, operator, parseFactor());
        }
        return expr;
    }

    private Expression parseFactor() throws LexicalException, ParserException {
        if (currentToken == null)
            throw new ParserException("Unexpected end of input");
        switch (currentToken.getType()) {
            case NUMBER: {
                Object value = currentToken.getValue();
                consume(TokenType.NUMBER);
                return new Literal(value);
            }
            case STRING: {
                Object value = currentToken.getValue();
                consume(TokenType.STRING);
                return new Literal(value);
            }
            case BOOLEAN: {
                Object value = currentToken.getValue();
                consume(TokenType.BOOLEAN);
                return new Literal(value);
            }
            case IDENTIFIER: {
                String name = currentToken.getValue().toString();
                consume(TokenType.IDENTIFIER);
                return new Variable(name);
            }
            case LEFT_PAREN: {
                consume(TokenType.LEFT_PAREN);
                Expression expr = parseExpression();
                consume(TokenType.RIGHT_PAREN);
                return expr;
            }
            default:
                throw new ParserException("Unexpected token: " + currentToken);
        }
    }

    private List<Statement> parseBlock() throws LexicalException, ParserException {
        List<Statement> statements = new ArrayList<>();
        while (currentToken != null && currentToken.getType() != TokenType.ELSE
                && currentToken.getType() != TokenType.EOF) {
            Statement stmt = parseStatement();
            if (stmt == null) {
                // Advance past the end token
                if (currentToken != null && 
                    (currentToken.getType() == TokenType.END_WHILE || 
                     currentToken.getType() == TokenType.END_IF ||
                     currentToken.getType() == TokenType.END_REPEAT)) {
                    currentToken = lexer.next();
                }
                break;
            }
            statements.add(stmt);
        }
        return statements;
    }

    private void consume(TokenType type) throws LexicalException, ParserException {
        if (currentToken != null && currentToken.getType() == type) {
            currentToken = lexer.next();
        } else {
            throw new ParserException(
                    "Expected " + type + " but got " + (currentToken == null ? "null" : currentToken.getType()));
        }
    }

    private boolean isRelationalOperator(TokenType type) {
        return type == TokenType.RELATIONALOP;
    }

    private boolean isArithmeticOperator(TokenType type) {
        return type == TokenType.ARITHMETIC_OP;
    }

    public List<Statement> getStatements() {
        return statements;
    }
}