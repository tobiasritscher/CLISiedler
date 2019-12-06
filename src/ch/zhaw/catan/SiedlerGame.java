package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Land;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.hexboard.HexBoard;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SiedlerGame {
    private int winPoints;
    private List<Player> players = new ArrayList<>();
    private int currentPlayer = 0;
    private TextIO textIO = TextIoFactory.getTextIO();

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

    public Settlement placeInitialSettlement(Point position, Player player, SiedlerBoard board) {
        Settlement settlement = null;
        boolean trying;

        do {
            if (validSettlementPlacing(position, board)) {
                settlement = new Settlement(position, player);
                player.addSettlement(settlement);
                trying = false;
            } else {
                int x = textIO.newIntInputReader().read("Can't place here, try again with another x coordinate\n");
                UI.refresh(board);
                int y = textIO.newIntInputReader().read("Can't place here, try again with another y coordinate\n");
                UI.refresh(board);
                position = new Point(x, y);
                trying = true;
            }
        } while (trying);
        board.setCorner(settlement.getPosition(), settlement);
        return settlement;
    }

    public void placeSettlement(Point position, Player player, SiedlerBoard board, Bank bank) {
        Settlement settlement = null;
        boolean trying;

        do {
            if (validSettlementPlacing(position, board)) {
                if (player.getResourcesInPossession().containsKey(Resource.CLAY)
                        && player.getResourcesInPossession().containsKey(Resource.WOOD)
                        && player.getResourcesInPossession().containsKey(Resource.WOOL)
                        && player.getResourcesInPossession().containsKey(Resource.GRAIN)) {
                    settlement = new Settlement(position, player);
                    player.addSettlement(settlement);
                    player.removeResources(Resource.CLAY, 1, bank);
                    player.removeResources(Resource.WOOD, 1, bank);
                    player.removeResources(Resource.WOOL, 1, bank);
                    player.removeResources(Resource.GRAIN, 1, bank);
                    board.setCorner(settlement.getPosition(), settlement);
                    UI.print("Your settlement has been built");
                    trying = false;
                } else {

                    UI.print("You don't have enough resources to build a settlement\n");
                    trying = false;
                }
            } else {
                UI.refresh(board);
                int x = textIO.newIntInputReader().read("Can't place here, try again with another x coordinate\n");
                UI.refresh(board);
                int y = textIO.newIntInputReader().read("Can't place here, try again with another y coordinate\n");
                UI.refresh(board);
                position = new Point(x, y);
                trying = true;
            }
        } while (trying);
    }

    private boolean validSettlementPlacing(Point position, SiedlerBoard board) {
        return board.getNeighboursOfCorner(position).isEmpty() &&
                board.hasCorner(position) &&
                isCornerConnectedToLand(position, board) &&
                (board.getCorner(position) == null);
    }


    public void placeCity(Point position, Player player, Bank bank) { //TODO: test and bugfix

        // checks if there is a settlement on the desired position
        if (player.getSettlementsBuiltPoints().contains(position)) {
            Map<Resource, Integer> resources = player.getResourcesInPossession();
            // checks if the player has the resources needed for a city to be build
            if (resources.get(Resource.STONE) >= 3 && resources.get(Resource.GRAIN) >= 2) {
                player.getSettlementAtPosition(position).setToCity();
                player.removeResources(Resource.STONE, 3, bank);
                player.removeResources(Resource.GRAIN, 2, bank);
                UI.print("Your settlement has been upgraded to a city");
            } else {
                UI.print("You do not have enough resources\n");
            }
        } else {
            UI.print("You don't have a settlement to upgrade on this position: " + position + "\n");
        }
    }

    public void tradeWithBankFourToOne(Resource offer, Resource want, Player player, Bank bank) {
        if (bank.checkResources(want, 1)) {
            if (player.removeResources(offer, 4, bank)) {
                player.addResources(want, 1, bank);
                bank.trade(offer, want);
            } else {
                UI.print("You do not have enough " + offer + ".\n");
            }
        } else {
            UI.print("The bank doesn't have enough " + want + ".\n");
        }
    }

    public void askPlayerWhatToTrade(Player player, Bank bank) {
        printAllResources(player);
        int chosenOptionWhatToGive = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(5)
                .read("What would you like to trade?\n");
        int chosenOptionWhatToTake = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(5)
                .read("What would you like to take?\n");
        tradeWithBankFourToOne(Resource.values()[chosenOptionWhatToGive - 1], Resource.values()[chosenOptionWhatToTake - 1], player, bank);
    }

    private void printAllResources(Player player) {
        for (int i = 0; i < Resource.values().length; ++i) {
            int resourceInPossessionCount = player.getResourcesInPossession().getOrDefault(Resource.values()[i], 0);
            UI.print("" + (i + 1) + ": " + Resource.values()[i] + " (You have " + resourceInPossessionCount + ")\n");
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
                UI.print("Error this point is not on a corner, please try again\n");
                int x = textIO.newIntInputReader().read("Try again with a new x coordinate\n");
                int y = textIO.newIntInputReader().read("Try again with a new y coordinate\n");
                point = new Point(x, y);
                running = true;
            }
        } while (running);
        return point;
    }

    // used in the first phase to place the roads for each player
    public void placeInitialRoad(Point roadStart, Point roadEnd, SiedlerBoard board, Player player) {
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
    }


    public void placeRoad(Point roadStart, Point roadEnd, SiedlerBoard board, Player player, Bank bank) {
        boolean running;
        do {
            // checks if a road can be placed on the desired location
            if (validRoadPlacement(roadStart, roadEnd, board, player)) {
                // checks if the player has the necessary resources to build a road
                if (player.getResourcesInPossession().containsKey(Resource.CLAY) && player.getResourcesInPossession().containsKey(Resource.WOOD)) {
                    player.buildRoad(player, roadStart, roadEnd);
                    running = false;
                    player.removeResources(Resource.CLAY, 1, bank);
                    player.removeResources(Resource.WOOD, 1, bank);
                    board.setEdge(roadStart, roadEnd, new Road(player, roadStart, roadEnd));
                    UI.print("Your road has been built");
                } else {
                    UI.print("You do not have enough resources to build a road\n");
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

    public boolean verifyWinner(Player currentPlayer){
        boolean gameIsRunning = true;
        if (getWinner(currentPlayer)) {
            UI.print(currentPlayer + " has won the game\n");
            gameIsRunning = false;
        }
        return gameIsRunning;

    }

}
