package com.inlingo;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.inlingo.components.LexerList;
import com.inlingo.components.ParserLLK;
import com.inlingo.components.ScannerString;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;

public class ParserLLKListTest {
    @Test
    void testEmptyInput() {
        parseAndAssert("", true, "Empty input should parse successfully");
    }

    @Test
    void testEmptyList() {
        parseAndAssert("[]", true, "Empty list should parse successfully");
    }

    @Test
    void testSingleElementList() {
        parseAndAssert("[a]", true, "Single element list should parse successfully");
    }

    @Test
    void testMultipleElementsList() {
        parseAndAssert("[a, b, c, d]", true, "Multiple elements list should parse successfully");
    }

    @Test
    void testNestedList() {
        parseAndAssert("[a, [b, c], d]", true, "Nested list should parse successfully");
    }

    @Test
    void testDeeplyNestedList() {
        parseAndAssert("[a, [b, [c, d], e], f]", true, "Deeply nested list should parse successfully");
    }

    @Test
    void testEmptyNestedList() {
        parseAndAssert("[a, [], b]", true, "Empty nested list should parse successfully");
    }

    @Test
    void testTrailingCommaError() {
        parseAndAssert("[a, b,]", false, "List with trailing comma should throw ParserException");
    }

    @Test
    void testMissingClosingBracket() {
        parseAndAssert("[a, b", false, "List with missing closing bracket should throw ParserException");
    }

    @Test
    void testMissingComma() {
        parseAndAssert("[a b]", false, "List with missing comma should throw ParserException");
    }

    @Test
    void testExtraTokensAfterList() {
        parseAndAssert("[] extra", false, "Extra tokens after list should throw ParserException");
    }

    @Test
    void testAssignmentInList() {
        parseAndAssert("[a=b, c, d]", true, "List with assignment should parse successfully");
    }

    @Test
    void testNestedAssignmentInList() {
        parseAndAssert("[a, [b, c=d], e]", true, "List with nested assignment should parse successfully");
    }

    private void parseAndAssert(String input, boolean shouldSucceed, String message) {
        try {
            LexerList lexer = new LexerList(new ScannerString(input));
            ParserLLK parser = new ParserLLK(lexer, 2);

            if (shouldSucceed) {
                assertDoesNotThrow(() -> parser.parse(), message);
            } else {
                assertThrows(ParserException.class,
                        () -> parser.parse(),
                        message);
            }
        } catch (LexicalException e) {
            throw new AssertionError("Failed to create lexer: " + e.getMessage());
        }
    }
}
