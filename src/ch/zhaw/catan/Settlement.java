package ch.zhaw.catan;

import java.awt.Point;

public class Settlement {
    private int winPoints = 1;
    private boolean isCity = false;
    private Point position;
    private Player player;

    public Settlement(Point position, Player player) {
        this.position = position;
        this.player = player;
    }

    public void setToCity() {
        winPoints = 2;
        isCity = true;
    }

    public Point getPosition() {
        return position;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean getIsCity() {
        return isCity;
    }

    public int getWinPoints() {
        return winPoints;
    }

    public String getFaction() {
        return player.getFaction().toString().toUpperCase();
    }
}
