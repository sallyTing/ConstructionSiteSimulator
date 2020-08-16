package org.example.constructionsitesimulator;

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
    public void shouldAccumulateCommandsAndCalculateCommandsCostUnitCorrect() throws TerminateException {
        Simulator newSimulator = simulator.process(new AdvanceCommand(1)).process(new LeftCommand()).process(new QuitCommand());

        assertThat(newSimulator.getCommands().size()).isEqualTo(3);
        assertThat(simulator.getCommandsCostUnit()).isEqualTo(2);

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
