package com.inlingo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.inlingo.components.LexerList;
import com.inlingo.components.ParserLL1;
import com.inlingo.components.ScannerString;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;

public class ParserLL1ListTest {

    @Test
    void testEmptyList() throws LexicalException {
        String input = "[]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        assertDoesNotThrow(() -> parser.parse(), "Empty list should parse successfully");
    }

    @Test
    void testSingleElementList() throws LexicalException {
        String input = "[a]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        assertDoesNotThrow(() -> parser.parse(), "Single element list should parse successfully");
    }

    @Test
    void testMultipleElementsList() throws LexicalException {
        String input = "[a, b, c, d]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        assertDoesNotThrow(() -> parser.parse(), "Multiple elements list should parse successfully");
    }

    @Test
    void testNestedList() throws LexicalException {
        String input = "[a, [b, c], d]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        assertDoesNotThrow(() -> parser.parse(), "Nested list should parse successfully");
    }

    @Test
    void testDeeplyNestedList() throws LexicalException {
        String input = "[a, [b, [c, d], e], f]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        assertDoesNotThrow(() -> parser.parse(), "Deeply nested list should parse successfully");
    }

    @Test
    void testEmptyNestedList() throws LexicalException {
        String input = "[a, [], b]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        assertDoesNotThrow(() -> parser.parse(), "Empty nested list should parse successfully");
    }

    @Test
    void testTrailingCommaError() throws LexicalException {
        String input = "[a, b,]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        ParserException exception = assertThrows(ParserException.class,
                () -> parser.parse(),
                "List with trailing comma should throw ParserException");

        assertTrue(exception.getMessage().contains("Unexpected end of list after comma"),
                "Exception message should mention unexpected end of list after comma");
    }

    @Test
    void testMissingClosingBracket() throws LexicalException {
        String input = "[a, b";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        ParserException exception = assertThrows(ParserException.class,
                () -> parser.parse(),
                "List with missing closing bracket should throw ParserException");

        assertTrue(exception.getMessage().contains("Missing closing bracket"),
                "Exception message should mention missing closing bracket");
    }

    @Test
    void testMissingComma() throws LexicalException {
        String input = "[a b]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        assertThrows(ParserException.class,
                () -> parser.parse(),
                "List with missing comma should throw ParserException");
    }

    @Test
    void testExtraTokensAfterList() throws LexicalException {
        String input = "[] extra";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        ParserException exception = assertThrows(ParserException.class,
                () -> parser.parse(),
                "Extra tokens after list should throw ParserException");

        assertTrue(exception.getMessage().contains("Unexpected token after end of list"),
                "Exception message should mention unexpected token after end of list");
    }

    @Test
    void testUnexpectedTokenInsteadOfElement() throws LexicalException {
        String input = "[a, +, c]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        ParserException exception = assertThrows(ParserException.class,
                () -> parser.parse(),
                "Unexpected token instead of element should throw ParserException");

        assertTrue(exception.getMessage().contains("Expecting identifier or list"),
                "Exception message should mention expecting identifier or list");
    }

    @Test
    void testMissingElementAfterComma() throws LexicalException {
        String input = "[a, ]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        ParserException exception = assertThrows(ParserException.class,
                () -> parser.parse(),
                "Missing element after comma should throw ParserException");

        assertTrue(exception.getMessage().contains("Unexpected end of list after comma"),
                "Exception message should mention unexpected end of list after comma");
    }

    @Test
    void testIncompleteDeeplyNestedList() throws LexicalException {
        String input = "[a, [b, [c, d], e]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        ParserException exception = assertThrows(ParserException.class,
                () -> parser.parse(),
                "Incomplete deeply nested list should throw ParserException");

        assertTrue(exception.getMessage().contains("Missing closing bracket"),
                "Exception message should mention missing closing bracket");
    }

    @Test
    void testNumbersAsElements() throws LexicalException {
        String input = "[1, 2, 3]";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        ParserException exception = assertThrows(ParserException.class,
                () -> parser.parse(),
                "List with numbers should throw ParserException as element() only accepts IDENTIFIER");

        assertTrue(exception.getMessage().contains("Expecting identifier or list"),
                "Exception message should mention expecting identifier or list");
    }

    @Test
    void testMixedErrorsInList() throws LexicalException {
        String input = "[a, [b, c,], d";
        LexerList lexer = new LexerList(new ScannerString(input));
        ParserLL1 parser = new ParserLL1(lexer);

        assertThrows(ParserException.class,
                () -> parser.parse(),
                "List with mixed errors should throw ParserException");
    }
}
