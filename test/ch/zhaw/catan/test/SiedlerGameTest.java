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
        testGame = new SiedlerGame(7, 4);
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
        assertEquals("rr", testGame.getfirstPlayer().getFaction().toString());
    }

    @Test
    void getCurrentPlayerResourceStock() {
        Map<Config.Resource, Integer> testResources = new HashMap<>();
        Bank testBank = new Bank();
        testResources.put(Config.Resource.GRAIN, 1);
        testResources.put(Config.Resource.WOOD, 1);
        testResources.put(Config.Resource.WOOL, 1);
        testResources.put(Config.Resource.STONE, 1);
        testResources.put(Config.Resource.CLAY, 1);

        testGame.getfirstPlayer().addResources(Config.Resource.GRAIN, 1, testBank);
        testGame.getfirstPlayer().addResources(Config.Resource.WOOD, 1, testBank);
        testGame.getfirstPlayer().addResources(Config.Resource.WOOL, 1, testBank);
        testGame.getfirstPlayer().addResources(Config.Resource.STONE, 1, testBank);
        testGame.getfirstPlayer().addResources(Config.Resource.CLAY, 1, testBank);

        assertEquals(testResources, testGame.getfirstPlayer().getResourcesInPossession());
    }

    @Test
    void placeInitialSettlement() {
        testBoard = new SiedlerBoard();
        SiedlerBoardTextView view = new SiedlerBoardTextView(testBoard);
        Point testPointOne = new Point(5, 7);
        Player testPlayer = new Player(Config.Faction.RED);
        SiedlerGame testGame = new SiedlerGame(7, 2);
        testGame.placeInitialSettlement(testPointOne, testPlayer, testBoard, view);
        assertEquals(testPlayer.getSettlementsBuilt().get(0), testBoard.getCorner(testPointOne));

        Point testPointTwo = new Point(7, 13);
        testGame.placeInitialSettlement(testPointTwo, testPlayer, testBoard, view);
        assertEquals(testPlayer.getSettlementsBuilt().get(1), testBoard.getCorner(testPointTwo));
    }

    @Test
    void placeCity() {
        SiedlerBoard testBoard = new SiedlerBoard();
        SiedlerBoardTextView testView = new SiedlerBoardTextView(testBoard);
        Bank testBank = new Bank();

        testGame.getPlayers().get(0).addResources(Config.Resource.STONE, 7, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.GRAIN, 6, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.WOOD, 1, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.WOOL, 1, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.CLAY, 1, testBank);

        Point testPoint = new Point(11, 15);

        testGame.placeSettlement(testPoint, testGame.getPlayers().get(0), testBoard, testBank, testView);

        // Negative test
        assertFalse(testGame.getPlayers().get(0).getSettlementsBuilt().get(0).getIsCity());

        testGame.placeCity(testPoint, testGame.getPlayers().get(0), testBank, testView);

        // Positive test
        assertTrue(testGame.getPlayers().get(0).getSettlementsBuilt().get(0).getIsCity());
    }

    @Test
    void tradeWithBankFourToOne() {
        Bank testBank = new Bank();

        testGame.getfirstPlayer().addResources(Config.Resource.GRAIN, 5, testBank);
        testGame.getfirstPlayer().addResources(Config.Resource.CLAY, 3, testBank);

        ResourceStock bankTestStock = new ResourceStock();
        bankTestStock.add(Config.Resource.WOOD, 1);
        bankTestStock.add(Config.Resource.GRAIN, 1);
        bankTestStock.add(Config.Resource.CLAY, 3);

        // Positive test
        testGame.tradeWithBankFourToOne(Config.Resource.GRAIN, Config.Resource.WOOD, testGame.getfirstPlayer(), testBank);
        Assertions.assertEquals(bankTestStock.getResources(), testGame.getfirstPlayer().getResourcesInPossession());

        // Negative test
        testGame.tradeWithBankFourToOne(Config.Resource.CLAY, Config.Resource.WOOD, testGame.getfirstPlayer(), testBank);
        Assertions.assertEquals(bankTestStock.getResources(), testGame.getfirstPlayer().getResourcesInPossession());
    }

    @Test
    void getWinner() {
        SiedlerBoard testBoard = new SiedlerBoard();
        SiedlerBoardTextView testView = new SiedlerBoardTextView(testBoard);
        Bank testBank = new Bank();

        testGame.getPlayers().get(0).addResources(Config.Resource.STONE, 12, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.GRAIN, 12, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.WOOD, 4, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.WOOL, 4, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.CLAY, 4, testBank);

        Point testPoint1 = new Point(11, 15);
        Point testPoint2 = new Point(7, 13);
        Point testPoint3 = new Point(3,13);
        Point testPoint4 = new Point(3,7);

        testGame.placeSettlement(testPoint1, testGame.getPlayers().get(0), testBoard, testBank, testView);
        testGame.placeSettlement(testPoint2, testGame.getPlayers().get(0), testBoard, testBank, testView);
        testGame.placeSettlement(testPoint3, testGame.getPlayers().get(0), testBoard, testBank, testView);
        testGame.placeSettlement(testPoint4, testGame.getPlayers().get(0), testBoard, testBank, testView);

        // Negative test
        Assertions.assertFalse(testGame.getWinner(testGame.getPlayers().get(0)));

        testGame.placeCity(testPoint1, testGame.getPlayers().get(0), testBank, testView);
        testGame.placeCity(testPoint2, testGame.getPlayers().get(0), testBank, testView);
        testGame.placeCity(testPoint3, testGame.getPlayers().get(0), testBank, testView);
        testGame.placeCity(testPoint4, testGame.getPlayers().get(0), testBank, testView);

        // Positive test
        Assertions.assertTrue(testGame.getWinner(testGame.getPlayers().get(0)));
    }

    @Test
    void createPlayers() {
        testGame = new SiedlerGame(7, 2);
        assertEquals(2, testGame.getPlayers().size());
        testGame = new SiedlerGame(7, 3);
        assertEquals(3, testGame.getPlayers().size());
        testGame = new SiedlerGame(7, 4);
        assertEquals(4, testGame.getPlayers().size());
    }

    @Test
    void placeRoad() {
        Bank testBank = new Bank();
        SiedlerBoard testBoard = new SiedlerBoard();
        SiedlerBoardTextView testView = new SiedlerBoardTextView(testBoard);

        testGame.getPlayers().get(0).addResources(Config.Resource.CLAY, 5, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.WOOD, 5, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.WOOL, 1, testBank);
        testGame.getPlayers().get(0).addResources(Config.Resource.GRAIN, 1, testBank);

        Point positiveTestPointEnd = new Point(6, 16);
        Point positiveTestPointStart = new Point(5, 15);
        Road testRoad = new Road(testGame.getPlayers().get(0), positiveTestPointStart, positiveTestPointEnd);

        testGame.placeSettlement(positiveTestPointEnd, testGame.getPlayers().get(0), testBoard, testBank, testView);
        testGame.placeRoad(positiveTestPointStart, positiveTestPointEnd, testBoard, testGame.getPlayers().get(0), testBank, testView);
        assertEquals(testBoard.getEdge(positiveTestPointStart, positiveTestPointEnd).toString(), testRoad.toString());
    }

    @Test
    void validRoadPlacement() {
        testBoard = new SiedlerBoard();
        SiedlerBoardTextView testVIew = new SiedlerBoardTextView(testBoard);
        Point positiveTestPointStart = new Point(6, 6);
        Point positiveTestPointEnd = new Point(5, 7);
        Point negativeTestPointStart = new Point(7, 13);
        Point negativeTestPointEnd = new Point(8, 15);
        testGame.placeInitialSettlement(positiveTestPointStart, testGame.getfirstPlayer(), testBoard, testVIew);

        // Positive test
        assertTrue(testGame.validRoadPlacement(positiveTestPointStart, positiveTestPointEnd, testBoard, testGame.getfirstPlayer()));

        // Negative test
        assertFalse(testGame.validRoadPlacement(negativeTestPointStart, negativeTestPointEnd, testBoard, testGame.getfirstPlayer()));
    }
}