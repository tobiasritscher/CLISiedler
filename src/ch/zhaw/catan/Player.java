package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Map;


public class Player {
    private ResourceStock resourcesInPossession;
    private ArrayList<Settlement> settlementsBuilt;
    private ArrayList<Road> roadsBuilt;
    private Faction faction;
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();

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

    public boolean buildSettlement(Point position, Player player, SiedlerBoard hexBoard) {
        Settlement settlement;
        boolean wrongAwnser;
        boolean result = false;

        if (resourcesInPossession.available(Resource.WOOL, 1)
                && resourcesInPossession.available(Resource.CLAY, 1)
                && resourcesInPossession.available(Resource.WOOD, 1)
                && resourcesInPossession.available(Resource.GRAIN, 1)) {
            do {
                settlement = new Settlement(position, player);
                if (hexBoard.getNeighboursOfCorner(position).isEmpty() && hexBoard.hasCorner(position)) {
                    settlementsBuilt.add(settlement);
                    hexBoard.setCorner(position, settlement);
                    removeResources(Resource.WOOL, 1);
                    removeResources(Resource.CLAY, 1);
                    removeResources(Resource.WOOD, 1);
                    removeResources(Resource.GRAIN, 1);
                    wrongAwnser = false;
                    result = true;
                } else {
                    int x = textIO.newIntInputReader().read("Can't place here, try again with another x coordinate");
                    textTerminal.printf(System.lineSeparator());
                    int y = textIO.newIntInputReader().read("Can't place here, try again with another y coordinate");
                    textTerminal.printf(System.lineSeparator());
                    position = new Point(x, y);
                    wrongAwnser = true;
                }
            } while(wrongAwnser);
        } else {
            textTerminal.print("Not enough ressources to build a settlement!");
        }
        return result;
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
