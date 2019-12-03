package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.HexBoard;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;


import java.awt.*;
import java.util.ArrayList;
import java.util.Map;


public class Player {
    private ResourceStock resourcesInPossession;
    private ArrayList<Settlement> settlementsBuilt;
    private ArrayList<Road> roadsBuilt;
    private Faction faction;
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private UI ui = new UI();
    private HexBoard hexBoard = new HexBoard();

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

    public void addSettlement(Settlement settlement) {
        settlementsBuilt.add(settlement);
    }

    public Settlement buildSettlement(Point position, Player player) {
        Settlement settlement = new Settlement(position, player);

        if (resourcesInPossession.available(Resource.WOOL, 1) && resourcesInPossession.available(Resource.CLAY, 1) && resourcesInPossession.available(Resource.WOOD, 1) && resourcesInPossession.available(Resource.GRAIN, 1)) {
            if (hexBoard.getNeighboursOfCorner(position).isEmpty()) {
                settlementsBuilt.add(settlement);
                removeResources(Resource.WOOL, 1);
                removeResources(Resource.CLAY, 1);
                removeResources(Resource.WOOD, 1);
                removeResources(Resource.GRAIN, 1);
            } else {
                textTerminal.print("There's a settlement nearby! You can't place your settlement there");
            }
        } else {
            textTerminal.print("Not enough ressources to build a settlement!");
        }
        return settlement;

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
