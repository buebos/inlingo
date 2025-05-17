package com.inlingo.components;

import org.junit.jupiter.api.Test;

import com.inlingo.components.LexerList;
import com.inlingo.components.PseudoParser;
import com.inlingo.core.SymbolTable;
import com.inlingo.exception.LexicalException;
import com.inlingo.exception.ParserException;

public class PseudoParserTest {
    @Test
    public void test() throws LexicalException, ParserException {
        String input = """
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

                  if (sum > 100) then
                      write "Sum is large: ", sum
                  end if

                  repeat (i, 1, 5)
                      write "Count: ", i
                  end repeat

                  write "The average is: ", sum / count
                end program
                """;
        LexerList lexer = new LexerList(input);
        SymbolTable symbolTable = new SymbolTable();
        PseudoParser parser = new PseudoParser(lexer, symbolTable);

        parser.parse();
    }
}
