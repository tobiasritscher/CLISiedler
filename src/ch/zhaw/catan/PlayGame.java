package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class PlayGame {
    private static SiedlerGame siedlerGame;
    private SiedlerBoard hexBoard = new SiedlerBoard();
    private int numberOfPlayers;
    private static TextIO textIO = TextIoFactory.getTextIO();
    private static TextTerminal<SwingTextTerminal> textTerminal = (SwingTextTerminal) textIO.getTextTerminal();


    public PlayGame() {
    }

   //starts entire game with both phases
    private void run() {

        UI.setupTerminal();
        UI.setBookmark("BLANK_SCREEN");

        if (!UI.buildStartMenu()) {

            numberOfPlayers = UI.askNumberOfPlayers();
            //TODO: Delete for Release
            if (numberOfPlayers == 69) {
                siedlerGame = new SiedlerGame(7, 2);
                siedlerGame.placeInitialSettlement(new Point(5, 15), siedlerGame.getPlayers().get(0), hexBoard);
                siedlerGame.placeInitialSettlement(new Point(8, 12), siedlerGame.getPlayers().get(0), hexBoard);
                siedlerGame.placeInitialSettlement(new Point(6, 6), siedlerGame.getPlayers().get(1), hexBoard);
                siedlerGame.placeInitialSettlement(new Point(9, 7), siedlerGame.getPlayers().get(1), hexBoard);
                siedlerGame.placeInitialRoad(new Point(5, 15), new Point(6, 16), hexBoard, siedlerGame.getPlayers().get(0));
                siedlerGame.placeInitialRoad(new Point(7, 13), new Point(8, 12), hexBoard, siedlerGame.getPlayers().get(0));
                siedlerGame.placeInitialRoad(new Point(6, 6), new Point(5, 7), hexBoard, siedlerGame.getPlayers().get(1));
                siedlerGame.placeInitialRoad(new Point(10, 6), new Point(9, 7), hexBoard, siedlerGame.getPlayers().get(1));

                UI.refresh(hexBoard);
                giveResourcesAfterFirstPhase(hexBoard);
                secondPhase();
            } else {
                UI.refresh(hexBoard);
                UI.print("Ok, there will be " + numberOfPlayers + " players");
                UI.promptEnter();

                //Creating a new game with both phases
                siedlerGame = new SiedlerGame(7, numberOfPlayers);
                firstPhase();
                secondPhase();
            }
        }

    }

//distribution of first 2 settlements and their roads
    public void firstPhase() {

        for (int i = 0; i < siedlerGame.getPlayers().size(); i++) {
            String currentPlayerFaction = siedlerGame.getPlayers().get(i).getFaction().name();
            Player currentPlayer = siedlerGame.getPlayers().get(i);
            UI.resetBookmark("SHOW_MAP");

            setInitialSettlementsAndRoads(currentPlayerFaction, currentPlayer, "first");
        }

        for (int i = siedlerGame.getPlayers().size() - 1; i >= 0; i--) {
            String currentPlayerFaction = siedlerGame.getPlayers().get(i).getFaction().name();
            Player currentPlayer = siedlerGame.getPlayers().get(i);

            setInitialSettlementsAndRoads(currentPlayerFaction, currentPlayer, "second");

        }
        giveResourcesAfterFirstPhase(hexBoard); // all the players receive resources depending next to which field they are
    }


    private void setInitialSettlementsAndRoads(String currentPlayerFaction, Player currentPlayer, String turn) {
        //ask for first settlement
        UI.refresh(hexBoard);
        int x = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for your " + turn + " settlement\n");
        UI.refresh(hexBoard);
        int y = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for your " + turn + " settlement\n");
        //set first settlement
        Point point = new Point(x, y);
        Point newPoint = siedlerGame.isPointACorner(point);
        Settlement settlement = siedlerGame.placeInitialSettlement(newPoint, currentPlayer, hexBoard);
        UI.refresh(hexBoard);

        //ask for first road start
        int xRoadStart = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for the start of your " + turn + " road\n");
        UI.refresh(hexBoard);

        int yRoadStart = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for the start of your " + turn + " road\n");
        UI.refresh(hexBoard);

        Point roadStart = new Point(xRoadStart, yRoadStart);

        //ask for first road finish
        int xRoadFinish = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for the finish of your " + turn + " road\n");
        UI.refresh(hexBoard);

        int yRoadFinish = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for the finish of your " + turn + " road\n");
        UI.refresh(hexBoard);

        //set first road
        Point roadEnd = new Point(xRoadFinish, yRoadFinish);
        Road road = siedlerGame.placeInitialRoad(roadStart, roadEnd, hexBoard, currentPlayer);
        UI.refresh(hexBoard);
    }


    // TODO build phase method calls
    //        To build roads:                   siedlerGame.placeRoad(Point_RoadStart, Point_RoadEnd); -- returns boolean
    //        To build settlements:             siedlerGame.placeSettlement(Point_Position); -- returns boolean
    //        To upgrade settlements to cities: siedlerGame.placeCity(Point_Position); -- returns boolean

    // TODO gameplay calls
    //        To check if someone won:          siedlerGame.getWinner(); -- returns boolean
    //        To trade with bank:               siedlerGame.tradeWithBankFourToOne(Resource_offer, Resource_want); -- returns boolean
    //        To roll dice                      siedlerGame.throwDice(dice.roll()); -- returns Map<Faction, List<Resource>

    public boolean isCornerFree(Point corner) {
        return hexBoard.getNeighboursOfCorner(corner).isEmpty();
    }

    public void giveResourcesAfterFirstPhase(SiedlerBoard board) {
        for (Point field : board.getFields()) {
            if (board.getField(field) != Config.Land.DESERT && board.getField(field) != Config.Land.WATER) {
                if (!board.getCornersOfField(field).isEmpty()) {
                    for (Settlement settlement : board.getCornersOfField(field)) {
                        settlement.getPlayer().addResources(board.getField(field).getResource(), 1);
                    }
                }
            }
        }

        printAllGivenRessourcesOfAllPlayers();
        //Ask players to press enter in order to start the second game phase
        UI.promptEnter();
    }

    public void printAllGivenRessourcesOfAllPlayers() {
        for (Player player : siedlerGame.getPlayers()){
            StringBuilder tempOutput = new StringBuilder(player.toString() + " has been given: ");
            for (Config.Resource resource : player.getResourcesInPossession().keySet()){

                tempOutput.append(player.getResourcesInPossession().get(resource)).append(" ").append(resource.toString()).append(", ");
            }
            String finalOutput = tempOutput.substring(0,tempOutput.length() - 2);
            finalOutput += "\n";
            UI.print(finalOutput);
        }
    }

    private void giveResourcesFromDice(int rolledNumber){
        for (Point field : hexBoard.getFields()) {
            if (hexBoard.getField(field) != Config.Land.DESERT && hexBoard.getField(field) != Config.Land.WATER && Config.getStandardDiceNumberPlacement().get(field) == rolledNumber) {
                if (!hexBoard.getCornersOfField(field).isEmpty()) {
                    for (Settlement settlement : hexBoard.getCornersOfField(field)) {
                        settlement.getPlayer().addResources(hexBoard.getField(field).getResource(), 1);
                        textTerminal.print(settlement.getFaction() + " has recieved 1 " + hexBoard.getField(field).getResource() + '\n');
                    }
                }
            }
        }
    }

    private void divideAllResources() {
        for (int i = 0; i < numberOfPlayers; ++i) {
            int totalResources = 0;
            for (Integer amountOfRessource : siedlerGame.getPlayers().get(i).getResourcesInPossession().values()) {
                totalResources += amountOfRessource;
            }

            //remove random resources from players with more then seven cards
            if (totalResources > 7) {
                int resourcesToDelete = (totalResources - totalResources % 2) / 2;
                for (int j = 0; j < resourcesToDelete; ++j) {
                    //new Arraylist with all resources the player has, to choose a random resource to remove
                    ArrayList<Config.Resource> resources = new ArrayList<>(siedlerGame.getPlayers().get(i).getResourcesInPossession().keySet());
                    //create random number to choose which resource to delete
                    int random = new Random().nextInt(resources.size() - 1);
                    siedlerGame.getPlayers().get(i).removeResources(resources.get(random), 1);
                }
            }
        }
    }

    private boolean longestRoad(Player currentPlayer){

        return true;
    }

    public void secondPhase() {
        UI.refresh(hexBoard);

        // each player begins their turn with a dice roll and receive the resources corresponding to the fields
        boolean gameIsRunning = true;
        for (int i = 0; gameIsRunning; i = (i + 1) % numberOfPlayers) {
            Player currentPlayer = siedlerGame.getPlayers().get(i);
            int rolledNumber = Dice.roll();
            UI.print(currentPlayer + " rolled a " + rolledNumber + "\n");

            // If the number rolled is 7 all players with more than 7 resources lose randomly half of their resources
            if (rolledNumber == 7) {
                divideAllResources();
            } else {
                giveResourcesFromDice(rolledNumber);
            }

            //gives the different options to the player after the dice roll
            boolean playersTurn = true;
            do {
                UI.refresh(hexBoard);
                UI.print("It's " + currentPlayer + "'s turn\n");
                UI.newLine();
                Integer decision = UI.printSecondPhaseMenu();

                switch (decision) {
                    case 1:
                        UI.refresh(hexBoard);
                        siedlerGame.tradeWithBank(i);
                        break;
                    case 2:
                        UI.refresh(hexBoard);
                        int x = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for your settlement\n");

                        UI.refresh(hexBoard);
                        int y = textIO.newIntInputReader().read(currentPlayer + " please pick a y coordinate for your settlement\n");

                        UI.refresh(hexBoard);
                        Point position = new Point(x, y);
                        siedlerGame.placeSettlement(position, currentPlayer, hexBoard);
                        if (siedlerGame.getWinner(currentPlayer)) {
                            UI.print(currentPlayer + "has won the game\n");
                        }
                        UI.print("Your settlement has been built");
                        UI.promptEnter();
                        break;
                    case 3:
                        UI.refresh(hexBoard);
                        int a = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for the start of your road\n");

                        UI.refresh(hexBoard);
                        int b = textIO.newIntInputReader().read(currentPlayer + " please pick a y coordinate for the start of your road\n");
                        Point roadStart = new Point(a, b);

                        UI.refresh(hexBoard);
                        int c = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for the finish of your road\n");

                        UI.refresh(hexBoard);
                        int d = textIO.newIntInputReader().read(currentPlayer + " please pick a y coordinate for the finish of your road\n");

                        Point roadEnd = new Point(c, d);
                        siedlerGame.placeRoad(roadStart, roadEnd, hexBoard, currentPlayer);
                        if (siedlerGame.getWinner(currentPlayer)) {
                            UI.print(currentPlayer + "has won the game\n");
                        }

                        UI.print("Your road has been built");
                        UI.promptEnter();
                        break;
                    case 4:
                        UI.refresh(hexBoard);
                        int e = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for your city\n");

                        UI.refresh(hexBoard);
                        int f = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for your city\n");

                        Point where = new Point(e, f);
                        siedlerGame.placeCity(where, currentPlayer);
                        UI.refresh(hexBoard);

                        if (siedlerGame.getWinner(currentPlayer)) {
                            UI.print(currentPlayer + "has won the game\n");
                        }
                        UI.refresh(hexBoard);
                        UI.print("Your settlement has been upgraded to a city");
                        UI.promptEnter();
                        break;
                    case 5:
                        UI.refresh(hexBoard);

                        for (HashMap.Entry<Config.Resource, Integer> entry : currentPlayer.getResourcesInPossession().entrySet()) {
                            UI.print(currentPlayer + " has " + entry.getKey() + ": " + entry.getValue() + "\n");
                        }

                        UI.promptEnter();
                        UI.refresh(hexBoard);


                        break;
                    case 6:
                        UI.refresh(hexBoard);
                        String sure = textIO.newStringInputReader().read(currentPlayer + " are you sure you want to end your turn? (Y/N)\n");
                        if (sure.equalsIgnoreCase("Y")) {
                            playersTurn = false;
                        }

                        break;
                    case 7:
                        UI.refresh(hexBoard);
                        String ciao = textIO.newStringInputReader().read("Sure?(Y/N)\n");
                        if (ciao.equalsIgnoreCase("Y")) {
                            playersTurn = false;
                            gameIsRunning = false;
                            UI.closeTerminal();
                        }
                        break;
                    case 69:
                        for (Config.Resource resource : Config.Resource.values()) {
                            currentPlayer.addResources(resource, 1000);
                        }
                        break;
                    default:
                        UI.print("The number you have selected doesn't exist, please try again\n");
                }
            } while (playersTurn);
        }
    }



    public static void main(String[] Args) {
        new PlayGame().run();

    }

}
