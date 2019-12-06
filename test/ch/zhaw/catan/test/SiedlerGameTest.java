package test;

import ch.zhaw.catan.*;
import org.junit.jupiter.api.Assertions;
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
        Bank testBank = new Bank();

        testGame.getCurrentPlayer().addResources(Config.Resource.GRAIN, 5);
        testGame.getCurrentPlayer().addResources(Config.Resource.CLAY, 3);

        ResourceStock bankTestStock = new ResourceStock();
        bankTestStock.add(Config.Resource.WOOD, 1);
        bankTestStock.add(Config.Resource.GRAIN, 1);
        bankTestStock.add(Config.Resource.CLAY, 3);

        // Positive test
        testGame.tradeWithBankFourToOne(Config.Resource.GRAIN, Config.Resource.WOOD, testGame.getCurrentPlayer(), testBank);
        Assertions.assertEquals(bankTestStock.getResources(), testGame.getCurrentPlayer().getResourcesInPossession());

        // Negative test
        testGame.tradeWithBankFourToOne(Config.Resource.CLAY, Config.Resource.WOOD, testGame.getCurrentPlayer(), testBank);
        Assertions.assertEquals(bankTestStock.getResources(), testGame.getCurrentPlayer().getResourcesInPossession());
    }

    @Test
    void getWinner() {
        // TODO: Implement
    }

    @Test
    void createPlayers() {
        testGame = new SiedlerGame(7, 2);
        assertEquals(2, testGame.createPlayers(2));
        testGame = new SiedlerGame(7, 3);
        assertEquals(3, testGame.createPlayers(3));
        testGame = new SiedlerGame(7, 4);
        assertEquals(4, testGame.createPlayers(4));
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