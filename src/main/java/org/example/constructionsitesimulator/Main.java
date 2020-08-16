package org.example.constructionsitesimulator;

import org.example.constructionsitesimulator.commands.QuitCommand;
import org.example.constructionsitesimulator.commands.UserCommand;
import org.example.constructionsitesimulator.costs.CostSummary;
import org.example.constructionsitesimulator.exceptions.InvalidCommandException;
import org.example.constructionsitesimulator.exceptions.OutOfMapException;
import org.example.constructionsitesimulator.exceptions.ProtectedTreePenaltyException;
import org.example.constructionsitesimulator.exceptions.TerminateException;
import org.example.constructionsitesimulator.io.ConsoleIO;
import org.example.constructionsitesimulator.io.FileReader;
import org.example.constructionsitesimulator.io.Printer;
import org.example.constructionsitesimulator.models.Bulldozer;
import org.example.constructionsitesimulator.models.ConstructionSite;
import org.example.constructionsitesimulator.models.Simulator;
import org.example.constructionsitesimulator.models.SquareBlock;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        ConsoleIO console = new ConsoleIO();
        Printer printer = new Printer(console);

        if (args.length == 0) {
            console.writeLine("Please specify the input file name.");
            System.exit(1);
        }

        String filename = args[0];
        List<List<SquareBlock>> constructionMap = new ArrayList<>(new ArrayList<>());

        try {
            List<String> fileContent = new FileReader().readFile(filename);
            constructionMap = SimulatorHelper.readMap(fileContent);
        } catch (FileNotFoundException e) {
            console.writeLine("File does not exist: " + filename);
            System.exit(2);
        } catch (IllegalArgumentException e) {
            console.writeLine(String.format("Oops, something wrong found in construction map: %s", e.getMessage()));
            System.exit(3);
        }

        ConstructionSite site = new ConstructionSite(constructionMap, new Bulldozer());
        Simulator simulator = new Simulator(site, new ArrayList<UserCommand>());
        printer.printSiteMap(site);

        try {
            while (true) {
                console.write("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
                UserCommand command = SimulatorHelper.parseCommand(console.readLine());
                simulator = simulator.process(command);
                if (command instanceof QuitCommand) {
                    break;
                }
            }
        } catch (InvalidCommandException e) {
            console.writeLine("Invalid input, try again.");
        } catch (TerminateException e) {
            simulator = e.getSimulator();
            if (e.getReason() instanceof OutOfMapException) {
                console.writeLine("Oops, you run out of the site.");
            } else if (e.getReason() instanceof ProtectedTreePenaltyException) {
                console.writeLine("Oops, you are not allowed to clear a protected tree.");
            } else {
                console.writeLine("Oops, something gone wrong.");
            }
            endSimulation(printer, simulator);
            System.exit(4);
        }

        endSimulation(printer, simulator);
        System.exit(0);
    }

    private static void endSimulation(Printer printer, Simulator simulator) {
        printer.printCommands(simulator.getCommands());
        CostSummary costSummary = new CostSummary(simulator);
        printer.printCostSummary(costSummary);
        printer.printThankyou();
    }

}
