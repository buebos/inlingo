package com.inlingo;

import com.inlingo.command.RunCommand;
import com.inlingo.command.InteractiveCommand;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        switch (args[0]) {
            case "run":
                if (args.length < 2) {
                    System.err.println("Usage: inlingo run <file>");
                    System.exit(1);
                }
                new RunCommand(args[1]).execute();
                break;
            case "interactive":
                new InteractiveCommand().execute();
                break;
            default:
                printUsage();
        }
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  inlingo run <file>         Run a pseudocode file");
        System.out.println("  inlingo interactive        Start the interactive interpreter");
    }
}
