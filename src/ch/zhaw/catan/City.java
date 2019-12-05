package ch.zhaw.catan;

import java.awt.*;

public class City extends Settlement {

    // TODO: Migrate isCity boolean to seperate City objects
    private int winPoints = 2;

    public City(Point position, Player player) {
        super(position, player);
    }
}
