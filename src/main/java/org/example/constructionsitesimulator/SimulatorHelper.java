package org.example.constructionsitesimulator;

import org.example.constructionsitesimulator.commands.*;
import org.example.constructionsitesimulator.exceptions.InvalidCommandException;
import org.example.constructionsitesimulator.models.SquareBlock;

import java.util.List;
import java.util.stream.Collectors;

public class SimulatorHelper {
    public static List<List<SquareBlock>> readMap(List<String> lines) throws IllegalArgumentException{
        List<List<SquareBlock>> constructionLands = lines.stream().map(line ->
                line.replaceAll(" ", "").chars().mapToObj(c ->
                        SquareBlock.ofMarker(Character.toString((char)c))).collect(Collectors.toList())
        ).collect(Collectors.toList());
        return constructionLands;
    }

    public static UserCommand parseCommand(String userInput) throws InvalidCommandException {
        String[] inputs = userInput.split(" ");
        switch (inputs[0].toLowerCase()) {
            case "l":
            case "left":
                return new LeftCommand();
            case "r":
            case "right":
                return new RightCommand();
            case "q":
            case "quit":
                return new QuitCommand();
            case "a":
            case "advance":
                if (inputs.length > 1) {
                    int num = Integer.parseInt(inputs[1]);
                    if (num > 0){
                        return new AdvanceCommand(num);
                    }
                }
            default:
                throw new InvalidCommandException();
        }
    }
}
