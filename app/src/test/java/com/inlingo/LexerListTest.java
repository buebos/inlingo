package com.inlingo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.inlingo.components.LexerList;
import com.inlingo.components.ScannerString;
import com.inlingo.core.Token;
import com.inlingo.core.TokenType;
import com.inlingo.exception.LexicalException;

public class LexerListTest {
    @Test
    void testValidTokenization() throws LexicalException {
        LexerList lexer = new LexerList(new ScannerString("""
                begin program
                variables: count, sum, i, value
                read count
                sum = 0
                i = 0
                end program"""));
        List<Token> tokens = lexer.getTokens();

        assertNotNull(tokens, "Token list should not be null");
        assertFalse(tokens.isEmpty(), "Token list should not be empty");
        assertEquals(19, tokens.size(), "Expected number of tokens");
    }

    @Test
    void testTokenTypes() throws LexicalException {
        String sourceCode = "if sum > 100 then write \"Result: \" sum end if";
        LexerList lexer = new LexerList(new ScannerString(sourceCode));
        List<Token> tokens = lexer.getTokens();

        assertEquals("IF", tokens.get(0).getType().getName(), "Expected IF token");
        assertEquals("IDENTIFIER", tokens.get(1).getType().getName(), "Expected IDENTIFIER token for 'sum'");
        assertEquals("RELATIONALOP", tokens.get(2).getType().getName(), "Expected RELATIONALOP token");
        assertEquals("NUMBER", tokens.get(3).getType().getName(), "Expected NUMBER token for 100");
        assertEquals("THEN", tokens.get(4).getType().getName(), "Expected THEN token");
    }

    @Test
    void testLexicalException() {
        String invalidSourceCode = "@invalid_token";

        Exception exception = assertThrows(LexicalException.class,
                () -> new LexerList(new ScannerString(invalidSourceCode)));
        assertTrue(exception.getMessage().contains("Invalid token"), "Exception should mention invalid token");
    }

    @Test
    void testEmptyInput() throws LexicalException {
        String emptySourceCode = "";
        LexerList lexer = new LexerList(new ScannerString(emptySourceCode));
        List<Token> tokens = lexer.getTokens();

        assertTrue(tokens.isEmpty(), "Token list should be empty for empty input");
    }

    @Test
    void testNextReturnsTokensInOrder() throws LexicalException {
        // Create a simple program
        String sourceCode = "begin program variables: x, y end program";
        LexerList lexer = new LexerList(new ScannerString(sourceCode));

        // First token should be "begin program"
        Token token1 = lexer.next();
        assertNotNull(token1, "First token should not be null");
        assertEquals("PROGRAMSTART", token1.getType().getName(), "First token should be PROGRAMSTART");
        assertEquals("begin program", token1.getValue(), "First token value should be 'begin program'");

        // Second token should be "variables"
        Token token2 = lexer.next();
        assertNotNull(token2, "Second token should not be null");
        assertEquals("VARIABLES", token2.getType().getName(), "Second token should be VARIABLES");

        // Third token should be ":"
        Token token3 = lexer.next();
        assertNotNull(token3, "Third token should not be null");
        assertEquals("COLON", token3.getType().getName(), "Third token should be COLON");

        // Fourth token should be "x"
        Token token4 = lexer.next();
        assertNotNull(token4, "Fourth token should not be null");
        assertEquals("IDENTIFIER", token4.getType().getName(), "Fourth token should be IDENTIFIER");
        assertEquals("x", token4.getValue(), "Fourth token value should be 'x'");
    }

    @Test
    void testNextCursorIncrement() throws LexicalException {
        // Test that the cursor increments properly
        String sourceCode = "a = 42";
        LexerList lexer = new LexerList(new ScannerString(sourceCode));

        // Call next() multiple times and verify different tokens are returned
        Token token1 = lexer.next();
        Token token2 = lexer.next();
        Token token3 = lexer.next();

        assertNotEquals(token1, token2, "Different calls to next() should return different tokens");
        assertNotEquals(token2, token3, "Different calls to next() should return different tokens");

        // Check the actual values
        assertEquals("a", token1.getValue(), "First token should be 'a'");
        assertEquals("=", token2.getValue(), "Second token should be '='");
        assertEquals("42", token3.getValue(), "Third token should be '42'");
    }

