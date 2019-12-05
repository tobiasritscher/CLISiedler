package ch.zhaw.catan;

import java.awt.*;

public class City extends Settlement {

    // TODO: Migrate isCity boolean to seperate City objects

    public City(Point position, Player player) {
        super(position, player);
        super.setToCity(); // TODO: May be deleted
    }

}
