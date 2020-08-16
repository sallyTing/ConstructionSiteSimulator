package org.example.constructionsitesimulator;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SimulatorHelperTest {
    @Test
    public void shouldParseFromStringArrayToConstructionMap() {
        List<String> fileLines = Arrays.asList("c o t", "T r o");
        List<List<SquareBlock>> map = SimulatorHelper.readMap(fileLines);
        assertThat(map).isEqualTo(Arrays.asList(
                Arrays.asList(SquareBlock.CLEARED_LAND, SquareBlock.PLAIN_LAND, SquareBlock.TREE),
                Arrays.asList(SquareBlock.PROTECTED_TREE, SquareBlock.ROCKY_GROUND, SquareBlock.PLAIN_LAND)));
    }

    @Test
    public void shouldThrowExceptionIfFailToParseToConstructionMap() {
        List<String> fileLines = Arrays.asList("c o y", "T r o");
        try {
            SimulatorHelper.readMap(fileLines);
        } catch (Exception e) {
            assertThat(e instanceof IllegalArgumentException).isTrue();
        }
    }

    @Test
    public void shouldParseUserInputToCommand() throws InvalidCommandException {
        assertThat(SimulatorHelper.parseCommand("l") instanceof LeftCommand).isTrue();
        assertThat(SimulatorHelper.parseCommand("Left") instanceof LeftCommand).isTrue();
        assertThat(SimulatorHelper.parseCommand("R") instanceof RightCommand).isTrue();
        assertThat(SimulatorHelper.parseCommand("right") instanceof RightCommand).isTrue();
        assertThat(SimulatorHelper.parseCommand("a 4") instanceof AdvanceCommand).isTrue();
        assertThat(((AdvanceCommand) SimulatorHelper.parseCommand("Advance 4")).getNum()).isEqualTo(4);
    }

    @Test
    public void shouldThrowExceptionIfFailToParseUserInputToCommand() {
        try {
            SimulatorHelper.parseCommand("invalid command");
        } catch (Exception e) {
            assertThat(e instanceof InvalidCommandException).isTrue();
        }
        try{
            SimulatorHelper.parseCommand("a");
        } catch (Exception e) {
            assertThat(e instanceof InvalidCommandException).isTrue();
        }
        try{
            SimulatorHelper.parseCommand("a 0");
        } catch (Exception e) {
            assertThat(e instanceof InvalidCommandException).isTrue();
        }
    }

}
