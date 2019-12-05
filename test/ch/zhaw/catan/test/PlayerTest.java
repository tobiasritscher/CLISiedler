package test;

import ch.zhaw.catan.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.zhaw.catan.Config.Resource;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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

        Assertions.assertEquals(testResources, testPlayer.getResourcesInPossession());
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
        Assertions.assertEquals(testResources, testPlayer.getResourcesInPossession());
    }

    @Test
    void getSettlementsBuilt() {
        Point testPosition = new Point(6, 12);
        Settlement testSettlement = new Settlement(testPosition, testPlayer);
        testPlayer.addSettlement(testSettlement);
        Assertions.assertEquals(testPlayer.getSettlementsBuilt().get(0), testSettlement);
    }

    @Test
    void addSettlement() {
        testBoard = new SiedlerBoard();
        Point testPosition = new Point(6, 12);
        Settlement testSettlement = new Settlement(testPosition, testPlayer);
        testPlayer.addSettlement(testSettlement);
        Assertions.assertEquals(testSettlement, testPlayer.getSettlementsBuilt().get(0));
    }

    @Test
    void buildSettlement() {
        testBoard = new SiedlerBoard();
        Point testPosition = new Point(6, 12);
        Settlement testSettlement = new Settlement(testPosition, testPlayer);
        testPlayer.addResources(Resource.GRAIN, 1);
        testPlayer.addResources(Resource.WOOD, 1);
        testPlayer.addResources(Resource.WOOL, 1);
        testPlayer.addResources(Resource.CLAY, 1);
        testPlayer.buildSettlement(testPosition, testPlayer, testBoard);
        Assertions.assertEquals(testBoard.getCorner(testPosition).getFaction(), testSettlement.getFaction());
    }

    @Test
    void getFaction() {
        Assertions.assertEquals(Config.Faction.RED, testPlayer.getFaction());
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
        Assertions.assertEquals(testPlayer.getRoadsBuilt().get(0), testRoad);
    }

    @Test
    void buildRoad() { // TODO: Doesn't work yet
        testBoard = new SiedlerBoard();
        Point testPositionStart = new Point(6, 12);
        Point testPositionEnd = new Point(6, 10);
        Settlement testSettlement = new Settlement(testPositionStart, testPlayer);
        Road testRoad = new Road(testPlayer, testPositionStart, testPositionEnd);
        testPlayer.addResources(Resource.GRAIN, 1);
        testPlayer.addResources(Resource.WOOD, 2);
        testPlayer.addResources(Resource.WOOL, 1);
        testPlayer.addResources(Resource.CLAY, 2);
        testPlayer.buildSettlement(testPositionStart, testPlayer, testBoard);
        testPlayer.buildRoad(testPlayer, testPositionStart, testPositionEnd);
        Assertions.assertEquals(testBoard.getEdge(testPositionStart, testPositionEnd).getEndingAt(), testRoad.getEndingAt());
        Assertions.assertEquals(testBoard.getEdge(testPositionStart, testPositionEnd).getStartingAt(), testRoad.getStartingAt());
    }

    @Test
    void getInitialResources() {
        testBoard = new SiedlerBoard();
        Player blue = new Player(Config.Faction.BLUE);
        Player red = new Player(Config.Faction.RED);
        Point one = new Point(6, 6);
        Point two = new Point(10, 6);
        Point three = new Point(8, 12);
        Point four = new Point(5, 15);
        testBoard.setCorner(one, new Settlement(one, blue));
        testBoard.setCorner(two, new Settlement(two, blue));
        testBoard.setCorner(three, new Settlement(three, red));
        testBoard.setCorner(four, new Settlement(four, red));
        PlayGame playGame = new PlayGame();
        playGame.giveResourcesAfterFirstPhase(testBoard);

        ResourceStock resourceStockBlue = new ResourceStock();
        resourceStockBlue.add(Resource.WOOD, 1);
        resourceStockBlue.add(Resource.WOOL, 1);
        resourceStockBlue.add(Resource.WOOL, 1);
        resourceStockBlue.add(Resource.STONE, 1);
        resourceStockBlue.add(Resource.WOOD, 1);

        ResourceStock resourceStockRed = new ResourceStock();
        resourceStockRed.add(Resource.STONE, 1);
        resourceStockRed.add(Resource.WOOD, 1);
        resourceStockRed.add(Resource.STONE, 1);
        resourceStockRed.add(Resource.WOOL, 1);
        resourceStockRed.add(Resource.GRAIN, 1);

        Assertions.assertEquals(blue.getResourcesInPossession(), resourceStockBlue.getResources());
        Assertions.assertEquals(red.getResourcesInPossession(), resourceStockRed.getResources());
    }

    @Test
    void isCornerConnectedToLandTest() {
        SiedlerBoard testBoard = new SiedlerBoard();
        SiedlerGame game = new SiedlerGame(7,2);
        Point falseOne =  new Point(3,3);
        Point falseTwo = new Point(12,4);
        Point trueOne = new Point(3,7);
        Point trueTwo = new Point(5,9);

        Assertions.assertFalse(game.isCornerConnectedToLand(falseOne, testBoard));
        Assertions.assertFalse(game.isCornerConnectedToLand(falseTwo, testBoard));
        Assertions.assertTrue(game.isCornerConnectedToLand(trueOne, testBoard));
        Assertions.assertTrue(game.isCornerConnectedToLand(trueTwo, testBoard));

    }
}