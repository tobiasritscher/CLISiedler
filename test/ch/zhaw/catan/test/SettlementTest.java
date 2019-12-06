package test;

import ch.zhaw.catan.City;
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
    private Point cityTestPoint = new Point(5, 15);
    private Settlement testSettlement;
    private City testCity;

    @BeforeEach
    void setUp() {
        testSettlement = new Settlement(settlementTestPoint, testPlayer);
        testCity = new City(cityTestPoint, testPlayer);
    }

    @Test
    void getPosition() {
        assertEquals(settlementTestPoint, testSettlement.getPosition());
        assertEquals(cityTestPoint, testCity.getPosition());
    }

    @Test
    void getPlayer() {
        assertEquals(testPlayer, testSettlement.getPlayer());
        assertEquals(testPlayer, testCity.getPlayer());
    }

    @Test
    void getWinPoints() {
        assertEquals(1, testSettlement.getWinPoints());
        assertEquals(2, testCity.getWinPoints());
    }

    @Test
    void getFaction() {
        assertEquals("RR", testSettlement.getFaction().toString());
        assertEquals("RR", testCity.getFaction().toString());
    }

    @Test
    void getIsCity() {
        assertFalse(testSettlement.getIsCity());
        assertTrue(testCity.getIsCity());
    }

    @Test
    void testToString() {
        assertEquals("RR", testSettlement.toString());
        assertEquals("CR", testCity.toString());
    }
}