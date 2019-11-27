package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.HexBoard;

import java.awt.Point;
import java.util.*;

public class SiedlerGame {
    private Dice dice = new Dice();
    private Player playerBlue = new Player(Faction.BLUE); // TODO implement into SiedlerGame constructor
    private Player playerGreen = new Player(Faction.GREEN); // TODO implement into SiedlerGame constructor
    private Player playerRed = new Player(Faction.RED); // TODO implement into SiedlerGame constructor
    private Player playerYellow = new Player(Faction.YELLOW); // TODO implement into SiedlerGame constructor

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

        // TODO: It would go something like this:
        playerBlue.buildSettlement(position);
        return false;
    }

    public boolean buildCity(Point position) {
        // TODO: OPTIONAL task - Implement

        // TODO: It would go something like this:
        playerBlue.getSettlementsBuilt().get(0).setToCity(); // TODO doesn't work that way yet, help me out
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
