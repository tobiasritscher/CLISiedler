package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.HexBoard;
import java.awt.Point;
import java.util.*;

public class SiedlerGame {
    private Dice dice = new Dice();
    private int winPoints;
    private List<Faction> players = new ArrayList<Faction>();
    private SiedlerBoard siedlerBoard = new SiedlerBoard();
    private int currentPlayer = 0;

    public SiedlerGame(int winPoints, int players) {
        createPlayers(players);
        this.winPoints = winPoints;
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

    public List<Faction> getPlayer() {
        // TODO: Implement
        return players;
    }

    public SiedlerBoard getBoard() {
        // TODO: Implement
        return siedlerBoard;

        return siedlerBoard;
    }

    public Faction getCurrentPlayer() {
        return players.get(currentPlayer).getFaction();
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
        // TODO: Implement
        return false;
    }

    public Faction getWinner() {
        // TODO: Implement
        return null;
    }

    private void createPlayers(int numberOfPlayers) {
//        if (numberOfPlayers == 2) {
//            players.add(new Player(Config.Faction.BLUE));
//            players.add(new Player(Config.Faction.GREEN));
//        } else if (numberOfPlayers == 3) {
//            players.add(new Player(Config.Faction.BLUE));
//            players.add(new Player(Config.Faction.GREEN));
//            players.add(new Player(Config.Faction.RED));
//        } else if (numberOfPlayers == 4) {
//            players.add(new Player(Config.Faction.BLUE));
//            players.add(new Player(Config.Faction.GREEN));
//            players.add(new Player(Config.Faction.RED));
//            players.add(new Player(Config.Faction.YELLOW));
//        } else {
//            System.out.println("Error only between 2 and 4 players are allowed");
//        }

        if (numberOfPlayers < Config.MIN_NUMBER_OF_PLAYERS) {
            numberOfPlayers = Config.MIN_NUMBER_OF_PLAYERS;
        } else if (numberOfPlayers > Faction.values().length) {
            numberOfPlayers = Faction.values().length;
        }

        for (int i = 0; i < numberOfPlayers; i++) {
            Faction player = Faction.values()[i];
            this.players.add(player);
        }

    }

}
