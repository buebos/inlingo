package inlingo.phase.analysis;

public class LexicalException extends Exception {
    public LexicalException(String token) {
        super("Invalid token encountered: '" + token + "'");
    }
}