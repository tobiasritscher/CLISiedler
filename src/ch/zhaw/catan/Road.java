package ch.zhaw.catan;

import java.awt.Point;

public class Road {
    Point position;
    Point startingAt;
    Point endingAt;

    public Road(Point position, Point startingAt, Point endingAt) {
        this.position = position;
        this.startingAt = startingAt;
        this.endingAt = endingAt;
    }

    public Point getPosition() {
        return position;
    }

    public Point getStartingAt() { return startingAt; }

    public Point getEndingAt() { return endingAt; }

}
