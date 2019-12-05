package test;

import ch.zhaw.catan.City;
import ch.zhaw.catan.Config;
import ch.zhaw.catan.Player;
import ch.zhaw.catan.Settlement;
import org.junit.jupiter.api.Assertions;
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
        Assertions.assertEquals(settlementTestPoint, testSettlement.getPosition());
        Assertions.assertEquals(cityTestPoint, testCity.getPosition());
    }

    @Test
    void getPlayer() {
        Assertions.assertEquals(testPlayer, testSettlement.getPlayer());
        Assertions.assertEquals(testPlayer, testCity.getPlayer());
    }

    @Test
    void getWinPoints() {
        Assertions.assertEquals(1, testSettlement.getWinPoints());
        Assertions.assertEquals(2, testCity.getWinPoints());
    }

    @Test
    void getFaction() {
        Assertions.assertEquals("RR", testSettlement.getFaction().toString());
        Assertions.assertEquals("RR", testCity.getFaction().toString());
    }
}