package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.Config.Land;
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
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private UI ui = new UI();
    private static Player player;

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

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public int getCurrentPlayerResourceStock(Resource resource) {
        return players.get(currentPlayer).getResourcesInPossession().get(resource);
    }

    public Settlement placeInitialSettlement(Point position, Player player, SiedlerBoard hexBoard) {
        Settlement settlement = null;
        boolean trying;

        do {
            if (validSettlementPlacing(position, hexBoard)) {
                settlement = new Settlement(position, player);
                player.addSettlement(settlement);
                trying = false;
            } else {
                int x = textIO.newIntInputReader().read("Can't place here, try again with another x coordinate");
                UI.newLine();
                int y = textIO.newIntInputReader().read("Can't place here, try again with another y coordinate");
                UI.newLine();
                position = new Point(x, y);
                trying = true;
            }
        } while (trying);
        hexBoard.setCorner(settlement.getPosition(), settlement);
        return settlement;
    }

    public void placeSettlement(Point position, Player player, SiedlerBoard hexBoard) {
        Settlement settlement = null;
        boolean trying;

        do {
            if (validSettlementPlacing(position, hexBoard)) {
                if (player.getResourcesInPossession().containsKey(Resource.CLAY)
                        && player.getResourcesInPossession().containsKey(Resource.WOOD)
                        && player.getResourcesInPossession().containsKey(Resource.WOOL)
                        && player.getResourcesInPossession().containsKey(Resource.GRAIN)) {
                    settlement = new Settlement(position, player);
                    player.addSettlement(settlement);
                    player.removeResources(Resource.CLAY, 1);
                    player.removeResources(Resource.WOOD, 1);
                    player.removeResources(Resource.WOOL, 1);
                    player.removeResources(Resource.GRAIN, 1);
                    hexBoard.setCorner(settlement.getPosition(), settlement);
                    trying = false;
                } else {
                    textTerminal.print("You don't have enough resources to build a settlement");
                    trying = false;
                }
            } else {
                int x = textIO.newIntInputReader().read("Can't place here, try again with another x coordinate");
                UI.newLine();
                int y = textIO.newIntInputReader().read("Can't place here, try again with another y coordinate");
                UI.newLine();
                position = new Point(x, y);
                trying = true;
            }
        } while (trying);
    }

    private boolean validSettlementPlacing(Point position, SiedlerBoard hexBoard) {
        return hexBoard.getNeighboursOfCorner(position).isEmpty() &&
                hexBoard.hasCorner(position) &&
                isCornerConnectedToLand(position, hexBoard);
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


    public boolean placeCity(Point position, Player player) { //TODO: test and bugfix

        boolean settlementFound = false;
        if (player.getSettlementsBuiltPoints().contains(position)) {
            Map<Resource, Integer> resources = player.getResourcesInPossession();
            if (resources.get(Resource.STONE) >= 3 && resources.get(Resource.GRAIN) >= 2) {
                player.getSettlementAtPosition(position).setToCity();
                settlementFound = true;
                player.removeResources(Resource.STONE, 3);
                player.removeResources(Resource.GRAIN, 2);
            } else {
                textTerminal.print("You do not have enough resources");
            }
        } else {
            textTerminal.print("You don't have a settlement to upgrade on this position: " + position);
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

    public void tradeWithBank(int i) {
        textTerminal.print("1: Wood\n");
        textTerminal.print("2: Stone\n");
        textTerminal.print("3: Grain\n");
        textTerminal.print("4: Clay\n");
        textTerminal.print("5: Wool\n");
        int x = textIO.newIntInputReader().read("What would you like to trade?\n");
        switch (x) {
            case 1:
                if (getPlayers().get(i).getResourcesInPossession().get(Resource.WOOD) >= 4) {
                    textTerminal.print("1: Wood\n");
                    textTerminal.print("2: Stone\n");
                    textTerminal.print("3: Grain\n");
                    textTerminal.print("4: Clay\n");
                    textTerminal.print("5: Wool\n");
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.WOOD, 4);
                    switch (y) {
                        case 1:
                            getPlayers().get(i).addResources(Resource.WOOD, 1);
                            break;
                        case 2:
                            getPlayers().get(i).addResources(Resource.STONE, 1);
                            break;
                        case 3:
                            getPlayers().get(i).addResources(Resource.GRAIN, 1);
                            break;
                        case 4:
                            getPlayers().get(i).addResources(Resource.CLAY, 1);
                            break;
                        case 5:
                            getPlayers().get(i).addResources(Resource.WOOL, 1);
                            break;
                        default:
                            textTerminal.print("Come on there are only 5 numbers...you can do this!\n");
                            break;
                    }

                } else {
                    textTerminal.print("You do not have enough Wood in your hood.\n");
                }
                break;

            case 2:
                if (getPlayers().get(i).getResourcesInPossession().get(Resource.STONE) >= 4) {
                    textTerminal.print("1: Wood\n");
                    textTerminal.print("2: Stone\n");
                    textTerminal.print("3: Grain\n");
                    textTerminal.print("4: Clay\n");
                    textTerminal.print("5: Wool\n");
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.WOOD, 4);
                    switch (y) {
                        case 1:
                            getPlayers().get(i).addResources(Resource.WOOD, 1);
                            break;
                        case 2:
                            getPlayers().get(i).addResources(Resource.STONE, 1);
                            break;
                        case 3:
                            getPlayers().get(i).addResources(Resource.GRAIN, 1);
                            break;
                        case 4:
                            getPlayers().get(i).addResources(Resource.CLAY, 1);
                            break;
                        case 5:
                            getPlayers().get(i).addResources(Resource.WOOL, 1);
                            break;
                        default:
                            textTerminal.print("Come on there are only 5 numbers...you can do this!\n");
                            break;
                    }

                } else {
                    textTerminal.print("You do not have enough Stone to bone.\n");
                }
                break;
            case 3:
                if (getPlayers().get(i).getResourcesInPossession().get(Resource.GRAIN) >= 4) {
                    textTerminal.print("1: Wood\n");
                    textTerminal.print("2: Stone\n");
                    textTerminal.print("3: Grain\n");
                    textTerminal.print("4: Clay\n");
                    textTerminal.print("5: Wool\n");
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.WOOD, 4);
                    switch (y) {
                        case 1:
                            getPlayers().get(i).addResources(Resource.WOOD, 1);
                            break;
                        case 2:
                            getPlayers().get(i).addResources(Resource.STONE, 1);
                            break;
                        case 3:
                            getPlayers().get(i).addResources(Resource.GRAIN, 1);
                            break;
                        case 4:
                            getPlayers().get(i).addResources(Resource.CLAY, 1);
                            break;
                        case 5:
                            getPlayers().get(i).addResources(Resource.WOOL, 1);
                            break;
                        default:
                            textTerminal.print("Come on there are only 5 numbers...you can do this!\n");
                            break;
                    }

                } else {
                    textTerminal.print("You do not have enough Grain to gain.\n");
                }
                break;
            case 4:
                if (getPlayers().get(i).getResourcesInPossession().get(Resource.CLAY) >= 4) {
                    textTerminal.print("1: Wood\n");
                    textTerminal.print("2: Stone\n");
                    textTerminal.print("3: Grain\n");
                    textTerminal.print("4: Clay\n");
                    textTerminal.print("5: Wool\n");
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.WOOD, 4);
                    switch (y) {
                        case 1:
                            getPlayers().get(i).addResources(Resource.WOOD, 1);
                            break;
                        case 2:
                            getPlayers().get(i).addResources(Resource.STONE, 1);
                            break;
                        case 3:
                            getPlayers().get(i).addResources(Resource.GRAIN, 1);
                            break;
                        case 4:
                            getPlayers().get(i).addResources(Resource.CLAY, 1);
                            break;
                        case 5:
                            getPlayers().get(i).addResources(Resource.WOOL, 1);
                            break;
                        default:
                            textTerminal.print("Come on there are only 5 numbers...you can do this!\n");
                            break;
                    }

                } else {
                    textTerminal.print("You do not have enough Clay to stay.\n");
                }
                break;
            case 5:
                if (getPlayers().get(i).getResourcesInPossession().get(Resource.WOOL) >= 4) {
                    textTerminal.print("1: Wood\n");
                    textTerminal.print("2: Stone\n");
                    textTerminal.print("3: Grain\n");
                    textTerminal.print("4: Clay\n");
                    textTerminal.print("5: Wool\n");
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.WOOD, 4);
                    switch (y) {
                        case 1:
                            getPlayers().get(i).addResources(Resource.WOOD, 1);
                            break;
                        case 2:
                            getPlayers().get(i).addResources(Resource.STONE, 1);
                            break;
                        case 3:
                            getPlayers().get(i).addResources(Resource.GRAIN, 1);
                            break;
                        case 4:
                            getPlayers().get(i).addResources(Resource.CLAY, 1);
                            break;
                        case 5:
                            getPlayers().get(i).addResources(Resource.WOOL, 1);
                            break;
                        default:
                            textTerminal.print("Come on there are only 5 numbers...you can do this!\n");
                            break;
                    }

                } else {
                    textTerminal.print("You do not have enough Wool you fool.\n");
                }
                break;
            default:
                textTerminal.print("Come on there are only 5 numbers...you can do this!\n");
                break;
        }

    }

    public boolean getWinner(Player player) { //TODO: test and bugfix
        int winPointCounter = 0;
        for (Settlement settlement : player.getSettlementsBuilt()) {
            if (settlement.getIsCity()) {
                winPointCounter += settlement.getWinPoints();
            } else {
                winPointCounter += settlement.getWinPoints();
            }
        }
        return winPointCounter >= winPoints;
    }


    public int createPlayers(int numberOfPlayers) {
        for (int i = 0; i < numberOfPlayers; ++i) {
            players.add(new Player(Faction.values()[i]));
        }
        return numberOfPlayers;
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

    public Road placeInitialRoad(Point roadStart, Point roadEnd, SiedlerBoard board, Player player) {
        boolean running;
        do {
            if (validRoadPlacement(roadStart, roadEnd, board, player)) {
                player.buildRoad(player, roadStart, roadEnd);
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
        Road road = new Road(player, roadStart, roadEnd);
        board.setEdge(road.getStartingAt(), road.getEndingAt(), road);
        return road;
    }

    public void placeRoad(Point roadStart, Point roadEnd, SiedlerBoard board, Player player) {
        boolean running;
        do {
            if (validRoadPlacement(roadStart, roadEnd, board, player)) {
                if (player.getResourcesInPossession().containsKey(Resource.CLAY) && player.getResourcesInPossession().containsKey(Resource.WOOD)) {
                    player.buildRoad(player, roadStart, roadEnd);
                    running = false;
                    player.removeResources(Resource.CLAY, 1);
                    player.removeResources(Resource.WOOD, 1);
                    board.setEdge(roadStart, roadEnd, new Road(player, roadStart, roadEnd));
                } else {
                    textTerminal.print("You do not have enough resources to build a road");
                    running = true;
                }
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
    }

    public boolean validRoadPlacement(Point roadStart, Point roadEnd, SiedlerBoard board, Player player) {
        if (board.hasCorner(roadStart) && board.hasCorner(roadEnd) && isCornerConnectedToLand(roadStart, board) && isCornerConnectedToLand(roadEnd, board)) {
            boolean rightCoordinates = board.hasEdge(roadStart, roadEnd) && board.hasCorner(roadStart) && board.hasCorner(roadEnd);
            boolean roadStartIsSettlement = player.getSettlementsBuilt().contains(board.getCorner(roadStart));
            boolean roadEndIsSettlement = player.getSettlementsBuilt().contains(board.getCorner(roadEnd));

            return rightCoordinates && (roadStartIsSettlement || roadEndIsSettlement);
        } else {
            return false;
        }
    }

    public boolean isCornerConnectedToLand(Point corner, SiedlerBoard board) {
        boolean result = false;
        Land[] lands = {Land.DESERT, Land.MOUNTAIN, Land.MEADOW, Land.GRAINFIELD, Land.FOREST, Land.CLAYSOIL};
        for (Land land : lands) {
            if (board.getFields(corner).contains(land))
                result = true;
        }
        return result;
    }
}
