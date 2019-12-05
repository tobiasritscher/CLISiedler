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

    public Settlement getSettlementAtPosition(Point point) {
        Settlement result = null;
        for (Settlement settlement : settlementsBuilt) {
            if (settlement.getPosition().equals(point))
                result = settlement;
        }
        return result;
    }

    public ArrayList<Point> getSettlementsBuiltPoints() {
        ArrayList<Point> points = new ArrayList<>();
        for (Settlement settlements : settlementsBuilt) {
            points.add(settlements.getPosition());
        }
        return points;
    }

    public void addSettlement(Settlement settlement) {
        settlementsBuilt.add(settlement);
    }

    public Faction getFaction() {
        return faction;
    }

    public ArrayList<Road> getRoadsBuilt() {
        return roadsBuilt;
    }

    public void buildRoad(Player player, Point startingAt, Point endingAt) {
        roadsBuilt.add(new Road(player, startingAt, endingAt));
    }

    @Override
    public String toString() {
        return faction.toString().toUpperCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof Player)) return false;
        Player player = (Player) other;
        boolean result = false;
        if (player.faction == faction)
            result = true;
        return result;
    }
}
