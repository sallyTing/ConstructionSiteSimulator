package org.example.constructionsitesimulator.costs;

import org.example.constructionsitesimulator.commands.QuitCommand;
import org.example.constructionsitesimulator.commands.UserCommand;

import java.util.List;

public class CommunicationOverhead extends CostItem {
    public CommunicationOverhead(final List<UserCommand> commands) {
        super(commands.stream().filter(c -> !(c instanceof QuitCommand)).toArray().length, 1);
    }
}
