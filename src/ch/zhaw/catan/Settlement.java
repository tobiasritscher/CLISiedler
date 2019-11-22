package ch.zhaw.catan;

import java.awt.*;

public class Settlement {
    private int winPoints = 1;
    private boolean isCity = false;
    private Point position;

    public Settlement (Point position){
        this.position = position;
    }

    public void setToCity() {
        winPoints = 2;
        isCity = true;
    }

    public Point getPosition(){
        return position;
    }
}
