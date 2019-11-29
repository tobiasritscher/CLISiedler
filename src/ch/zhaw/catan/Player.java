package ch.zhaw.catan;

import java.awt.Point;
import java.util.ArrayList;

import ch.zhaw.catan.Config.Faction;


public class Player {
    private ArrayList<Card> cardsInPossession;
    private ArrayList<Settlement> settlementsBuilt;
    private ArrayList<Road> roadsBuilt;
    private Faction faction;

    public Player(Faction faction) {
        cardsInPossession = new ArrayList<>();
        settlementsBuilt = new ArrayList<>();
        roadsBuilt = new ArrayList<>();
        this.faction = faction;
    }

    public ArrayList<Card> getCardsInPossession() {
        return cardsInPossession;
    }

    public ArrayList<Settlement> getSettlementsBuilt() {
        return settlementsBuilt;
    }

    public void buildSettlement(Point position) {
        settlementsBuilt.add(new Settlement(position));
    }

    public String getFaction() {
        return String.valueOf(faction);
    }

    public ArrayList<Road> getRoadsBuilt() {
        return roadsBuilt;
    }

    public void buildRoad(Point startingAt, Point endingAt) {
        roadsBuilt.add(new Road(startingAt, endingAt));
    }
}
