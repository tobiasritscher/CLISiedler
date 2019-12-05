package test;

import ch.zhaw.catan.Config;
import ch.zhaw.catan.SiedlerBoard;
import ch.zhaw.catan.SiedlerGame;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SiedlerGameTest {

    private SiedlerGame testGame;
    private SiedlerBoard testBoard;

    @BeforeEach
    void setUp() {
        testGame = new SiedlerGame(20, 4);
        testBoard = new SiedlerBoard();
    }

    @Test
    void switchToNextPlayer() {
        testGame.switchToNextPlayer();
        // Checks if player is current player
        Assertions.assertEquals(testGame.getPlayers().get(1), testGame.getCurrentPlayer());
        // Faction test
        Assertions.assertEquals("bb", testGame.getPlayers().get(1).getFaction().toString());
    }

    @Test
    void switchToPreviousPlayer() {
        // TODO: First test doesn't work yet and i can't explain why! Help me
        testGame.switchToPreviousPlayer();
        // Checks if player is current player
        Assertions.assertEquals(testGame.getPlayers().get(3), testGame.getCurrentPlayer());
        // Faction test
        Assertions.assertEquals("yy", testGame.getPlayers().get(3).getFaction().toString());
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
    void placeInitialSettlement() { // TODO positive doesn't work yet! Maybe initial Settlement isn't saved in Player.settlementsBuilt?
        Point positiveTestPoint = new Point(5, 7);
        testGame.placeInitialSettlement(positiveTestPoint, testGame.getCurrentPlayer(), testBoard);
        Assertions.assertEquals(testGame.getCurrentPlayer().getSettlementsBuilt().get(0), testBoard.getCorner(positiveTestPoint));

        Point negativeTestPoint = new Point(7, 13);
        testGame.placeInitialSettlement(negativeTestPoint, testGame.getCurrentPlayer(), testBoard);
        Assertions.assertEquals(null, testBoard.getCorner(negativeTestPoint));
    }

    @Test
    void throwDice() { // TODO: Implement, doesnt work yet
        int diceThrow = 5;
        Map<Config.Faction, List<Config.Resource>> diceTestMap = new HashMap<>();
        diceTestMap = testGame.throwDice(diceThrow);
        assertTrue(Config.getStandardDiceNumberPlacement().containsKey(diceThrow));
        // TODO: create demo settlements, make method look up field in board and return an example resource stock
    }

    @Test
    void placeCity() {
        // TODO: Implement
    }

    @Test
    void tradeWithBankFourToOne() { // TODO: Redundant, same test as BankTest class, maybe ask muon per mail which one to keep?
        testGame.getCurrentPlayer().addResources(Config.Resource.GRAIN, 5);
        testGame.getCurrentPlayer().addResources(Config.Resource.CLAY, 3);

        String expectedValue = "{GR=1, WD=1, CL=3}";
        // Positive test
        testGame.tradeWithBankFourToOne(Config.Resource.GRAIN, Config.Resource.WOOD);
        Assertions.assertEquals(expectedValue, testGame.getCurrentPlayer().getResourcesInPossession().toString());

        // Negative test
        testGame.tradeWithBankFourToOne(Config.Resource.CLAY, Config.Resource.WOOD);
        Assertions.assertEquals(expectedValue, testGame.getCurrentPlayer().getResourcesInPossession().toString());
    }

    @Test
    void getWinner() {
        // TODO: Implement
    }

    @Test
    void createPlayers() { // TODO: Somehow the test fails, it passed a few days ago
        testGame = new SiedlerGame(20, 1);
        Assertions.assertEquals(2, testGame.createPlayers(1));
        testGame = new SiedlerGame(20, 5);
        Assertions.assertEquals(4, testGame.createPlayers(5));
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
        Assertions.assertTrue(testGame.validRoadPlacement(positiveTestPointStart, positiveTestPointEnd, testBoard, testGame.getCurrentPlayer()));

        // Negative test
        Assertions.assertFalse(testGame.validRoadPlacement(negativeTestPointStart, negativeTestPointEnd, testBoard, testGame.getCurrentPlayer()));
    }
}