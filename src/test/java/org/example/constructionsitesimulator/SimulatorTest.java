package org.example.constructionsitesimulator;

import org.example.constructionsitesimulator.commands.AdvanceCommand;
import org.example.constructionsitesimulator.commands.LeftCommand;
import org.example.constructionsitesimulator.commands.QuitCommand;
import org.example.constructionsitesimulator.commands.UserCommand;
import org.example.constructionsitesimulator.exceptions.OutOfMapException;
import org.example.constructionsitesimulator.exceptions.ProtectedTreePenaltyException;
import org.example.constructionsitesimulator.exceptions.TerminateException;
import org.example.constructionsitesimulator.models.Bulldozer;
import org.example.constructionsitesimulator.models.ConstructionSite;
import org.example.constructionsitesimulator.models.Simulator;
import org.example.constructionsitesimulator.models.SquareBlock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SimulatorTest {
    List<List<SquareBlock>> constructionMap = Arrays.asList(Arrays.asList(SquareBlock.PLAIN_LAND, SquareBlock.ROCKY_GROUND, SquareBlock.PROTECTED_TREE, SquareBlock.TREE));
    ConstructionSite site = new ConstructionSite(constructionMap, new Bulldozer());
    List<UserCommand> commands = new ArrayList<>();
    Simulator simulator = new Simulator(site, commands);

    @Test
    public void shouldAccumulateCommands() throws TerminateException {
        Simulator newSimulator = simulator.process(new AdvanceCommand(1)).process(new LeftCommand()).process(new QuitCommand());

        assertThat(newSimulator.getCommands().size()).isEqualTo(3);
    }

    @Test
    public void shouldThrowTerminateExceptionWithReasonExceptionIfGoingOutOfMap() {
        try {
            simulator.process(new LeftCommand());
        } catch (TerminateException e) {
            assertThat(e.getSimulator().getCommands().size()).isEqualTo(1);
            assertThat(e.getReason() instanceof OutOfMapException).isTrue();
        }
    }

    @Test
    public void shouldThrowTerminateExceptionWithReasonExceptionIfTryToClearProtectedTree() {
        try {
            simulator.process(new AdvanceCommand(3));
        } catch (TerminateException e) {
            assertThat(e.getSimulator().getCommands().size()).isEqualTo(1);
            assertThat(e.getReason() instanceof ProtectedTreePenaltyException).isTrue();
        }
    }
}
