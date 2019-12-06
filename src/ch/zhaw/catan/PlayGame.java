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
    private SiedlerBoard board = new SiedlerBoard();
    private int numberOfPlayers;
    private static TextIO textIO = TextIoFactory.getTextIO();
    private static TextTerminal<SwingTextTerminal> textTerminal = (SwingTextTerminal) textIO.getTextTerminal();
    private Bank bank = new Bank();


    public PlayGame() {
    }

    public void  siedlerGameTestHelper() {
        siedlerGame = new SiedlerGame(7, 4);
    }

    public SiedlerBoard getBoard() {
        return board;
    }

    public Player getPlayer(int index) {
        return siedlerGame.getPlayers().get(index);
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
                siedlerGame.placeInitialSettlement(new Point(5, 15), siedlerGame.getPlayers().get(0), board);
                siedlerGame.placeInitialSettlement(new Point(8, 12), siedlerGame.getPlayers().get(0), board);
                siedlerGame.placeInitialSettlement(new Point(6, 6), siedlerGame.getPlayers().get(1), board);
                siedlerGame.placeInitialSettlement(new Point(9, 7), siedlerGame.getPlayers().get(1), board);
                siedlerGame.placeInitialRoad(new Point(5, 15), new Point(6, 16), board, siedlerGame.getPlayers().get(0));
                siedlerGame.placeInitialRoad(new Point(7, 13), new Point(8, 12), board, siedlerGame.getPlayers().get(0));
                siedlerGame.placeInitialRoad(new Point(6, 6), new Point(5, 7), board, siedlerGame.getPlayers().get(1));
                siedlerGame.placeInitialRoad(new Point(10, 6), new Point(9, 7), board, siedlerGame.getPlayers().get(1));

                numberOfPlayers = 2;
                UI.refresh(board);
                giveResourcesAfterFirstPhase(board);
                secondPhase();
            } else {
                UI.refresh(board);
                UI.print("Ok, there will be " + numberOfPlayers + " players");
                UI.promptEnter();

                //Creating a new game with both phases
                siedlerGame = new SiedlerGame(7, numberOfPlayers);
                firstPhase();
                secondPhase();
                UI.closeTerminal();
            }
        }

    }

