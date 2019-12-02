package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;


import java.awt.*;
import java.util.ArrayList;
import java.util.Map;


public class Player {
    private ResourceStock resourcesInPossession;
    private ArrayList<Settlement> settlementsBuilt;
    private ArrayList<Road> roadsBuilt;
    private Faction faction;

    public Player(Faction faction) {
        resourcesInPossession = new ResourceStock();
        settlementsBuilt = new ArrayList<>();
        roadsBuilt = new ArrayList<>();
        this.faction = faction;
    }

    public Map<Resource, Integer> getResourcesInPossession() {
        return resourcesInPossession.getResources();
    } //TODO: test and bugfix

    public boolean removeResources(Resource resource, int resourceCount) {
        return resourcesInPossession.remove(resource, resourceCount);
    }

    public void addResources(Resource resource, int resourceCount) { //TODO: test and bugfix
        resourcesInPossession.add(resource, resourceCount);
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