    @Test
    void testNextReturnsNullWhenExhausted() throws LexicalException {
        // Test that null is returned when all tokens have been consumed
        String sourceCode = "x = 10";
        LexerList lexer = new LexerList(new ScannerString(sourceCode));

        // Consume all tokens
        lexer.next(); // x
        lexer.next(); // =
        lexer.next(); // 10

        // Next call should return null
        Token token = lexer.next();
        assertNull(token, "next() should return null when all tokens have been consumed");
    }

    @Test
    void testNextWithEmptySource() throws LexicalException {
        // Test next() with empty source code
        String emptySourceCode = "";
        LexerList lexer = new LexerList(new ScannerString(emptySourceCode));

        Token token = lexer.next();
        assertNull(token, "next() should return null for empty source code");
    }

    @Test
    void testIteration() throws LexicalException {
        String sourceCode = "begin program\nx = 10\nend program";
        LexerList lexer = new LexerList(new ScannerString(sourceCode));

        int tokenCount = 0;

        while ((lexer.next()) != null) {
            tokenCount++;
        }

        assertEquals(lexer.getTokens().size(), tokenCount,
                "Number of tokens from next() should match getTokens().size() - 1");
    }

    @Test
    void testList() throws LexicalException {
        LexerList lexer = new LexerList(new ScannerString("[1,2,3,4 , 3, 1, -1]"));

        assertEquals(TokenType.LEFT_SQBRACKET.getName(), lexer.next().getType().getName());
        assertEquals("[", lexer.current().getValue());

        assertEquals(TokenType.NUMBER.getName(), lexer.next().getType().getName());
        assertEquals("1", lexer.current().getValue());

        assertEquals(TokenType.COMMA.getName(), lexer.next().getType().getName());
        assertEquals(",", lexer.current().getValue());

        assertEquals(TokenType.NUMBER.getName(), lexer.next().getType().getName());
        assertEquals("2", lexer.current().getValue());

        assertEquals(TokenType.COMMA.getName(), lexer.next().getType().getName());
        assertEquals(",", lexer.current().getValue());

        assertEquals(TokenType.NUMBER.getName(), lexer.next().getType().getName());
        assertEquals("3", lexer.current().getValue());

        assertEquals(TokenType.COMMA.getName(), lexer.next().getType().getName());
        assertEquals(",", lexer.current().getValue());

        assertEquals(TokenType.NUMBER.getName(), lexer.next().getType().getName());
        assertEquals("4", lexer.current().getValue());

        assertEquals(TokenType.COMMA.getName(), lexer.next().getType().getName());
        assertEquals(",", lexer.current().getValue());

        assertEquals(TokenType.NUMBER.getName(), lexer.next().getType().getName());
        assertEquals("3", lexer.current().getValue());

        assertEquals(TokenType.COMMA.getName(), lexer.next().getType().getName());
        assertEquals(",", lexer.current().getValue());

        assertEquals(TokenType.NUMBER.getName(), lexer.next().getType().getName());
        assertEquals("1", lexer.current().getValue());

        assertEquals(TokenType.COMMA.getName(), lexer.next().getType().getName());
        assertEquals(",", lexer.current().getValue());

        assertEquals(TokenType.NUMBER.getName(), lexer.next().getType().getName());
        assertEquals("-1", lexer.current().getValue());

        assertEquals(TokenType.RIGHT_SQBRACKET.getName(), lexer.next().getType().getName());
        assertEquals("]", lexer.current().getValue());

        /** There should not be more tokens present */
        assertNull(lexer.next());
        assertNull(lexer.next());
        assertNull(lexer.next());
    }
}
