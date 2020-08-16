package org.example.constructionsitesimulator;

import org.junit.Test;
import static org.fest.assertions.Assertions.assertThat;


public class BulldozerTest {
    Bulldozer bulldozer = new Bulldozer(3, 2, Direction.East, 4, 2, false);

    @Test
    public void shouldFacingNorthAfterTurnLeftFromEast() {
        bulldozer = bulldozer.turnLeft();
        assertThat(bulldozer.getFacing()).isEqualTo(Direction.North);
        assertThat(bulldozer.getX()).isEqualTo(3);
        assertThat(bulldozer.getY()).isEqualTo(2);
        assertThat(bulldozer.getFuelUnit()).isEqualTo(4);
        assertThat(bulldozer.getDamageNum()).isEqualTo(2);
        assertThat(bulldozer.getPenaltyFlag()).isFalse();
    }

    @Test
    public void shouldFacingSouthAfterTurnRightFromEast() {
        bulldozer = bulldozer.turnRight();
        assertThat(bulldozer.getFacing()).isEqualTo(Direction.South);
        assertThat(bulldozer.getX()).isEqualTo(3);
        assertThat(bulldozer.getY()).isEqualTo(2);
        assertThat(bulldozer.getFuelUnit()).isEqualTo(4);
        assertThat(bulldozer.getDamageNum()).isEqualTo(2);
        assertThat(bulldozer.getPenaltyFlag()).isFalse();
    }

    @Test
    public void shouldMoveForwardBasedOnFacingDirection() {
        bulldozer = bulldozer.moveForward();
        assertThat(bulldozer.getX()).isEqualTo(4);
        assertThat(bulldozer.getY()).isEqualTo(2);
        assertThat(bulldozer.getFacing()).isEqualTo(Direction.East);
        assertThat(bulldozer.getFuelUnit()).isEqualTo(4);
        assertThat(bulldozer.getDamageNum()).isEqualTo(2);
        assertThat(bulldozer.getPenaltyFlag()).isFalse();
    }

    @Test
    public void shouldAccumulateFuelAndDamange() {
        bulldozer = bulldozer.updateUsage(2, 1);
        assertThat(bulldozer.getX()).isEqualTo(3);
        assertThat(bulldozer.getY()).isEqualTo(2);
        assertThat(bulldozer.getFacing()).isEqualTo(Direction.East);
        assertThat(bulldozer.getFuelUnit()).isEqualTo(6);
        assertThat(bulldozer.getDamageNum()).isEqualTo(3);
        assertThat(bulldozer.getPenaltyFlag()).isFalse();
    }

    @Test
    public void shouldFlagTreePenaltyField() {
        bulldozer = bulldozer.flagPenalty();
        assertThat(bulldozer.getPenaltyFlag()).isTrue();
        assertThat(bulldozer.getX()).isEqualTo(3);
        assertThat(bulldozer.getY()).isEqualTo(2);
        assertThat(bulldozer.getFacing()).isEqualTo(Direction.East);
        assertThat(bulldozer.getFuelUnit()).isEqualTo(4);
        assertThat(bulldozer.getDamageNum()).isEqualTo(2);
    }
}