//distribution of first 2 settlements and their roads
    private void firstPhase() {

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
        giveResourcesAfterFirstPhase(board); // all the players receive resources depending next to which field they are
    }


    private void setInitialSettlementsAndRoads(String currentPlayerFaction, Player currentPlayer, String turn) {
        //ask for first settlement
        UI.refresh(board);
        int x = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for your " + turn + " settlement\n");
        UI.refresh(board);
        int y = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for your " + turn + " settlement\n");
        //set first settlement
        Point point = new Point(x, y);
        Point newPoint = siedlerGame.isPointACorner(point);
        siedlerGame.placeInitialSettlement(newPoint, currentPlayer, board);
        UI.refresh(board);

        //ask for first road start
        int xRoadStart = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for the start of your " + turn + " road\n");
        UI.refresh(board);

        int yRoadStart = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for the start of your " + turn + " road\n");
        UI.refresh(board);

        Point roadStart = new Point(xRoadStart, yRoadStart);

        //ask for first road finish
        int xRoadFinish = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for the finish of your " + turn + " road\n");
        UI.refresh(board);

        int yRoadFinish = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for the finish of your " + turn + " road\n");
        UI.refresh(board);

        //set first road
        Point roadEnd = new Point(xRoadFinish, yRoadFinish);
        siedlerGame.placeInitialRoad(roadStart, roadEnd, board, currentPlayer);
        UI.refresh(board);
    }

    public void giveResourcesAfterFirstPhase(SiedlerBoard board) {
        for (Point field : board.getFields()) {
            if (board.getField(field) != Config.Land.DESERT && board.getField(field) != Config.Land.WATER) {
                if (!board.getCornersOfField(field).isEmpty()) {
                    for (Settlement settlement : board.getCornersOfField(field)) {
                        Config.Resource currentResource = (board.getField(field).getResource());
                        settlement.getPlayer().addResources(currentResource, 1, bank);
                        bank.removeResources(currentResource, 1);
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
        for (Point field : board.getFields()) {
            if (board.getField(field) != Config.Land.DESERT && board.getField(field) != Config.Land.WATER && Config.getStandardDiceNumberPlacement().get(field) == rolledNumber) {
                if (!board.getCornersOfField(field).isEmpty()) {
                    for (Settlement settlement : board.getCornersOfField(field)) {
                        Config.Resource currentResource = board.getField(field).getResource();
                        Player currentPlayer = settlement.getPlayer();
                        int amount;
                        if (settlement.getIsCity())
                            amount = 2;
                        else
                            amount = 1;
                        currentPlayer.addResources(currentResource, amount, bank);
                        textTerminal.print(currentPlayer.getFaction() + " has recieved " + amount + " " + currentResource + '\n');
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
                    int random = new Random().nextInt(resources.size());
                    siedlerGame.getPlayers().get(i).removeResources(resources.get(random), 1, bank);
                    bank.addResources(resources.get(random), 1);
                }
            }
        }
    }

    private boolean longestRoad(Player currentPlayer){

        return true;
    }

    public void secondPhase() {
        UI.refresh(board);

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
            boolean running = true;
            do {
                UI.print("It's " + currentPlayer + "'s turn\n");
                UI.newLine();
                int decision = UI.printSecondPhaseMenu();

                switch (decision) {
                    case 1:
                        UI.refresh(board);
                        siedlerGame.askPlayerWhatToTrade(currentPlayer, bank);
                        break;
                    case 2:
                        UI.refresh(board);
                        int x = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for your settlement\n");

                        UI.refresh(board);
                        int y = textIO.newIntInputReader().read(currentPlayer + " please pick a y coordinate for your settlement\n");

                        UI.refresh(board);
                        Point position = new Point(x, y);
                        siedlerGame.placeSettlement(position, currentPlayer, board, bank);
                        if (siedlerGame.getWinner(currentPlayer)) {
                            UI.print(currentPlayer + "has won the game\n");
                        }
                        UI.promptEnter();
                        UI.refresh(board);
                        break;
                    case 3:
                        UI.refresh(board);
                        int a = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for the start of your road\n");

                        UI.refresh(board);
                        int b = textIO.newIntInputReader().read(currentPlayer + " please pick a y coordinate for the start of your road\n");
                        Point roadStart = new Point(a, b);

                        UI.refresh(board);
                        int c = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for the finish of your road\n");

                        UI.refresh(board);
                        int d = textIO.newIntInputReader().read(currentPlayer + " please pick a y coordinate for the finish of your road\n");

                        Point roadEnd = new Point(c, d);
                        siedlerGame.placeRoad(roadStart, roadEnd, board, currentPlayer, bank);
                        siedlerGame.verifyWinner(currentPlayer);

                        UI.promptEnter();
                        UI.refresh(board);
                        break;
                    case 4:
                        UI.refresh(board);
                        int e = textIO.newIntInputReader().read(currentPlayer + " please pick a x coordinate for your city\n");

                        UI.refresh(board);
                        int f = textIO.newIntInputReader().read(currentPlayer + " please pick a y coordinate for your city\n");

                        Point where = new Point(e, f);
                        siedlerGame.placeCity(where, currentPlayer, bank);

                        siedlerGame.verifyWinner(currentPlayer);
                        UI.promptEnter();
                        UI.refresh(board);
                        break;
                    case 5:
                        UI.refresh(board);

                        for (HashMap.Entry<Config.Resource, Integer> entry : currentPlayer.getResourcesInPossession().entrySet()) {
                            UI.print(currentPlayer + " has " + entry.getKey() + ": " + entry.getValue() + "\n");
                        }

                        UI.promptEnter();
                        UI.refresh(board);


                        break;
                    case 6:
                        UI.refresh(board);
                        String sure = textIO.newStringInputReader().read(currentPlayer + " are you sure you want to end your turn? (Y/N)\n");
                        if (sure.equalsIgnoreCase("Y")) {
                            running = false;
                        }
                        break;
                    case 7:
                        UI.refresh(board);
                        String ciao = textIO.newStringInputReader().read("Sure?(Y/N)\n");
                        if (ciao.equalsIgnoreCase("Y")) {
                            gameIsRunning = false;
                            running = false;
                        }
                        break;
                    case 69:
                        for (Config.Resource resource : Config.Resource.values()) {
                            currentPlayer.addWithCheat(resource, 1000);
                        }
                        break;
                    default:
                        UI.print("The number you have selected doesn't exist, please try again\n");
                }
            } while (running);
        }
    }

    public enum ChosenOption {
        STOP("0", "Exit the programm (also prints the final text)"),
        PRINT_PARAGRAPHS("1", "Print paragraphs"),
        INSERT_PARAGRAPH("2", "Insert paragraph"),
        DELETE_PARAGRAPH("3", "Delete paragraph"),
        REPLACE_WORD("4", "Replace a word in a paragraph"),
        INDEX_WORDS("5", "Index of words being used"),
        PRINT_FORMATED_TEXT("6", "Print formatted text"),
        ENCRYPT("7", "Encrypt a text"),
        DECRYPT("8", "Decrypt a text"),
        WRONG_INPUT("ERROR", "");

        private final String chosenOptionCode;
        private final String textForUser;

        ChosenOption(String chosenOptionCode, String textForUser) {
            this.chosenOptionCode = chosenOptionCode;
            this.textForUser = textForUser;
        }

        public static ChosenOption codeOfOption(String label) {
            for (ChosenOption value : values()) {
                if (value.chosenOptionCode.equals(label)) {
                    return value;
                }
            }
            return WRONG_INPUT;
        }
    }


    public static void main(String[] Args) {
        new PlayGame().run();

    }

}
