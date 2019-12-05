package test;

import ch.zhaw.catan.Dice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiceTest {

    @Test
    void roll() {
        for (int index = 0; index <= 100; index++) {
            Assertions.assertTrue(Dice.roll() >= 1 && Dice.roll() <= 12);
        }
    }
}