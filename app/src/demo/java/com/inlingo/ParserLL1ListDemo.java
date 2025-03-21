package com.inlingo;

import java.io.FileReader;
import java.io.IOException;

import com.inlingo.components.LexerList;
import com.inlingo.components.ParserLL1;
import com.inlingo.components.ScannerString;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;

public class ParserLL1ListDemo {
    public static void main(String[] args) {
        if (args.length == 0) {
            testWithSampleCode();
        } else {
            String sourceCode = readProgramFile(args[0]);
            parseListCode(sourceCode);
        }
    }

    private static void testWithSampleCode() {
        System.out.println("*** Running test with sample list code ***\n");

        // Sample list code that tests the parser features
        String[] sampleCodes = {
                // Simple flat list
                "[a, b, c, d]",

                // Nested lists
                "[a, [b, c], d]",

                // Multiple nesting levels
                "[a, [b, [c, d], e], f]",

                // Empty list
                "[]",

                // List with one element
                "[x]",

                // List with nested empty list
                "[a, [], b]",

                // Invalid lists (should cause parser errors)
                "[a, b,]", // Trailing comma
                "[a, b", // Missing closing bracket
                "a, b, c]", // Missing opening bracket
                "[a,, b]", // Double comma
                "[a, [b, c, d]" // Unclosed nested list
        };

        for (int i = 0; i < sampleCodes.length; i++) {
            System.out.println("\nList Example #" + (i + 1) + ":");
            System.out.println("------------------");
            System.out.println(sampleCodes[i]);
            System.out.println("------------------");
            parseListCode(sampleCodes[i]);
            System.out.println();
        }
    }

    private static void parseListCode(String sourceCode) {
        try {
            // Create the lexer and parser
            LexerList lexer = new LexerList(new ScannerString(sourceCode));
            ParserLL1 parser = new ParserLL1(lexer);

            System.out.println("*** Parsing List ***");

            try {
                // Try to parse the list
                parser.parse();
                System.out.println("Parsing SUCCESSFUL! The input conforms to list grammar.");
            } catch (ParserException e) {
                System.out.println("Parsing FAILED: " + e.getMessage());
            }

        } catch (LexicalException e) {
            System.out.println("Lexical error: " + e.getMessage());
        }
    }

    private static String readProgramFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (FileReader reader = new FileReader(filename)) {
            int character;
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
            return content.toString();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return "";
        }
    }
}