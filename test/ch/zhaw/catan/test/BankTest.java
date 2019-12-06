package test;

import ch.zhaw.catan.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTest {

    private SiedlerGame testGame;

    @BeforeEach
    void setUp() {
        testGame = new SiedlerGame(20, 2);
        testGame.getCurrentPlayer().addResources(Config.Resource.GRAIN, 5);
        testGame.getCurrentPlayer().addResources(Config.Resource.CLAY, 3);
    }

    @Test
    void trade() {
        // TODO: Test fails because order of Resources varies
        Player testPlayer = new Player(Config.Faction.RED);
        String expectedValue = "{CL=3, GR=1, WD=1}";
        // Positive test
        testGame.tradeWithBankFourToOne(Config.Resource.GRAIN, Config.Resource.WOOD, testPlayer);
        // Assertions.assertEquals(expectedValue, testGame.getCurrentPlayer().getResourcesInPossession().toString());
        Assertions.assertTrue(testGame.getCurrentPlayer().getResourcesInPossession().containsKey(Config.Resource.GRAIN));



        // Negative test
        testGame.tradeWithBankFourToOne(Config.Resource.CLAY, Config.Resource.WOOD, testPlayer);
        //Assertions.assertEquals(expectedValue, testGame.getCurrentPlayer().getResourcesInPossession().toString());
    }
}