package test;

import ch.zhaw.catan.*;
import ch.zhaw.catan.Config.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player testPlayer;
    private SiedlerBoard testBoard;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(Config.Faction.RED);
    }

    @Test
    void getResourcesInPossession() {
        testPlayer.addResources(Resource.GRAIN, 1);
        testPlayer.addResources(Resource.WOOD, 1);
        testPlayer.addResources(Resource.WOOL, 1);
        testPlayer.addResources(Resource.STONE, 1);
        testPlayer.addResources(Resource.CLAY, 1);

        Map<Resource, Integer> testResources = new HashMap<>();
        testResources.put(Resource.GRAIN, 1);
        testResources.put(Resource.WOOD, 1);
        testResources.put(Resource.WOOL, 1);
        testResources.put(Resource.STONE, 1);
        testResources.put(Resource.CLAY, 1);

        assertEquals(testResources, testPlayer.getResourcesInPossession());
    }

    @Test
    void removeResources() {
        testPlayer.addResources(Resource.GRAIN, 2);
        testPlayer.removeResources(Resource.GRAIN, 1);
        Map<Resource, Integer> testResources = new HashMap<>();
        testResources.put(Resource.GRAIN, 1);
    }

    @Test
    void addResources() {
        testPlayer.addResources(Resource.GRAIN, 1);
        Map<Resource, Integer> testResources = new HashMap<>();
        testResources.put(Resource.GRAIN, 1);
        assertEquals(testResources, testPlayer.getResourcesInPossession());
    }

    @Test
    void getSettlementsBuilt() {
        Point testPosition = new Point(6, 12);
        Settlement testSettlement = new Settlement(testPosition, testPlayer);
        testPlayer.addSettlement(testSettlement);
        assertEquals(testPlayer.getSettlementsBuilt().get(0), testSettlement);
    }

    @Test
    void addSettlement() {
        testBoard = new SiedlerBoard();
        Point testPosition = new Point(6, 12);
        Settlement testSettlement = new Settlement(testPosition, testPlayer);
        testPlayer.addSettlement(testSettlement);
        assertEquals(testSettlement, testPlayer.getSettlementsBuilt().get(0));
    }

    @Test
    void buildSettlement() {
        testBoard = new SiedlerBoard();
        SiedlerGame testGame = new SiedlerGame(7, 2);
        Point testPosition = new Point(6, 12);
        Settlement testSettlement = new Settlement(testPosition, testPlayer);
        testPlayer.addResources(Resource.GRAIN, 1);
        testPlayer.addResources(Resource.WOOD, 1);
        testPlayer.addResources(Resource.WOOL, 1);
        testPlayer.addResources(Resource.CLAY, 1);
        testGame.placeSettlement(testPosition, testPlayer, testBoard);
        assertEquals(testBoard.getCorner(testPosition).getFaction(), testSettlement.getFaction());
    }

    @Test
    void getFaction() {
        assertEquals(Config.Faction.RED, testPlayer.getFaction());
    }

    @Test
    void getRoadsBuilt() { // TODO: Doesn't work yet
        testBoard = new SiedlerBoard();
        Point testPositionStart = new Point(6, 12);
        Point testPositionEnd = new Point(6, 10);
        Road testRoad = new Road(testPlayer, testPositionStart, testPositionEnd);
        testPlayer.addResources(Resource.WOOD, 1);
        testPlayer.addResources(Resource.CLAY, 1);
        testPlayer.buildRoad(testPlayer, testPositionStart, testPositionEnd);
        assertEquals(testPlayer.getRoadsBuilt().get(0), testRoad);
    }

    @Test
    void buildRoad() { // TODO: Doesn't work yet
        testBoard = new SiedlerBoard();
        SiedlerGame testGame = new SiedlerGame(7, 2);
        Point testPositionStart = new Point(6, 10);
        Point testPositionEnd = new Point(6, 12);
        Road testRoad = new Road(testPlayer, testPositionStart, testPositionEnd);
        testPlayer.addResources(Resource.GRAIN, 1);
        testPlayer.addResources(Resource.WOOD, 2);
        testPlayer.addResources(Resource.WOOL, 1);
        testPlayer.addResources(Resource.CLAY, 2);
        testGame.placeSettlement(testPositionStart, testPlayer, testBoard);
        testGame.placeRoad(testPositionStart, testPositionEnd, testBoard, testPlayer);
        assertEquals(testBoard.getEdge(testPositionStart, testPositionEnd), testRoad);
    }

    @Test
    void isCornerConnectedToLandTest() {
        SiedlerBoard testBoard = new SiedlerBoard();
        SiedlerGame game = new SiedlerGame(7, 2);
        Point falseOne = new Point(3, 3);
        Point falseTwo = new Point(12, 4);
        Point trueOne = new Point(3, 7);
        Point trueTwo = new Point(5, 9);

        assertFalse(game.isCornerConnectedToLand(falseOne, testBoard));
        assertFalse(game.isCornerConnectedToLand(falseTwo, testBoard));
        assertTrue(game.isCornerConnectedToLand(trueOne, testBoard));
        assertTrue(game.isCornerConnectedToLand(trueTwo, testBoard));

    }
}