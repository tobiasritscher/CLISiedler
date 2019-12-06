package test;

import ch.zhaw.catan.Config;
import ch.zhaw.catan.Player;
import ch.zhaw.catan.Road;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoadTest {

    private Player testPlayer = new Player(Config.Faction.RED);
    private Point testPointStart = new Point(6,6);
    private Point testPointEnd = new Point(5,7);
    private Road testRoad = new Road(testPlayer, testPointStart, testPointEnd);

    @Test
    void getStartingAt() {
        assertEquals(testPointStart, testRoad.getStartingAt());
    }

    @Test
    void getEndingAt() {
        assertEquals(testPointEnd, testRoad.getEndingAt());
    }

    @Test
    void testToString() {
        assertEquals("R", testRoad.toString());
    }
}