package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayGameTest {

    private PlayGame testGame = new PlayGame();

    @Test
    void getPlayer() {
        Player testPlayer = new Player(Config.Faction.RED);
        assertEquals(testPlayer.getFaction(), testGame.getPlayer(0).getFaction());
    }

    @Test
    void firstPhase() {

    }

    @Test
    void isCornerFree() {

    }

    @Test
    void giveResourcesAfterFirstPhase() {
        testGame.siedlerGameTestHelper();
        Point one = new Point(6, 6);
        Point two = new Point(10, 6);
        Point three = new Point(8, 12);
        Point four = new Point(5, 15);
        testGame.getBoard().setCorner(one, new Settlement(one, testGame.getPlayer(0)));
        testGame.getBoard().setCorner(two, new Settlement(two, testGame.getPlayer(1)));
        testGame.getBoard().setCorner(three, new Settlement(three, testGame.getPlayer(0)));
        testGame.getBoard().setCorner(four, new Settlement(four, testGame.getPlayer(0)));
        testGame.giveResourcesAfterFirstPhase(testGame.getBoard());

        ResourceStock resourceStockBlue = new ResourceStock();
        resourceStockBlue.add(Config.Resource.WOOD, 1);
        resourceStockBlue.add(Config.Resource.WOOL, 1);
        resourceStockBlue.add(Config.Resource.WOOL, 1);
        resourceStockBlue.add(Config.Resource.STONE, 1);
        resourceStockBlue.add(Config.Resource.WOOD, 1);

        ResourceStock resourceStockRed = new ResourceStock();
        resourceStockRed.add(Config.Resource.STONE, 1);
        resourceStockRed.add(Config.Resource.WOOD, 1);
        resourceStockRed.add(Config.Resource.STONE, 1);
        resourceStockRed.add(Config.Resource.WOOL, 1);
        resourceStockRed.add(Config.Resource.GRAIN, 1);

        assertEquals(resourceStockBlue.getResources(), testGame.getPlayer(1).getResourcesInPossession());
        assertEquals(resourceStockRed.getResources(), testGame.getPlayer(0).getResourcesInPossession());
    }

    @Test
    void printAllGivenRessourcesOfAllPlayers() {
    }

    @Test
    void secondPhase() {

    }
}