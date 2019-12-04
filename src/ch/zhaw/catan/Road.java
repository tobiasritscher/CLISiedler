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
}
