package ch.zhaw.catan;

import java.util.Random;

public class Dice {
    private int max = 12;
    private int min = 1;
    private Random roll = new Random();

    public int roll() {
        return (roll.nextInt((max - min) + 1) + min);
    }
}