package com.inlingo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.inlingo.components.LexerList;
import com.inlingo.components.ScannerString;
import com.inlingo.core.Token;
import com.inlingo.exception.LexicalException;

public class LexerListDemo {
    public static void main(String[] args) throws LexicalException {
        // Test with inline sample code if no file is provided
        if (args.length == 0) {
            testWithSampleCode();
        } else {
            // Use file from command line argument
            String sourceCode = readProgramFile(args[0]);
            processPseudocode(sourceCode);
        }
    }

    private static void testWithSampleCode() throws LexicalException {
        System.out.println("*** Running test with sample pseudocode ***\n");

        // Sample pseudocode that tests all the lexer features
        String sampleCode = """
                begin program
                variables: count, sum, i, value

                read count
                sum = 0
                i = 0

                while (i < count)
                    read value
                    sum = sum + value
                    i = i + 1
                end while

                if sum > 100 then
                    write "Sum is large: ", sum
                end if

                repeat (i, 1, 5)
                    write "Count: ", i
                end repeat

                write "The average is: ", sum / count
                end program""";

        System.out.println("Sample Pseudocode:");
        System.out.println("------------------");
        System.out.println(sampleCode);
        System.out.println("------------------\n");

        processPseudocode(sampleCode);
    }

    private static void processPseudocode(String sourceCode) throws LexicalException {
        LexerList lexer = new LexerList(new ScannerString(sourceCode));

        System.out.println("*** Lexical Analysis Results ***");
        System.out.println("Total tokens found: " + lexer.getTokens().size());
        System.out.println("------------------");

        // Print tokens with line numbers
        int tokenCount = 0;
        for (Token token : lexer.getTokens()) {
            System.out.printf("%3d: %s\n", ++tokenCount, token);
        }

        // Print token statistics
        System.out.println("\n*** Token Type Statistics ***");
        printTokenStatistics(lexer.getTokens());
    }

    private static void printTokenStatistics(ArrayList<Token> tokens) {
        // Count occurrences of each token type
        java.util.Map<String, Integer> statistics = new java.util.HashMap<>();

        for (Token token : tokens) {
            String typeName = token.getType().getName();
            statistics.put(typeName, statistics.getOrDefault(typeName, 0) + 1);
        }

        // Print statistics in sorted order
        statistics.entrySet().stream()
                .sorted(java.util.Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf("%-20s: %d\n", entry.getKey(), entry.getValue()));
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
