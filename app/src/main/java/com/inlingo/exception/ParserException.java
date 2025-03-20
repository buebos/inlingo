package com.inlingo.exception;

public class ParserException extends Exception {
    public ParserException(String token) {
        super("Invalid token encountered: '" + token + "'");
    }
}
