package org.example.constructionsitesimulator.io;

import org.example.constructionsitesimulator.commands.QuitCommand;
import org.example.constructionsitesimulator.commands.UserCommand;
import org.example.constructionsitesimulator.costs.CostItem;
import org.example.constructionsitesimulator.costs.CostSummary;
import org.example.constructionsitesimulator.models.ConstructionSite;
import org.example.constructionsitesimulator.models.SquareBlock;

import java.util.List;
import java.util.stream.Collectors;

public class Printer {
    private final IO io;

    public Printer(final IO io) {
        this.io = io;
    }

    public void printSiteMap(final ConstructionSite site) {
        io.writeLine("Welcome to the Aconex site clearing simulator. This is a map of the site:");
        io.emptyLine();
        site.generateMap().stream().forEach(row -> io.writeLine(row));
        io.emptyLine();
        io.writeLine("The bulldozer is currently located at the Northern edge of the site, immediately to the West of the site, and facing East.");
        io.emptyLine();
    }

    public void printCommands(final List<UserCommand> commands) {
        if (commands.get(commands.size() - 1) instanceof QuitCommand) {
            io.writeLine("The simulation has ended at your request. These are the commands you issued:");
        } else {
            io.writeLine("The simulation has ended abruptly. These are the commands you issued:");
        }
        io.writeLine(commands.stream().map(Object::toString).collect(Collectors.joining(", ")));
        io.emptyLine();
    }

    public void printCostSummary(final CostSummary summary) {
        io.writeLine("The costs for this land clearing operation were:");
        io.emptyLine();
        io.writeLine(String.format("%s %35s %7s", "Item", "Quantity", "Cost"));

        CostItem communicationOverhead = summary.getCommunicationOverhead();
        CostItem fuelUsage = summary.getFuelUsage();
        CostItem unclearedSquaresCost = summary.getUnclearedSquaresCost();
        CostItem protectedTreePenalty = summary.getProtectedTreePenalty();
        CostItem damageRepair = summary.getDamageRepair();

        io.writeLine("Item                               Quantity      Cost");
        io.writeLine(String.format("communication overhead                  %d       %d", communicationOverhead.getQuantity(), communicationOverhead.getCost()));
        io.writeLine(String.format("fuel usage                              %d       %d", fuelUsage.getQuantity(), fuelUsage.getCost()));
        io.writeLine(String.format("uncleared squares                       %d       %d", unclearedSquaresCost.getQuantity(), unclearedSquaresCost.getCost()));
        io.writeLine(String.format("destruction of protected tree           %d       %d", protectedTreePenalty.getQuantity(), protectedTreePenalty.getCost()));
        io.writeLine(String.format("paint damage to bulldozer               %d       %d", damageRepair.getQuantity(), damageRepair.getCost()));
        io.writeLine("----");
        io.writeLine(String.format("Total                                            %d", summary.getTotalCost()));
        io.emptyLine();
    }

    public void printThankyou() {
        io.writeLine("Thankyou for using the Aconex site clearing simulator.");
    }

}
