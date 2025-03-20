package com.inlingo.contracts;

import com.inlingo.core.Token;

public abstract class LexerContract {
    public final ScannerContract scanner;

    public LexerContract(ScannerContract scanner) {
        this.scanner = scanner;
    }

    public abstract Token next();

    public abstract Token current();
}
