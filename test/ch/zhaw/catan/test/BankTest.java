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
        String expectedValue = "{GR=1, WD=1, CL=3}";
        // Positive test
        testGame.tradeWithBankFourToOne(Config.Resource.GRAIN, Config.Resource.WOOD);
        Assertions.assertEquals(expectedValue, testGame.getCurrentPlayer().getResourcesInPossession().toString());

        // Negative test
        testGame.tradeWithBankFourToOne(Config.Resource.CLAY, Config.Resource.WOOD);
        Assertions.assertEquals(expectedValue, testGame.getCurrentPlayer().getResourcesInPossession().toString());
    }
}