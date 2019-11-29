package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;

import java.awt.*;
import java.util.ArrayList;


public class Player {
    private ArrayList<ResourceCard> resourcesInPossession;
    private ArrayList<Settlement> settlementsBuilt;
    private ArrayList<Road> roadsBuilt;
    private Faction faction;

    public Player(Faction faction) {
        resourcesInPossession = new ArrayList<>();
        settlementsBuilt = new ArrayList<>();
        roadsBuilt = new ArrayList<>();
        this.faction = faction;
    }

    public ArrayList<ResourceCard> getResourcesInPossession() {
        return resourcesInPossession;
    }

    public void removeResources(Config.Resource resource, int ResourceCount) {
        int resourceCounter = 0;
        ArrayList<Integer> resourcesToRemove = new ArrayList<>();

        for (ResourceCard card: resourcesInPossession) {
            if (card.getResourceType() == resource){
                resourceCounter ++;
                resourcesToRemove.add(resourcesInPossession.indexOf(card));
            }
        }
        if (resourceCounter >= ResourceCount) {
            for (Integer cardToRemove: resourcesToRemove) {
                resourcesInPossession.remove(cardToRemove);
            }
        }
    }

    public ArrayList<Settlement> getSettlementsBuilt() {
        return settlementsBuilt;
    }

    public void buildSettlement(Point position) {
        settlementsBuilt.add(new Settlement(position));
    }

    public Faction getFaction() {
        return faction;
    }

    public ArrayList<Road> getRoadsBuilt() {
        return roadsBuilt;
    }

    public void buildRoad(Point startingAt, Point endingAt) {
        roadsBuilt.add(new Road(startingAt, endingAt));
    }
}
