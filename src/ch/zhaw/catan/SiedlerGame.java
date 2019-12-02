package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.HexBoard;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.List;
import java.util.*;

public class SiedlerGame {
    private int winPoints;
    private List<Player> players = new ArrayList<>();
    private SiedlerBoard siedlerBoard = new SiedlerBoard();
    private int currentPlayer = 0;
    private Config config = new Config();
    private HexBoard hexBoard;
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private UI ui = new UI();

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
        int resourceCounter = 0;
        for (int index = 0; index <= players.get(currentPlayer).getResourcesInPossession().size(); index++)
            if (players.get(currentPlayer).getResourcesInPossession().get(index).getResourceType() == resource) {
                resourceCounter++;
            }
        return resourceCounter;
    }

    public void placeInitialSettlement(Point position) {
        boolean trying = true;
        while (trying) {
            if (hexBoard.getNeighboursOfCorner(position).isEmpty()) {
                players.get(currentPlayer).buildSettlement(position);
                trying = false;
            } else {
                int x = textIO.newIntInputReader().read("Can't place here cuz of other settlements, try again with another x coordinate");
                textTerminal.printf(System.lineSeparator());
                int y = textIO.newIntInputReader().read("Can't place here cuz of other settlements, try again with another y coordinate");
                textTerminal.printf(System.lineSeparator());
                position = new Point(x, y);
            }
        }
    }

    public void placeInitialRoad(Point roadStart, Point roadEnd) {
        players.get(currentPlayer).buildRoad(roadStart, roadEnd);
    }

    public Map<Faction, List<Resource>> throwDice(int dicethrow) {
        TreeMap<Faction, List<Resource>> resourceMap = new TreeMap<Faction, List<Resource>>();
        TreeMap<Integer, Point> temporaryMap = new TreeMap<Integer, Point>();
        Integer index = 0;
        for (Map.Entry<Point, Integer> diceRolledField : config.getStandardDiceNumberPlacement().entrySet()) {
            if (Objects.equals(dicethrow, diceRolledField.getValue())) {
                temporaryMap.put(index, diceRolledField.getKey());
                index++;
            }
        }
        for (Map.Entry<Point, Config.Land> correspondingLand : config.getStandardLandPlacement().entrySet()) {
            // TODO: compare which corresponding Land lies on the Point values in temporaryMap
        }
        // TODO: compare what settlements are on the dice rolled fields and save corresponding factions and their resources in resourceMap

        return resourceMap;
    }

    public boolean placeSettlement(Point position) {
        boolean trying = true;
        while (trying) {
            if (hexBoard.getNeighboursOfCorner(position).isEmpty()) {
                players.get(currentPlayer).buildSettlement(position);
                trying = false;
            } else {
                int x = textIO.newIntInputReader().read("Can't place here cuz of other settlements, try again with another x coordinate");
                textTerminal.printf(System.lineSeparator());
                int y = textIO.newIntInputReader().read("Can't place here cuz of other settlements, try again with another y coordinate");
                textTerminal.printf(System.lineSeparator());
                Point point = new Point(x, y);
                position = point;
            }
        }
        return true;
    }

    public boolean placeCity(Point position) { //TODO: test and bugfix

        boolean settlementFound = false;
        for (Settlement settlementToUpgrade : players.get(currentPlayer).getSettlementsBuilt()) {
            if (settlementToUpgrade.getPosition() == position) {
                settlementToUpgrade.setToCity();
                settlementFound = true;
                break;
            }
        }
        return settlementFound;
    }

    public boolean placeRoad(Point roadStart, Point roadEnd) {

        players.get(currentPlayer).buildRoad(roadStart, roadEnd);
        return false; // TODO: check if road is already occupied and return true/false
    }

    public boolean tradeWithBankFourToOne(Resource offer, Resource want) { //TODO: test and bugfix
        if (!players.get(currentPlayer).removeResources(offer, 4)) {
            return false;
        } else {
            players.get(currentPlayer).addResources(want, 1);
            return true;
        }
    }

    public Faction getWinner() { //TODO: test and bugfix
        int winPointCounter = 0;
        for (Settlement settlement : players.get(currentPlayer).getSettlementsBuilt()) {
            winPointCounter += settlement.getWinPoints();
        }
        if (winPointCounter >= winPoints) {
            return players.get(currentPlayer).getFaction();
        } else {
            return null;
        }
    }


    public void createPlayers(int numberOfPlayers) {
        if (numberOfPlayers < Config.MIN_NUMBER_OF_PLAYERS) {
            numberOfPlayers = Config.MIN_NUMBER_OF_PLAYERS;
        } else if (numberOfPlayers > Faction.values().length) {
            numberOfPlayers = Faction.values().length;
        }

        int counter = 0;
        for (Faction faction : Faction.values()) {
            if (counter++ >= numberOfPlayers) break;
            players.add((new Player(faction)));
        }
    }

    public Point isPointACorner(Point point) {
        boolean running;
        do {
            if (HexBoard.isCornerCoordinate(point)) {
                running = false;
            } else {
                textTerminal.print("Error this point is not on a corner, please try again");
                int x = textIO.newIntInputReader().read("Try again with a new x coordinate");
                int y = textIO.newIntInputReader().read("Try again with a new y coordinate");
                point = new Point(x, y);
                running = true;
            }


        } while (running);
        return point;
    }

    public boolean arePointsAnEdge(Point p1, Point p2){
        boolean running;
        do {
            if(hexBoard.hasEdge(p1,p2)){
                running = false;
            } else{
                textTerminal.print("Error this points are not on an edge, please try again");
                int a = textIO.newIntInputReader().read("Try again with a new x coordinate for roadstart");
                int b = textIO.newIntInputReader().read("Try again with a new y coordinate for roadstart");
                p1 = new Point(a, b);
                textTerminal.print("Error this points are not on an edge, please try again");
                int x = textIO.newIntInputReader().read("Try again with a new x coordinate for roadend");
                int y = textIO.newIntInputReader().read("Try again with a new y coordinate for roadend");
                p2 = new Point(x, y);
                running = false;
            }
        } while(running);
        return true;
    }
}