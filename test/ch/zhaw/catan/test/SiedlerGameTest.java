package test;

import ch.zhaw.catan.Config;
import ch.zhaw.catan.SiedlerGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SiedlerGameTest {

    private SiedlerGame testGame;

    @BeforeEach
    void setUp() {
        testGame = new SiedlerGame(20, 4);
    }

    @Test
    void switchToNextPlayer() {
        testGame.switchToNextPlayer();
        Assertions.assertEquals(testGame.getPlayers().get(1), testGame.getCurrentPlayer());
    }

    @Test
    void switchToPreviousPlayer() {
        testGame.switchToPreviousPlayer();
        Assertions.assertEquals(testGame.getPlayers().get(3), testGame.getCurrentPlayer());
    }

    @Test
    void getPlayer() {
        Assertions.assertEquals("rr", testGame.getPlayers().get(0).getFaction().toString());
        Assertions.assertEquals("bb", testGame.getPlayers().get(1).getFaction().toString());
        Assertions.assertEquals("gg", testGame.getPlayers().get(2).getFaction().toString());
        Assertions.assertEquals("yy", testGame.getPlayers().get(3).getFaction().toString());
    }

    @Test
    void getCurrentPlayer() {
        Assertions.assertEquals("rr", testGame.getCurrentPlayer().getFaction().toString());
    }

    @Test
    void getCurrentPlayerResourceStock() {
        Map<Config.Resource, Integer> testResources = new HashMap<>();
        testResources.put(Config.Resource.GRAIN, 1);
        testResources.put(Config.Resource.WOOD, 1);
        testResources.put(Config.Resource.WOOL, 1);
        testResources.put(Config.Resource.STONE, 1);
        testResources.put(Config.Resource.CLAY, 1);

        testGame.getCurrentPlayer().addResources(Config.Resource.GRAIN, 1);
        testGame.getCurrentPlayer().addResources(Config.Resource.WOOD, 1);
        testGame.getCurrentPlayer().addResources(Config.Resource.WOOL, 1);
        testGame.getCurrentPlayer().addResources(Config.Resource.STONE, 1);
        testGame.getCurrentPlayer().addResources(Config.Resource.CLAY, 1);

        Assertions.assertEquals(testResources, testGame.getCurrentPlayer().getResourcesInPossession());
    }

    @Test
    void placeInitialSettlement() {
    }

    @Test
    void throwDice() {
        int diceThrow = 5;
        Map<Config.Faction, List<Config.Resource>> diceTestMap = new HashMap<>();
        diceTestMap = testGame.throwDice(diceThrow);
        assertTrue(Config.getStandardDiceNumberPlacement().containsKey(diceThrow));


    }

    @Test
    void placeCity() {
    }

    @Test
    void tradeWithBankFourToOne() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void createPlayers() {
        testGame = new SiedlerGame(20, 1);
        Assertions.assertEquals(2, testGame.createPlayers(1));
        testGame = new SiedlerGame(20, 5);
        Assertions.assertEquals(4, testGame.createPlayers(5));
    }

    @Test
    void isPointACorner() {
    }

    @Test
    void placeRoad() {
    }
}