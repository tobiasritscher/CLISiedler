package ch.zhaw.catan;

import java.awt.*;

public class Settlement {
    private int winPoints = 1;
    private boolean isCity = false;
    private Point position;
    private Player player;

    public Settlement(Point position, Player player) {
        this.position = position;
        this.player = player;
    }

    void setToCity() {
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

    @Override
    public String toString() {
        String result;
        if (isCity) {
            result = "C" + player.getFaction().toString().toUpperCase().substring(0, 1);
        } else {
            result = player.getFaction().toString().toUpperCase();
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Settlement)) return false;
        Settlement settlement = (Settlement) other;
        boolean result = false;
        if (settlement.position == position && settlement.player.equals(player))
            result = true;
        return result;
    }
}
