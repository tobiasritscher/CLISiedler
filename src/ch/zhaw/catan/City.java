package ch.zhaw.catan;

import java.awt.*;

public class City extends Settlement {

    public City(Point position, Player player) {
        super(position, player);
        super.setToCity();
    }

}
