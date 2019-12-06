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
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private Bank bank = new Bank();

    public SiedlerGame(int winPoints, int players) {
        createPlayers(players);
        this.winPoints = winPoints;
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
                int x = textIO.newIntInputReader().read("Can't place here, try again with another x coordinate\n");
                UI.refresh(hexBoard);
                int y = textIO.newIntInputReader().read("Can't place here, try again with another y coordinate\n");
                UI.refresh(hexBoard);
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
                    textTerminal.print("You don't have enough resources to build a settlement\n");
                    trying = false;
                }
            } else {
                int x = textIO.newIntInputReader().read("Can't place here, try again with another x coordinate\n");
                UI.refresh(hexBoard);
                int y = textIO.newIntInputReader().read("Can't place here, try again with another y coordinate\n");
                UI.refresh(hexBoard);
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


    public void placeCity(Point position, Player player) { //TODO: test and bugfix

        // checks if there is a settlement on the desired position
        if (player.getSettlementsBuiltPoints().contains(position)) {
            Map<Resource, Integer> resources = player.getResourcesInPossession();
            // checks if the player has the resources needed for a city to be build
            if (resources.get(Resource.STONE) >= 3 && resources.get(Resource.GRAIN) >= 2) {
                player.getSettlementAtPosition(position).setToCity();
                player.removeResources(Resource.STONE, 3);
                player.removeResources(Resource.GRAIN, 2);
            } else {
                UI.print("You do not have enough resources\n");
            }
        } else {
            UI.print("You don't have a settlement to upgrade on this position: " + position + "\n");
        }
    }

    public boolean tradeWithBankFourToOne(Resource offer, Resource want, Player player) {
        boolean result = false;
        if (bank.checkResources(want)) {
            if (player.removeResources(offer, 4)) {
                player.addResources(want, 1);
                bank.trade(offer, want);
                result = true;
            } else {
                textTerminal.print("You do not have enough " + offer + ".\n");
            }
        }
        return result;
    }

    public void askPlayerWhatToTrade(Player player) {
        for (int i = 0; i < Resource.values().length; ++i){
            textTerminal.print("" + (i+1) + ": " + Resource.values()[i].name() + " (You have " + player.getResourcesInPossession().get(Resource.values()[i]) + ")\n");
        }
        int chosenOptionWhatToGive = textIO.newIntInputReader().read("What would you like to trade?\n");
        int chosenOptionWhatToTake = textIO.newIntInputReader().read("What would you like to take?\n");
        tradeWithBankFourToOne(Resource.values()[chosenOptionWhatToGive-1], Resource.values()[chosenOptionWhatToTake-1], player);
    }

    public void tradeWithBank(int i) {
        Player currentPlayer = getPlayers().get(i);
        for (int j = 0; j < Resource.values().length; ++j){
            textTerminal.print("" + (j+1) + ": " + Resource.values()[j] + "\n");
        }
        int x = textIO.newIntInputReader().read("What would you like to trade?\n");
        switch (x) {
            case 1:
                if (currentPlayer.getResourcesInPossession().get(Resource.WOOD) >= 4) {
                    printAllResources();
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    currentPlayer.removeResources(Resource.WOOD, 4);
                    switch (y) {
                        case 1:
                            currentPlayer.addResources(Resource.WOOD, 1);
                            break;
                        case 2:
                            currentPlayer.addResources(Resource.STONE, 1);
                            break;
                        case 3:
                            currentPlayer.addResources(Resource.GRAIN, 1);
                            break;
                        case 4:
                            currentPlayer.addResources(Resource.CLAY, 1);
                            break;
                        case 5:
                            currentPlayer.addResources(Resource.WOOL, 1);
                            break;
                        default:
                            UI.print("Come on there are only 5 numbers...you can do this!\n");
                            break;
                    }

                } else {
                    textTerminal.print("You do not have enough Wood in your hood.\n");
                }
                break;

            case 2:
                if (getPlayers().get(i).getResourcesInPossession().get(Resource.STONE) >= 4) {
                    printAllResources();
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.STONE, 4);
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
                    printAllResources();
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.GRAIN, 4);
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
                    printAllResources();
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.CLAY, 4);
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
                    printAllResources();
                    int y = textIO.newIntInputReader().read("What would you like in return?\n");
                    getPlayers().get(i).removeResources(Resource.WOOL, 4);
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

    private void printAllResources() {
        for (int j = 0; j < Resource.values().length; ++j){
            textTerminal.print("" + (j+1) + ": " + Resource.values()[j] + "\n");
        }
    }

    // counts the points for each player for their settlements and cities
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

    // checks if the given point is a corner
    public Point isPointACorner(Point point) {
        boolean running;
        do {
            if (HexBoard.isCornerCoordinate(point)) {
                running = false;
            } else {
                textTerminal.print("Error this point is not on a corner, please try again\n");
                int x = textIO.newIntInputReader().read("Try again with a new x coordinate\n");
                int y = textIO.newIntInputReader().read("Try again with a new y coordinate\n");
                point = new Point(x, y);
                running = true;
            }
        } while (running);
        return point;
    }

    // used in the first phase to place the roads for each player
    public Road placeInitialRoad(Point roadStart, Point roadEnd, SiedlerBoard board, Player player) {
        boolean running;
        do {
            // checks if a road can be placed on the desired location
            if (validRoadPlacement(roadStart, roadEnd, board, player)) {
                player.buildRoad(player, roadStart, roadEnd);
                running = false;
            } else {
                UI.refresh(board);
                UI.print("Error this points are not on an edge, please try again\n");
                UI.refresh(board);
                int a = textIO.newIntInputReader().read("Try again with a new x coordinate for roadstart\n");
                UI.refresh(board);
                int b = textIO.newIntInputReader().read("Try again with a new y coordinate for roadstart\n");
                roadStart = new Point(a, b);
                UI.refresh(board);
                int x = textIO.newIntInputReader().read("Try again with a new x coordinate for roadend\n");
                UI.refresh(board);
                int y = textIO.newIntInputReader().read("Try again with a new y coordinate for roadend\n");
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
            // checks if a road can be placed on the desired location
            if (validRoadPlacement(roadStart, roadEnd, board, player)) {
                // checks if the player has the necessary resources to build a road
                if (player.getResourcesInPossession().containsKey(Resource.CLAY) && player.getResourcesInPossession().containsKey(Resource.WOOD)) {
                    player.buildRoad(player, roadStart, roadEnd);
                    running = false;
                    player.removeResources(Resource.CLAY, 1);
                    player.removeResources(Resource.WOOD, 1);
                    board.setEdge(roadStart, roadEnd, new Road(player, roadStart, roadEnd));
                } else {
                    textTerminal.print("You do not have enough resources to build a road\n");
                    running = true;
                }
            } else {
                UI.refresh(board);
                UI.print("Error this points are not on an edge, please try again\n");
                UI.refresh(board);
                int a = textIO.newIntInputReader().read("Try again with a new x coordinate for roadstart\n");
                UI.refresh(board);
                int b = textIO.newIntInputReader().read("Try again with a new y coordinate for roadstart\n");
                UI.refresh(board);
                roadStart = new Point(a, b);
                int x = textIO.newIntInputReader().read("Try again with a new x coordinate for roadend\n");
                UI.refresh(board);
                int y = textIO.newIntInputReader().read("Try again with a new y coordinate for roadend\n");
                UI.refresh(board);
                roadEnd = new Point(x, y);
                running = true;
            }
        } while (running);
    }

    // method used to verify the correct position for a road
    public boolean validRoadPlacement(Point roadStart, Point roadEnd, SiedlerBoard board, Player player) {
        if (board.hasCorner(roadStart) && board.hasCorner(roadEnd) && isCornerConnectedToLand(roadStart, board) && isCornerConnectedToLand(roadEnd, board)) {
            boolean rightCoordinates = board.hasEdge(roadStart, roadEnd) && board.hasCorner(roadStart) && board.hasCorner(roadEnd);
            boolean roadStartIsSettlement = player.getSettlementsBuilt().contains(board.getCorner(roadStart));
            boolean roadEndIsSettlement = player.getSettlementsBuilt().contains(board.getCorner(roadEnd));
            boolean roadStartIsRoad = player.getRoadPoints().contains(roadStart);
            boolean roadEndIsROad = player.getRoadPoints().contains(roadEnd);

            return rightCoordinates && (roadStartIsSettlement || roadEndIsSettlement || roadStartIsRoad || roadEndIsROad);
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
