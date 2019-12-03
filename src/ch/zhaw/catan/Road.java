package ch.zhaw.catan;

import java.awt.Point;

public class Road {
    private Point startingAt;
    private Point endingAt;

    public Road(Point startingAt, Point endingAt) {
        this.startingAt = startingAt;
        this.endingAt = endingAt;
    }

    public Point getStartingAt() {
        return startingAt;
    }

    public Point getEndingAt() {
        return endingAt;
    }

}
