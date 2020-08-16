package org.example.constructionsitesimulator;

import org.example.constructionsitesimulator.exceptions.OutOfMapException;
import org.example.constructionsitesimulator.exceptions.ProtectedTreePenaltyException;
import org.example.constructionsitesimulator.models.Bulldozer;
import org.example.constructionsitesimulator.models.ConstructionSite;
import org.example.constructionsitesimulator.models.Direction;
import org.example.constructionsitesimulator.models.SquareBlock;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class ConstructionSiteTest {
    List<String> fileLines = Arrays.asList("c o t o o o o o o o", "o o o o o o o T o o", "r r r o o o o T o o", "r r r r o o o o o o", "r r r r r t o o o o");
    List<List<SquareBlock>> constructionMap = SimulatorHelper.readMap(fileLines);
    Bulldozer bulldozer = new Bulldozer(5, 2, Direction.East, 0, 0, false);
    ConstructionSite site = new ConstructionSite(constructionMap, bulldozer);

    @Test
    public void shouldGetTargetSquareBasedOnXnY() {
        assertThat(site.getTargetGrid(0, 0)).isEqualTo(SquareBlock.CLEARED_LAND);
        assertThat(site.getTargetGrid(2, 0)).isEqualTo(SquareBlock.TREE);
        assertThat(site.getTargetGrid(7, 1)).isEqualTo(SquareBlock.PROTECTED_TREE);
        assertThat(site.getTargetGrid(0, 3)).isEqualTo(SquareBlock.ROCKY_GROUND);
        assertThat(site.getTargetGrid(5, 2)).isEqualTo(SquareBlock.PLAIN_LAND);
    }

    @Test
    public void shouldTellWhetherOutOfMap() {
        assertThat(site.outOfMap(0, 0)).isFalse();
        assertThat(site.outOfMap(5, 4)).isFalse();
        assertThat(site.outOfMap(-1, 4)).isTrue();
        assertThat(site.outOfMap(10, 4)).isTrue();
        assertThat(site.outOfMap(3, -2)).isTrue();
        assertThat(site.outOfMap(3, 5)).isTrue();
    }

    @Test
    public void shouldBeAbleToGetUnclearedSquareNum() {
        assertThat(site.getUnclearedSquares()).isEqualTo(47);
    }

    @Test
    public void shouldNotChangeMapAfterChangeBulldozerDirection() {
        ConstructionSite newSite1 = site.moveLeft();
        ConstructionSite newSite2 = site.moveRight();
        assertThat(newSite1.getMap()).isEqualTo(site.getMap());
        assertThat(newSite2.getMap()).isEqualTo(site.getMap());
    }

    @Test
    public void shouldLabelSquareAsClearedAfterBulldozerPassOrStandAndAccumulateFuelAndDamage() throws OutOfMapException, ProtectedTreePenaltyException {
        List<List<SquareBlock>> constructionMap = Arrays.asList(Arrays.asList(SquareBlock.PLAIN_LAND, SquareBlock.TREE, SquareBlock.ROCKY_GROUND));
        Bulldozer bulldozer = new Bulldozer(-1, 0, Direction.East, 0, 0, false);
        ConstructionSite site = new ConstructionSite(constructionMap, bulldozer);
        ConstructionSite newSite = site.moveForward(3);
        assertThat(newSite.getTargetGrid(0, 0)).isEqualTo(SquareBlock.CLEARED_LAND);
        assertThat(newSite.getTargetGrid(1, 0)).isEqualTo(SquareBlock.CLEARED_LAND);
        assertThat(newSite.getTargetGrid(2, 0)).isEqualTo(SquareBlock.CLEARED_LAND);
        assertThat(newSite.getBulldozer().getFuelUnit()).isEqualTo(5);
        assertThat(newSite.getBulldozer().getDamageNum()).isEqualTo(1);
    }

    @Test
    public void shouldNotBeenDamagedIfStopAtTreeInsteadOfPass() throws OutOfMapException, ProtectedTreePenaltyException{
        List<List<SquareBlock>> constructionMap = Arrays.asList(Arrays.asList(SquareBlock.PLAIN_LAND, SquareBlock.ROCKY_GROUND, SquareBlock.CLEARED_LAND, SquareBlock.TREE));
        Bulldozer bulldozer = new Bulldozer(-1, 0, Direction.East, 0, 0, false);
        ConstructionSite site = new ConstructionSite(constructionMap, bulldozer);
        ConstructionSite newSite = site.moveForward(4);
        assertThat(newSite.getBulldozer().getFuelUnit()).isEqualTo(6);
        assertThat(newSite.getBulldozer().getDamageNum()).isEqualTo(0);
    }

    @Test
    public void shouldStopBeforeProtectedTreeAndThrowProtectedTreePenaltyException() throws OutOfMapException {
        List<List<SquareBlock>> constructionMap = Arrays.asList(Arrays.asList(SquareBlock.PLAIN_LAND, SquareBlock.PROTECTED_TREE, SquareBlock.PLAIN_LAND));
        Bulldozer bulldozer = new Bulldozer(-1, 0, Direction.East, 0, 0, false);
        ConstructionSite site = new ConstructionSite(constructionMap, bulldozer);
        try {
            site.moveForward(3);
        } catch (ProtectedTreePenaltyException e) {
            ConstructionSite newSite = e.getSite();
            assertThat(newSite.getMap()).isEqualTo(Arrays.asList(Arrays.asList(SquareBlock.CLEARED_LAND, SquareBlock.PROTECTED_TREE, SquareBlock.PLAIN_LAND)));
            assertThat(newSite.getBulldozer().getPenaltyFlag()).isTrue();
            assertThat(newSite.getBulldozer().getFuelUnit()).isEqualTo(1);
            assertThat(newSite.getBulldozer().getX()).isEqualTo(0);

        }
    }
    @Test
    public void shouldStopBeforeMapEdgeAndThrowOutOfMapException() throws ProtectedTreePenaltyException {
        List<List<SquareBlock>> constructionMap = Arrays.asList(Arrays.asList(SquareBlock.PLAIN_LAND, SquareBlock.PLAIN_LAND, SquareBlock.PLAIN_LAND));
        Bulldozer bulldozer = new Bulldozer(-1, 0, Direction.East, 0, 0, false);
        ConstructionSite site = new ConstructionSite(constructionMap, bulldozer);
        try {
            site.moveForward(5);
        } catch (OutOfMapException e) {
            ConstructionSite newSite = e.getSite();
            assertThat(newSite.getMap()).isEqualTo(Arrays.asList(Arrays.asList(SquareBlock.CLEARED_LAND, SquareBlock.CLEARED_LAND, SquareBlock.CLEARED_LAND)));
            assertThat(newSite.getBulldozer().getPenaltyFlag()).isEqualTo(false);
            assertThat(newSite.getBulldozer().getFuelUnit()).isEqualTo(3);
            assertThat(newSite.getBulldozer().getX()).isEqualTo(2);
        }
    }
}
