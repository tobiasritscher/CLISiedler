package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.awt.Point;
import java.util.List;
import java.util.Map;

public class SiedlerGame {
    private Dice dice = new Dice();

    public SiedlerGame(int winPoints, int players) {
        // TODO: Implement
    }

    public void switchToNextPlayer() {
        // TODO: Implement
    }

    public void switchToPreviousPlayer() {
        // TODO: Implement
    }

    public List<Faction> getPlayer() {
        // TODO: Implement
        return null;
    }

    public SiedlerBoard getBoard() {
        // TODO: Implement
        return null;
    }

    public Faction getCurrentPlayer() {
        // TODO: Implement
        return null;
    }

    public int getCurrentPlayerResourceStock(Resource resource) {
        // TODO: Implement
        return 0;
    }

    public boolean placeInitialSettlement(Point position, boolean payout) {
        // TODO: Implement
        return false;
    }

    public boolean placeInitialRoad(Point roadStart, Point roadEnd) {
        // TODO: Implement
        return false;
    }

    public Map<Faction, List<Resource>> throwDice(int dicethrow) {
        // TODO: Implement
        return null;
    }

    public boolean buildSettlement(Point position) {
        // TODO: Implement
        return false;
    }

    public boolean buildCity(Point position) {
        // TODO: OPTIONAL task - Implement
        return false;
    }

    public boolean buildRoad(Point roadStart, Point roadEnd) {
        // TODO: Implement
        return false;
    }

    public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
        // TODO: Implement
        return false;
    }

    public Faction getWinner() {
        // TODO: Implement
        return null;
    }
}
