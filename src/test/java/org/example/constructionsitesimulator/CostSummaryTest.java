package org.example.constructionsitesimulator;

import org.example.constructionsitesimulator.commands.*;
import org.example.constructionsitesimulator.costs.CostSummary;
import org.example.constructionsitesimulator.exceptions.TerminateException;
import org.example.constructionsitesimulator.models.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class CostSummaryTest {


    @Test
    public void shouldCalculateCostCorrectly() {
        List<List<SquareBlock>> constructionMap = Arrays.asList(Arrays.asList(SquareBlock.CLEARED_LAND, SquareBlock.CLEARED_LAND, SquareBlock.CLEARED_LAND, SquareBlock.PROTECTED_TREE, SquareBlock.TREE));
        ConstructionSite site = new ConstructionSite(constructionMap, new Bulldozer(2, 0, Direction.North, 5, 1, true));
        List<UserCommand> commands = Arrays.asList(new AdvanceCommand(3), new LeftCommand(), new QuitCommand());
        Simulator simulator = new Simulator(site, commands);
        CostSummary costSummary = new CostSummary(simulator);

        assertThat(costSummary.getCommunicationOverhead().getCost()).isEqualTo(2);
        assertThat(costSummary.getFuelUsage().getCost()).isEqualTo(5);
        assertThat(costSummary.getUnclearedSquaresCost().getCost()).isEqualTo(3);
        assertThat(costSummary.getDamageRepair().getCost()).isEqualTo(2);
        assertThat(costSummary.getProtectedTreePenalty().getCost()).isEqualTo(10);
        assertThat(costSummary.getTotalCost()).isEqualTo(22);
    }

}
