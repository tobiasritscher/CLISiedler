package ch.zhaw.catan;

import java.util.Random;

public class Dice {

    public static int roll() {

        Random roll = new Random();

        int dice1 = roll.nextInt(6) + 1;
        int dice2 = roll.nextInt(6) + 1;
        return dice1 + dice2;

    }
}