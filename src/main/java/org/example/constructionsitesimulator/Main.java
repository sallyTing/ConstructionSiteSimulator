package org.example.constructionsitesimulator;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify the input file name.");
            System.exit(1);
        }

        String filename = args[0];
        List<String> fileContent = new ArrayList<>();
        try {
            fileContent = new FileReader().readFile(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist: " + filename);
            System.exit(2);
        }

        List<List<SquareBlock>> constructionMap = Arrays.asList(Arrays.asList());
        try {
            constructionMap = SimulatorHelper.readMap(fileContent);
        } catch (IllegalArgumentException e) {
            System.out.println("Oops, some invalid charactor found in construction map.");
            System.exit(3);
        }

        ConstructionSite site = new ConstructionSite(constructionMap, new Bulldozer());
        Simulator simulator = new Simulator(site, new ArrayList<UserCommand>());

        simulator.printConstructionSite();
        System.out.println("The bulldozer is currently located at the Northern edge of the site, " +
                "immediately to the West of the site, and facing East.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");

        while (scanner.hasNext()) {
            try {
                UserCommand command = SimulatorHelper.parseCommand(scanner.nextLine());

                simulator = simulator.process(command);
                if (command instanceof QuitCommand) {
                    break;
                }
            } catch (TerminateException e) {
                simulator = e.getSimulator();
                if (e.getReason() instanceof OutOfMapException) {
                    System.out.println("Oops, you run out of the site.");
                } else if (e.getReason() instanceof ProtectedTreePenaltyException) {
                    System.out.println("Oops, you are not allowed to clear a protected tree.");
                } else {
                    System.out.println("Oops, something gone wrong.");
                }
                endSimulation(simulator);
                System.exit(4);
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
            }
            System.out.print("(l)eft, (r)ight, (a)dvance <n>, (q)uit: ");
        }
        endSimulation(simulator);
        System.exit(0);
    }

    private static void endSimulation(Simulator simulator) {
        simulator.printCommands();
        simulator.printCost();

        System.out.println("Thankyou for using the Aconex site clearing simulator.");
    }

}
