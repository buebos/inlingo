package com.inlingo.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SymbolTableTest {
    private SymbolTable symbolTable;

    @BeforeEach
    void setUp() {
        symbolTable = new SymbolTable();
    }

    @Test
    void testDefineAndGet() {
        // Test defining and getting a variable
        symbolTable.define("x", 42);
        assertEquals(42, symbolTable.get("x"));
    }

    @Test
    void testSetExistingVariable() {
        // Test setting an existing variable
        symbolTable.define("x", 42);
        symbolTable.set("x", 100);
        assertEquals(100, symbolTable.get("x"));
    }

    @Test
    void testSetNonExistentVariable() {
        // Test setting a non-existent variable
        assertThrows(RuntimeException.class, () -> {
            symbolTable.set("x", 42);
        });
    }

    @Test
    void testGetNonExistentVariable() {
        // Test getting a non-existent variable
        assertThrows(RuntimeException.class, () -> {
            symbolTable.get("x");
        });
    }

    @Test
    void testHasVariable() {
        // Test checking variable existence
        assertFalse(symbolTable.has("x"));
        symbolTable.define("x", 42);
        assertTrue(symbolTable.has("x"));
    }

    @Test
    void testMultipleVariables() {
        // Test handling multiple variables
        symbolTable.define("x", 42);
        symbolTable.define("y", "hello");
        symbolTable.define("z", true);

        assertEquals(42, symbolTable.get("x"));
        assertEquals("hello", symbolTable.get("y"));
        assertEquals(true, symbolTable.get("z"));
    }

    @Test
    void testOverwriteVariable() {
        // Test overwriting a variable with define
        symbolTable.define("x", 42);
        symbolTable.define("x", 100);
        assertEquals(100, symbolTable.get("x"));
    }

    @Test
    void testNullValue() {
        // Test handling null values
        symbolTable.define("x", null);
        assertNull(symbolTable.get("x"));
    }

    @Test
    void testDifferentTypes() {
        // Test storing different types of values
        symbolTable.define("int", 42);
        symbolTable.define("double", 3.14);
        symbolTable.define("string", "hello");
        symbolTable.define("boolean", true);
        symbolTable.define("array", new int[]{1, 2, 3});

        assertEquals(42, symbolTable.get("int"));
        assertEquals(3.14, symbolTable.get("double"));
        assertEquals("hello", symbolTable.get("string"));
        assertEquals(true, symbolTable.get("boolean"));
        assertArrayEquals(new int[]{1, 2, 3}, (int[]) symbolTable.get("array"));
    }
} 