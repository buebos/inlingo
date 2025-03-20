package inlingo.phase.analysis;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import inlingo.core.Token;
import inlingo.core.TokenType;

public class Lexer {
    private final ArrayList<TokenType> tokenTypes = new ArrayList<>();
    private final ArrayList<Token> tokens = new ArrayList<>();

    public Lexer() {
        // Basic tokens
        tokenTypes.add(new TokenType(TokenType.NUMBER, "-?\\d+(\\.\\d+)?"));
        tokenTypes.add(new TokenType(TokenType.STRING, "\"[^\"]*\""));
        tokenTypes.add(new TokenType(TokenType.ARITHMETIC_OP, "[+*/\\-]"));
        tokenTypes.add(new TokenType(TokenType.RELATIONAL_OP, "<=|>=|==|<|>|!="));
        tokenTypes.add(new TokenType(TokenType.ASSIGNMENT, "="));
        tokenTypes.add(new TokenType(TokenType.COMMA, ","));
        tokenTypes.add(new TokenType(TokenType.LEFT_PAREN, "\\("));
        tokenTypes.add(new TokenType(TokenType.RIGHT_PAREN, "\\)"));

        // Program structure tokens
        tokenTypes.add(new TokenType(TokenType.PROGRAM_START, "begin\\s+program"));
        tokenTypes.add(new TokenType(TokenType.PROGRAM_END, "end\\s+program"));

        // I/O tokens
        tokenTypes.add(new TokenType(TokenType.READ, "read"));
        tokenTypes.add(new TokenType(TokenType.WRITE, "write"));

        // Control flow tokens
        tokenTypes.add(new TokenType(TokenType.IF, "if"));
        tokenTypes.add(new TokenType(TokenType.THEN, "then"));
        tokenTypes.add(new TokenType(TokenType.ENDIF, "end\\s+if"));
        tokenTypes.add(new TokenType(TokenType.WHILE, "while"));
        tokenTypes.add(new TokenType(TokenType.ENDWHILE, "end\\s+while"));

        // Variable declaration tokens
        tokenTypes.add(new TokenType(TokenType.VARIABLES, "variables"));
        tokenTypes.add(new TokenType(TokenType.COLON, ":"));

        // Repeat statement tokens
        tokenTypes.add(new TokenType(TokenType.REPEAT, "repeat"));
        tokenTypes.add(new TokenType(TokenType.ENDREPEAT, "end\\s+repeat"));

        // Identifier and whitespace tokens
        tokenTypes.add(new TokenType(TokenType.IDENTIFIER, "[a-zA-Z_][a-zA-Z0-9_]*"));
        tokenTypes.add(new TokenType(TokenType.WHITESPACE, "\\s+"));

        // Error token (catch-all)
        tokenTypes.add(new TokenType(TokenType.ERROR, "[^\\s]+"));
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void analyze(String sourceCode) throws LexicalException {
        StringBuilder patternBuilder = new StringBuilder();

        for (TokenType tokenType : tokenTypes) {
            patternBuilder.append(String.format("|(?<%s>%s)", tokenType.getName(), tokenType.getPattern()));
        }

        String pattern = patternBuilder.substring(1);

        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(sourceCode);

        while (matcher.find()) {
            for (TokenType tokenType : tokenTypes) {
                String match = matcher.group(tokenType.getName());

                if (match != null) {
                    if (tokenType.getName().equals(TokenType.WHITESPACE)) {
                        break;
                    }

                    if (tokenType.getName().equals(TokenType.ERROR)) {
                        throw new LexicalException(match);
                    }

                    String value = match;
                    if (tokenType.getName().equals(TokenType.STRING)) {
                        value = value.substring(1, value.length() - 1);
                    }

                    tokens.add(new Token(tokenType, value));
                    break;
                }
            }
        }
    }
}
