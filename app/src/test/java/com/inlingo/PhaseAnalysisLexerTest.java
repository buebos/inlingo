package com.inlingo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.inlingo.components.Lexer;
import com.inlingo.core.Token;
import com.inlingo.exception.LexicalException;

public class PhaseAnalysisLexerTest {
    @Test
    void testValidTokenization() throws LexicalException {
        String sourceCode = """
                begin program
                variables: count, sum, i, value
                read count
                sum = 0
                i = 0
                end program""";

        Lexer lexer = new Lexer();
        lexer.analyze(sourceCode);
        List<Token> tokens = lexer.getTokens();

        assertNotNull(tokens, "Token list should not be null");
        assertFalse(tokens.isEmpty(), "Token list should not be empty");
        assertEquals(19, tokens.size(), "Expected number of tokens");
    }

    @Test
    void testTokenTypes() throws LexicalException {
        String sourceCode = "if sum > 100 then write \"Result: \" sum end if";
        Lexer lexer = new Lexer();
        lexer.analyze(sourceCode);
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
        Lexer lexer = new Lexer();

        Exception exception = assertThrows(LexicalException.class, () -> lexer.analyze(invalidSourceCode));
        assertTrue(exception.getMessage().contains("Invalid token"), "Exception should mention invalid token");
    }

    @Test
    void testEmptyInput() throws LexicalException {
        String emptySourceCode = "";
        Lexer lexer = new Lexer();
        lexer.analyze(emptySourceCode);
        List<Token> tokens = lexer.getTokens();

        assertTrue(tokens.isEmpty(), "Token list should be empty for empty input");
    }
}
