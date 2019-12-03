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
    private int currentPlayer = 0;
    private Config config = new Config();
    private SiedlerBoard hexBoard = new SiedlerBoard();
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
        return hexBoard;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public int getCurrentPlayerResourceStock(Resource resource) {
        return players.get(currentPlayer).getResourcesInPossession().get(resource);
    }

    public Settlement placeInitialSettlement(Point position) {
        Settlement settlement = null;
        boolean trying;

        do {
            if (hexBoard.getNeighboursOfCorner(position).isEmpty() && hexBoard.hasCorner(position)) {
                settlement = new Settlement(position, players.get(currentPlayer));
                players.get(currentPlayer).addSettlement(settlement);
                trying = false;
            } else {
                int x = textIO.newIntInputReader().read("Can't place here, try again with another x coordinate");
                textTerminal.printf(System.lineSeparator());
                int y = textIO.newIntInputReader().read("Can't place here, try again with another y coordinate");
                textTerminal.printf(System.lineSeparator());
                position = new Point(x, y);
                trying = true;
            }
        } while (trying);
        return settlement;
    }

    public Map<Faction, List<Resource>> throwDice(int dicethrow) {
        TreeMap<Faction, List<Resource>> resourceMap = new TreeMap<>();
        TreeMap<Integer, Point> temporaryMap = new TreeMap<>();
        Integer index = 0;
        for (Map.Entry<Point, Integer> diceRolledField : Config.getStandardDiceNumberPlacement().entrySet()) {
            if (Objects.equals(dicethrow, diceRolledField.getValue())) {
                temporaryMap.put(index, diceRolledField.getKey());
                index++;
            }
        }
        for (Map.Entry<Point, Config.Land> correspondingLand : Config.getStandardLandPlacement().entrySet()) {
            // TODO: compare which corresponding Land lies on the Point values in temporaryMap
        }
        // TODO: compare what settlements are on the dice rolled fields and save corresponding factions and their resources in resourceMap

        return resourceMap;
    }

    public void placeSettlement(Point position) {
        boolean trying = true;
        while (trying) {
            if (hexBoard.getNeighboursOfCorner(position).isEmpty()) {
                players.get(currentPlayer).buildSettlement(position, players.get(currentPlayer));
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

    public boolean tradeWithBankFourToOne(Resource offer, Resource want) { //TODO: test and bugfix
        boolean result = false;
        Bank bank = new Bank();
        if (bank.trade(offer, want)) {
            if (players.get(currentPlayer).removeResources(offer, 4)) {
                players.get(currentPlayer).addResources(want, 1);
                result = true;
            }
        }
        return result;
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

    public void placeRoad(Point roadStart, Point roadEnd) {
        boolean running;
        do {
            if (hexBoard.hasEdge(roadStart, roadEnd)) {
                running = false;
            } else {
                textTerminal.print("Error this points are not on an edge, please try again");
                int a = textIO.newIntInputReader().read("Try again with a new x coordinate for roadstart");
                int b = textIO.newIntInputReader().read("Try again with a new y coordinate for roadstart");
                roadStart = new Point(a, b);
                int x = textIO.newIntInputReader().read("Try again with a new x coordinate for roadend");
                int y = textIO.newIntInputReader().read("Try again with a new y coordinate for roadend");
                roadEnd = new Point(x, y);
                running = true;
            }
        } while (running);
        players.get(currentPlayer).buildRoad(roadStart, roadEnd);
    }
}