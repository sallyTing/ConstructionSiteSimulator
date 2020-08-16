package org.example.constructionsitesimulator.io;

import java.util.Scanner;

public class ConsoleIO implements IO {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void writeLine(String s) {
        System.out.println(s);
    }

    @Override
    public void emptyLine() {
        System.out.println();
    }

    @Override
    public void write(String s) {
        System.out.print(s);
    }
}
