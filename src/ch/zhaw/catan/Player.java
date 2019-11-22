package ch.zhaw.catan;

import java.util.ArrayList;

public class Player {
    private ArrayList<Card> cardsInPossession;
    private String color;

    public Player(String color) {
        cardsInPossession = new ArrayList<>();
        this.color = color;
    }

    public ArrayList<Card> getCardsInPossession() {
        return cardsInPossession;
    }

    public String getColor() {
        return color;
    }
}
