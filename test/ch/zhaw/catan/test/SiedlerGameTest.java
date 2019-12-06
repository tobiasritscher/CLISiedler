package test;

import ch.zhaw.catan.Config;
import ch.zhaw.catan.Player;
import ch.zhaw.catan.SiedlerBoard;
import ch.zhaw.catan.SiedlerGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SiedlerGameTest {

    private SiedlerGame testGame;
    private SiedlerBoard testBoard;

    @BeforeEach
    void setUp() {
        testGame = new SiedlerGame(20, 4);
        testBoard = new SiedlerBoard();
    }

    @Test
    void getPlayer() {
        assertEquals("rr", testGame.getPlayers().get(0).getFaction().toString());
        assertEquals("bb", testGame.getPlayers().get(1).getFaction().toString());
        assertEquals("gg", testGame.getPlayers().get(2).getFaction().toString());
        assertEquals("yy", testGame.getPlayers().get(3).getFaction().toString());
    }

    @Test
    void getCurrentPlayer() {
        assertEquals("rr", testGame.getCurrentPlayer().getFaction().toString());
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

        assertEquals(testResources, testGame.getCurrentPlayer().getResourcesInPossession());
    }

    @Test
    void placeInitialSettlement() {
        Point testPointOne = new Point(5, 7);
        Player testPlayer = new Player(Config.Faction.RED);
        SiedlerGame testGame = new SiedlerGame(7,2);
        testGame.placeInitialSettlement(testPointOne, testPlayer, testBoard);
        assertEquals(testPlayer.getSettlementsBuilt().get(0), testBoard.getCorner(testPointOne));

        Point testPointTwo = new Point(7, 13);
        testGame.placeInitialSettlement(testPointTwo, testPlayer, testBoard);
        assertEquals(testPlayer.getSettlementsBuilt().get(1), testBoard.getCorner(testPointTwo));
    }

    @Test
    void placeCity() {
        // TODO: Implement
    }

    @Test
    void tradeWithBankFourToOne() { // TODO: Redundant, same test as BankTest class, maybe ask muon per mail which one to keep?
        Player testPlayer = new Player(Config.Faction.RED);
        testPlayer.addResources(Config.Resource.GRAIN, 5);
        testPlayer.addResources(Config.Resource.CLAY, 3);

        String expectedValue = "{GR=1, CL=3, WD=1}";
        // Positive test
        testGame.tradeWithBankFourToOne(Config.Resource.GRAIN, Config.Resource.WOOD, testPlayer);
        assertEquals(expectedValue, testPlayer.getResourcesInPossession().toString());

        // Negative test
        testGame.tradeWithBankFourToOne(Config.Resource.CLAY, Config.Resource.WOOD, testPlayer);
        assertEquals(expectedValue, testPlayer.getResourcesInPossession().toString());
    }

    @Test
    void getWinner() {
        // TODO: Implement
    }

    @Test
    void createPlayers() { // TODO: Somehow the test fails, it passed a few days ago
        testGame = new SiedlerGame(20, 1);
        assertEquals(2, testGame.createPlayers(1));
        testGame = new SiedlerGame(20, 5);
        assertEquals(4, testGame.createPlayers(5));
    }

    @Test
    void isPointACorner() {
        // TODO: Implement
    }

    @Test
    void placeRoad() {
        // TODO: Implement
    }

    @Test
    void validRoadPlacement() {
        Point positiveTestPointStart = new Point(6, 6);
        Point positiveTestPointEnd = new Point(5, 7);
        Point negativeTestPointStart = new Point(7, 13);
        Point negativeTestPointEnd = new Point(8, 15);
        testGame.placeInitialSettlement(positiveTestPointStart, testGame.getCurrentPlayer(), testBoard);

        // Positive test TODO: Positive test doesn't work yet, help me!
        assertTrue(testGame.validRoadPlacement(positiveTestPointStart, positiveTestPointEnd, testBoard, testGame.getCurrentPlayer()));

        // Negative test
        assertFalse(testGame.validRoadPlacement(negativeTestPointStart, negativeTestPointEnd, testBoard, testGame.getCurrentPlayer()));
    }
}