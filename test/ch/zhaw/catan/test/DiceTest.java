package test;

import ch.zhaw.catan.Dice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DiceTest {

    @Test
    void roll() {
        for (int index = 0; index <= 100; index++) {
            assertTrue(Dice.roll() >= 1 && Dice.roll() <= 12);
        }
    }
}