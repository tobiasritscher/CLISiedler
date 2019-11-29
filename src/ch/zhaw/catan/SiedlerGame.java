package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.HexBoard;
import java.awt.Point;
import java.util.*;

public class SiedlerGame {
    private Dice dice = new Dice();
    private int winPoints;
    private ArrayList<Player> players = new ArrayList<>();
    private int currentPlayer = 0;

    public SiedlerGame(int winPoints, int players) {
        createPlayers(players);
        this.winPoints = winPoints;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void switchToNextPlayer() {
        // TODO: Implement
        if(currentPlayer <= 3 ){
            currentPlayer++;
        }else if(currentPlayer == 4){
            currentPlayer = 1;
        }
    }

    public void switchToPreviousPlayer() {
        // TODO: Implement
        if(currentPlayer >= 2){
            currentPlayer--;
        }else if(currentPlayer == 1){
            currentPlayer = 4;
        }
    }

    public List<Player> getPlayer() {
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
//        playerBlue.buildSettlement(position);
        return false;
    }

    public boolean buildCity(Point position) {
        // TODO: OPTIONAL task - Implement

        // TODO: It would go something like this:
//        playerBlue.getSettlementsBuilt().get(0).setToCity(); // TODO doesn't work that way yet, help me out
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
            players.add(new Player(Config.Faction.BLUE));
            players.add(new Player(Config.Faction.GREEN));
        } else if (numberOfPlayers == 3) {
            players.add(new Player(Config.Faction.BLUE));
            players.add(new Player(Config.Faction.GREEN));
            players.add(new Player(Config.Faction.RED));
        } else if (numberOfPlayers == 4) {
            players.add(new Player(Config.Faction.BLUE));
            players.add(new Player(Config.Faction.GREEN));
            players.add(new Player(Config.Faction.RED));
            players.add(new Player(Config.Faction.YELLOW));
        } else {
            System.out.println("Error only between 2 and 4 players are allowed");
        }
    }

}
