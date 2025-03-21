package com.inlingo;

import com.inlingo.components.LexerList;
import com.inlingo.components.ParserLLK;
import com.inlingo.components.ScannerString;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;

/**
 * Demo class to showcase the ParserLLK functionality
 */
public class ParserLLKListDemo {
    public static void main(String[] args) {
        testWithSampleCode();
    }

    private static void testWithSampleCode() {
        System.out.println("Testing ParserLLK with sample code...\n");

        // Test lists
        System.out.println("=== List Examples ===");
        parseCode("[a=b, b, c, d]");
        parseCode("[a, [b, c], d]");
        parseCode("[a, [b, [c=d, d], e], f]");
        parseCode("[]");
        parseCode("[x]");
        parseCode("[a, [], b]");
        parseCode("[1, 2, 3, 4]");
        parseCode("[a, b,]"); // This should fail - trailing comma
        parseCode("[a, b"); // This should fail - missing closing bracket
    }

    private static void parseCode(String code) {
        System.out.println("------------------");
        System.out.println(code);
        System.out.println("------------------");
        System.out.println("*** Parsing ***");

        try {
            LexerList lexer = new LexerList(new ScannerString(code));
            // Using k=2 for lookahead to handle assignments
            ParserLLK parser = new ParserLLK(lexer, 2);
            parser.parse();
            System.out.println("Parsing SUCCESSFUL! The input conforms to the grammar.");
        } catch (LexicalException e) {
            System.out.println("Lexical Error: " + e.getMessage());
        } catch (ParserException e) {
            System.out.println("Parser Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println();
    }
}