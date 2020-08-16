package org.example.constructionsitesimulator;

import java.util.List;

public class Simulator {
    private final ConstructionSite constructionSite;
    private final List<UserCommand> commands;

    public Simulator(final ConstructionSite constructionSite, final List<UserCommand> comments) {
        this.constructionSite = constructionSite;
        this.commands = comments;
    }

    public List<UserCommand> getCommands() {
        return commands;
    }

    protected Simulator process(UserCommand command) throws TerminateException {
        List<UserCommand> commands = this.commands;
        commands.add(command);

        if (command instanceof LeftCommand) {
            return new Simulator(constructionSite.moveLeft(), commands);
        } else if (command instanceof RightCommand) {
            return new Simulator(constructionSite.moveRight(), commands);
        } else if (command instanceof AdvanceCommand) {
            try {
                ConstructionSite newSite = constructionSite.moveForward(((AdvanceCommand) command).getNum());
                return new Simulator(newSite, commands);
            } catch (OutOfMapException e) {
                throw new TerminateException(new Simulator(e.getSite(), commands), e);
            } catch (ProtectedTreePenaltyException e){
                throw new TerminateException(new Simulator(e.getSite(), commands), e);
            }
        } else {
            return new Simulator(constructionSite, commands);
        }
    }

    protected void printConstructionSite() {
        System.out.println("Welcome to the Aconex site clearing simulator. This is a map of the site:");
        constructionSite.print();
    }

    protected void printCommands() {
        System.out.println("The simulation has ended at your request. These are the commands you issued:");
        for(UserCommand command : commands) {
            System.out.print(command + ", ");
        }
        System.out.println("");
    }

    protected int getCommandsCostUnit() {
        return commands.stream().filter(c -> !(c instanceof QuitCommand)).toArray().length;
    }

    protected void printCost() {
        int commandsNum = getCommandsCostUnit();
        int fuelUnit = constructionSite.getBulldozer().getFuelUnit();
        int unclearedSquares = constructionSite.getUnclearedSquares();
        int damageNum = constructionSite.getBulldozer().getDamageNum();
        int penalty = constructionSite.getBulldozer().getPenaltyFlag() ? 1 : 0;
        System.out.println("The costs for this land clearing operation were:");
        System.out.println("Item                               Quantity      Cost");
        System.out.println(String.format("communication overhead                  %d       %d", commandsNum, commandsNum));
        System.out.println(String.format("fuel usage                              %d       %d", fuelUnit, fuelUnit));
        System.out.println(String.format("uncleared squares                       %d       %d", unclearedSquares, unclearedSquares * 3));
        System.out.println(String.format("destruction of protected tree           %d       %d", penalty, penalty * 10));
        System.out.println(String.format("paint damage to bulldozer               %d       %d", damageNum, damageNum * 2));
        System.out.println("----");
        int totalCost = commandsNum + fuelUnit + (unclearedSquares * 3) + (penalty * 10) + (damageNum * 2);
        System.out.println(String.format("Total                                            %d", totalCost));

    }


}

