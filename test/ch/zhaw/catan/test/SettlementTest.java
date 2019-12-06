package test;

import ch.zhaw.catan.Config;
import ch.zhaw.catan.Player;
import ch.zhaw.catan.Settlement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SettlementTest {

    private Player testPlayer = new Player(Config.Faction.RED);
    private Point settlementTestPoint = new Point(8, 12);
    private Settlement testSettlement;

    @BeforeEach
    void setUp() {
        testSettlement = new Settlement(settlementTestPoint, testPlayer);
    }

    @Test
    void getPosition() {
        assertEquals(settlementTestPoint, testSettlement.getPosition());
    }

    @Test
    void getPlayer() {
        assertEquals(testPlayer, testSettlement.getPlayer());
    }

    @Test
    void getWinPoints() {
        assertEquals(1, testSettlement.getWinPoints());
    }

    @Test
    void getFaction() {
        assertEquals("RR", testSettlement.getFaction().toString());
    }

    @Test
    void getIsCity() {
        assertFalse(testSettlement.getIsCity());
    }

    @Test
    void testToString() {
        assertEquals("RR", testSettlement.toString());
    }
}