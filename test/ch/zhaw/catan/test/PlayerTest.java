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
    void getRoadsBuilt() {
    }

    @Test
    void buildRoad() {
        testBoard = new SiedlerBoard();
        Point testPositionStart = new Point(6, 12);
        Point testPositionEnd = new Point(6, 20);
        Settlement testSettlement = new Settlement(testPositionStart, testPlayer);
        Road testRoad = new Road(testPositionStart, testPositionEnd);
        testPlayer.addResources(Config.Resource.GRAIN, 1);
        testPlayer.addResources(Config.Resource.WOOD, 2);
        testPlayer.addResources(Config.Resource.WOOL, 1);
        testPlayer.addResources(Config.Resource.CLAY, 2);
        testPlayer.buildSettlement(testPositionStart, testPlayer, testBoard);
        testPlayer.buildRoad(testPositionStart, testPositionEnd);
        //Assertions.assertEquals(testBoard.getEdge(testPositionStart, testPositionEnd), testRoad.getFaction());
    }
}