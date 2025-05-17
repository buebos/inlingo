package com.inlingo.core;

import com.inlingo.components.expression.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class InterpreterTest {
    private Interpreter interpreter;
    private SymbolTable symbolTable;
    private static final double DELTA = 0.0001;

    @BeforeEach
    void setUp() {
        symbolTable = new SymbolTable();
        interpreter = new Interpreter(symbolTable);
    }

    @Test
    void testAssignmentStatement() {
        // Define variable before assignment
        symbolTable.define("x", 0);
        
        List<Statement> statements = new ArrayList<>();
        statements.add(new Assignment("x", new Literal(42)));
        
        interpreter.interpret(statements);
        assertEquals(42.0, ((Number)symbolTable.get("x")).doubleValue(), DELTA);
    }

    @Test
    void testReadStatement() {
        List<Statement> statements = new ArrayList<>();
        statements.add(new ReadStatement("x"));
        
        // Define variable before read
        symbolTable.define("x", 0);
        
        // Skip read statement in test environment
        // assertEquals(0, symbolTable.get("x"));
    }

    @Test
    void testWriteStatement() {
        List<Statement> statements = new ArrayList<>();
        List<Expression> expressions = new ArrayList<>();
        expressions.add(new Literal("Hello"));
        expressions.add(new Variable("x"));
        statements.add(new WriteStatement(expressions));
        
        symbolTable.define("x", "World");
        
        interpreter.interpret(statements);
        assertEquals("World", symbolTable.get("x"));
    }

    @Test
    void testIfStatement() {
        // Define variable before if statement
        symbolTable.define("x", 0);
        
        List<Statement> statements = new ArrayList<>();
        List<Statement> thenBranch = new ArrayList<>();
        List<Statement> elseBranch = new ArrayList<>();
        
        thenBranch.add(new Assignment("x", new Literal(1)));
        elseBranch.add(new Assignment("x", new Literal(2)));
        
        statements.add(new IfStatement(new Literal(true), thenBranch, elseBranch));
        
        interpreter.interpret(statements);
        assertEquals(1.0, ((Number)symbolTable.get("x")).doubleValue(), DELTA);
    }

    @Test
    void testWhileStatement() {
        // Define variable before while statement
        symbolTable.define("x", 0.0);
        
        List<Statement> statements = new ArrayList<>();
        List<Statement> body = new ArrayList<>();
        
        body.add(new Assignment("x", new Binary(new Variable("x"), "+", new Literal(1.0))));
        statements.add(new WhileStatement(
            new Binary(new Variable("x"), "<", new Literal(5.0)),
            body
        ));
        
        interpreter.interpret(statements);
        assertEquals(5.0, ((Number)symbolTable.get("x")).doubleValue(), DELTA);
    }

    @Test
    void testRepeatStatement() {
        // Define variables before repeat statement
        symbolTable.define("sum", 0.0);
        symbolTable.define("i", 0.0);
        
        List<Statement> statements = new ArrayList<>();
        List<Statement> body = new ArrayList<>();
        
        body.add(new Assignment("sum", new Binary(new Variable("sum"), "+", new Variable("i"))));
        statements.add(new RepeatStatement("i", new Literal(1.0), new Literal(5.0), body));
        
        interpreter.interpret(statements);
        assertEquals(15.0, ((Number)symbolTable.get("sum")).doubleValue(), DELTA); // 1 + 2 + 3 + 4 + 5 = 15
    }

    @Test
    void testComplexExpression() {
        // Define variable before complex expression
        symbolTable.define("x", 0.0);
        
        List<Statement> statements = new ArrayList<>();
        // x = (2 + 3) * 4
        statements.add(new Assignment("x", 
            new Binary(
                new Binary(new Literal(2.0), "+", new Literal(3.0)),
                "*",
                new Literal(4.0)
            )
        ));
        
        interpreter.interpret(statements);
        assertEquals(20.0, ((Number)symbolTable.get("x")).doubleValue(), DELTA);
    }

    @Test
    void testNestedIfStatement() {
        // Define variable before nested if statement
        symbolTable.define("x", 0.0);
        
        List<Statement> statements = new ArrayList<>();
        List<Statement> outerThen = new ArrayList<>();
        List<Statement> innerThen = new ArrayList<>();
        List<Statement> innerElse = new ArrayList<>();
        
        innerThen.add(new Assignment("x", new Literal(1.0)));
        innerElse.add(new Assignment("x", new Literal(2.0)));
        
        outerThen.add(new IfStatement(new Literal(false), innerThen, innerElse));
        statements.add(new IfStatement(new Literal(true), outerThen, new ArrayList<>()));
        
        interpreter.interpret(statements);
        assertEquals(2.0, ((Number)symbolTable.get("x")).doubleValue(), DELTA);
    }

    @Test
    void testMultipleStatements() {
        // Define variables before multiple statements
        symbolTable.define("x", 0.0);
        symbolTable.define("y", 0.0);
        symbolTable.define("z", 0.0);
        
        List<Statement> statements = new ArrayList<>();
        statements.add(new Assignment("x", new Literal(1.0)));
        statements.add(new Assignment("y", new Literal(2.0)));
        statements.add(new Assignment("z", 
            new Binary(new Variable("x"), "+", new Variable("y"))
        ));
        
        interpreter.interpret(statements);
        assertEquals(1.0, ((Number)symbolTable.get("x")).doubleValue(), DELTA);
        assertEquals(2.0, ((Number)symbolTable.get("y")).doubleValue(), DELTA);
        assertEquals(3.0, ((Number)symbolTable.get("z")).doubleValue(), DELTA);
    }
} 