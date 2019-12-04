package test;

import ch.zhaw.catan.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    @AfterEach
    void tearDown() {
    }

    @Test
    void getResourcesInPossession() {
        testPlayer.addResources(Config.Resource.GRAIN, 1);
        testPlayer.addResources(Config.Resource.WOOD, 1);
        testPlayer.addResources(Config.Resource.WOOL, 1);
        testPlayer.addResources(Config.Resource.STONE, 1);
        testPlayer.addResources(Config.Resource.CLAY, 1);


        Map<Config.Resource, Integer> testResources = new HashMap<>();
        testResources.put(Config.Resource.GRAIN, 1);
        testResources.put(Config.Resource.WOOD, 1);
        testResources.put(Config.Resource.WOOL, 1);
        testResources.put(Config.Resource.STONE, 1);
        testResources.put(Config.Resource.CLAY, 1);

        Assertions.assertEquals(testResources, testPlayer.getResourcesInPossession());
    }

    @Test
    void removeResources() {
        testPlayer.addResources(Config.Resource.GRAIN, 2);
        testPlayer.removeResources(Config.Resource.GRAIN, 1);
        Map<Config.Resource, Integer> testResources = new HashMap<>();
        testResources.put(Config.Resource.GRAIN, 1);

    }

    @Test
    void addResources() {
        testPlayer.addResources(Config.Resource.GRAIN, 1);
        Map<Config.Resource, Integer> testResources = new HashMap<>();
        testResources.put(Config.Resource.GRAIN, 1);
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
        testPlayer.addResources(Config.Resource.GRAIN, 1);
        testPlayer.addResources(Config.Resource.WOOD, 1);
        testPlayer.addResources(Config.Resource.WOOL, 1);
        testPlayer.addResources(Config.Resource.CLAY, 1);
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
        Point testPositionEnd = new Point(6, 20);
        Road testRoad = new Road(testPlayer, testPositionStart, testPositionEnd);
        testPlayer.addResources(Config.Resource.WOOD, 1);
        testPlayer.addResources(Config.Resource.CLAY, 1);
        testPlayer.buildRoad(testPlayer, testPositionStart, testPositionEnd);
        Assertions.assertEquals(testPlayer.getRoadsBuilt().get(0), testRoad);
    }

    @Test
    void buildRoad() { // TODO: Doesn't work yet
        testBoard = new SiedlerBoard();
        Point testPositionStart = new Point(6, 12);
        Point testPositionEnd = new Point(6, 20);
        Settlement testSettlement = new Settlement(testPositionStart, testPlayer);
        Road testRoad = new Road(testPlayer, testPositionStart, testPositionEnd);
        testPlayer.addResources(Config.Resource.GRAIN, 1);
        testPlayer.addResources(Config.Resource.WOOD, 2);
        testPlayer.addResources(Config.Resource.WOOL, 1);
        testPlayer.addResources(Config.Resource.CLAY, 2);
        testPlayer.buildSettlement(testPositionStart, testPlayer, testBoard);
        testPlayer.buildRoad(testPlayer, testPositionStart, testPositionEnd);
        Assertions.assertEquals(testBoard.getEdge(testPositionStart, testPositionEnd).getEndingAt(), testRoad.getEndingAt());
        Assertions.assertEquals(testBoard.getEdge(testPositionStart, testPositionEnd).getStartingAt(), testRoad.getStartingAt());
    }

    @Test
    void getInitialRessources() {
        testBoard = new SiedlerBoard();
        Player blue = new Player(Config.Faction.BLUE);
        Player red = new Player(Config.Faction.RED);
        Point one = new Point(6,6);
        Point two = new Point(10,6);
        Point three = new Point(8,12);
        Point four = new Point(5,15);
        testBoard.setCorner(one, new Settlement(one,blue));
        testBoard.setCorner(two, new Settlement(two,blue));
        testBoard.setCorner(three, new Settlement(three,red));
        testBoard.setCorner(four, new Settlement(four,red));
        PlayGame playGame = new PlayGame();
        playGame.giveResourcesAfterFirstPhase(testBoard);

        ResourceStock resourceStockBlue = new ResourceStock();
        resourceStockBlue.add(Config.Resource.WOOD,1);
        resourceStockBlue.add(Config.Resource.WOOL,1);
        resourceStockBlue.add(Config.Resource.WOOL,1);
        resourceStockBlue.add(Config.Resource.STONE,1);
        resourceStockBlue.add(Config.Resource.GRAIN,1);
        resourceStockBlue.add(Config.Resource.WOOD,1);

        ResourceStock resourceStockRed = new ResourceStock();
        resourceStockRed.add(Config.Resource.STONE,1);
        resourceStockRed.add(Config.Resource.WOOD,1);
        resourceStockRed.add(Config.Resource.STONE,1);
        resourceStockRed.add(Config.Resource.WOOL,1);
        resourceStockRed.add(Config.Resource.GRAIN,1);

        Assertions.assertEquals(blue.getResourcesInPossession(), resourceStockBlue.getResources());
        Assertions.assertEquals(red.getResourcesInPossession(), resourceStockRed.getResources());
    }
}