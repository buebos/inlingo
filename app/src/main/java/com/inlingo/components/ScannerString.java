package com.inlingo.components;

import java.util.Scanner;

public class ScannerString {
    private final Scanner scanner;

    public ScannerString(String input) {
        this.scanner = new Scanner(input);
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    public String next() {
        return scanner.next();
    }
}