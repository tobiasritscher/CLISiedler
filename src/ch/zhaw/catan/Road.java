package ch.zhaw.catan;

import java.awt.Point;

public class Road {
    private Point startingAt;
    private Point endingAt;
    private Player player;

    public Road(Player player, Point startingAt, Point endingAt) {
        this.startingAt = startingAt;
        this.endingAt = endingAt;
        this.player = player;
    }

    public Point getStartingAt() {
        return startingAt;
    }

    public Point getEndingAt() {
        return endingAt;
    }

    @Override
    public String toString() {
        return player.getFaction().toString().toUpperCase().substring(0,1);
    }

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (!(other instanceof Road))return false;
        Road road = (Road)other;
        boolean result = false;
        if (road.startingAt == startingAt && road.endingAt == endingAt && road.player.equals(player))
            result = true;
        return result;
    }
}
