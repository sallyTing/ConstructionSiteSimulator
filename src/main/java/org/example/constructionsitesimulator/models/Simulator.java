package org.example.constructionsitesimulator.models;

import org.example.constructionsitesimulator.commands.*;
import org.example.constructionsitesimulator.exceptions.OutOfMapException;
import org.example.constructionsitesimulator.exceptions.ProtectedTreePenaltyException;
import org.example.constructionsitesimulator.exceptions.TerminateException;

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

    public ConstructionSite getConstructionSite() {
        return constructionSite;
    }

    public Simulator process(UserCommand command) throws TerminateException {
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

}

