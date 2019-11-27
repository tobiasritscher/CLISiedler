package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.HexBoard;

import java.awt.Point;
import java.util.*;

public class SiedlerGame {
    private Dice dice = new Dice();
    private int winPoints;
    private ArrayList<Player> players;
    private Player playerBlue;
    private Player playerGreen;
    private Player playerRed;
    private Player playerYellow;

    public SiedlerGame(int winPoints, int players) {
        createPlayers(players);
        this.winPoints = winPoints;
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
        /**
         *   TODO: how to call: siedlerGame.throwDice(dice.roll());
          */


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

    private void createPlayers(int numberOfPlayers) {
        if (numberOfPlayers == 2) {
            playerBlue = new Player(Config.Faction.BLUE);
            playerGreen = new Player(Config.Faction.GREEN);
            players = new ArrayList<>();
            players.add(playerBlue);
            players.add(playerGreen);
        } else if (numberOfPlayers == 3) {
            playerBlue = new Player(Config.Faction.BLUE);
            playerGreen = new Player(Config.Faction.GREEN);
            playerRed = new Player(Config.Faction.RED);
            players = new ArrayList<>();
            players.add(playerBlue);
            players.add(playerGreen);
            players.add(playerRed);
        } else if (numberOfPlayers == 4) {
            playerBlue = new Player(Config.Faction.BLUE);
            playerGreen = new Player(Config.Faction.GREEN);
            playerRed = new Player(Config.Faction.RED);
            playerYellow = new Player(Config.Faction.YELLOW);
            players = new ArrayList<>();
            players.add(playerBlue);
            players.add(playerGreen);
            players.add(playerRed);
            players.add(playerYellow);
        } else {
            System.out.println("Error only between 2 and 4 players are allowed");
        }
    }
}
