package org.example.constructionsitesimulator.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    public List<String> readFile(String filename) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        File myObj = new File(filename);
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            result.add(data);
        }
        myReader.close();
        return result;
    }
}
