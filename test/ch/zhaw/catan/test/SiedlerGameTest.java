package test;

import ch.zhaw.catan.SiedlerGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SiedlerGameTest {

    private SiedlerGame siedlergame;

    @BeforeEach
    void setUp() {
        siedlergame = new SiedlerGame(20, 4);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void switchToNextPlayer() {
        siedlergame.switchToNextPlayer();
        Assertions.assertEquals(siedlergame.getPlayer().get(1), siedlergame.getCurrentPlayer());
    }

    @Test
    void switchToPreviousPlayer() {
        siedlergame.switchToPreviousPlayer();
        Assertions.assertEquals(siedlergame.getPlayer().get(3), siedlergame.getCurrentPlayer());
    }

    @Test
    void getPlayer() {
        Assertions.assertEquals("rr", siedlergame.getPlayer().get(0).getFaction().toString());
        Assertions.assertEquals("bb", siedlergame.getPlayer().get(1).getFaction().toString());
        Assertions.assertEquals("gg", siedlergame.getPlayer().get(2).getFaction().toString());
        Assertions.assertEquals("yy", siedlergame.getPlayer().get(3).getFaction().toString());
    }

    @Test
    void getBoard() {
    }

    @Test
    void getCurrentPlayer() {
        Assertions.assertEquals("rr", siedlergame.getCurrentPlayer().getFaction().toString());
    }

    @Test
    void getCurrentPlayerResourceStock() {
    }

    @Test
    void placeInitialSettlement() {
    }

    @Test
    void throwDice() {
    }

    @Test
    void placeCity() {
    }

    @Test
    void tradeWithBankFourToOne() {
    }

    @Test
    void getWinner() {
        //siedlergame.getPlayer().get(0).addSettlement();
    }

    @Test
    void createPlayers() {
        siedlergame = new SiedlerGame(20, 1);
        Assertions.assertEquals(2, siedlergame.createPlayers(1));
        siedlergame = new SiedlerGame(20, 5);
        Assertions.assertEquals(4, siedlergame.createPlayers(5));
    }

    @Test
    void isPointACorner() {
    }

    @Test
    void placeRoad() {
    }
}