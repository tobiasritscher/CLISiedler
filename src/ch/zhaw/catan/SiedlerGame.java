package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SiedlerGame {
    private Dice dice = new Dice();
    private int winPoints;
    private List<Player> players = new ArrayList<>();
    private SiedlerBoard siedlerBoard = new SiedlerBoard();
    private int currentPlayer = 0;

    public SiedlerGame(int winPoints, int players) {
        createPlayers(players);
        this.winPoints = winPoints;
    }


    public void switchToNextPlayer() {
        // TODO: Implement
        if (currentPlayer <= 3) {
            currentPlayer++;
        } else if (currentPlayer == 4) {
            currentPlayer = 1;
        }
    }

    public void switchToPreviousPlayer() {
        // TODO: Implement
        if (currentPlayer >= 2) {
            currentPlayer--;
        } else if (currentPlayer == 1) {
            currentPlayer = 4;
        }
    }

    public List<Player> getPlayer() {
        return players;
    }

    public SiedlerBoard getBoard() {
        return siedlerBoard;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public int getCurrentPlayerResourceStock(Resource resource) {
        // TODO: Implement
        return 0;
    }

    public boolean placeInitialSettlement(Point position, boolean payout) {
        players.get(currentPlayer).buildSettlement(position);
        return false;
    }

    public boolean placeInitialRoad(Point roadStart, Point roadEnd) {
        players.get(currentPlayer).buildRoad(roadStart, roadEnd);
        return false;
    }

    public Map<Faction, List<Resource>> throwDice(int dicethrow) {
        // TODO: Implement
        // TODO: how to call: siedlerGame.throwDice(dice.roll());


        return null;
    }

    public boolean buildSettlement(Point position) {
        players.get(currentPlayer).buildSettlement(position);
        return true; // TODO: check if settlement is already occupied and return true/false
    }

    public boolean buildCity(Point position) {

        // players.get(currentPlayer).getSettlementsBuilt() TODO: Make that work via .getSettlementsBuilt(number of settlement).getPosition(position).setToCity();

        return true; // TODO: check if settlement is already occupied, city already built and return true/false
    }

    public boolean buildRoad(Point roadStart, Point roadEnd) {
        players.get(currentPlayer).buildRoad(roadStart, roadEnd);
        return false; // TODO: check if road is already occupied and return true/false
    }

    public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
        players.get(currentPlayer).removeResources(offer, 4);
        return false;
    }

    public Faction getWinner() {
        // TODO: Implement
        return null;
    }

    public void createPlayers(int numberOfPlayers) {
        if (numberOfPlayers < Config.MIN_NUMBER_OF_PLAYERS) {
            numberOfPlayers = Config.MIN_NUMBER_OF_PLAYERS;
        } else if (numberOfPlayers > Faction.values().length) {
            numberOfPlayers = Faction.values().length;
        }

        int counter = 0;
        for (Faction fac : Faction.values()) {
            if (counter++ >= numberOfPlayers) break;
            players.add((new Player(fac)));
        }
    }

}
