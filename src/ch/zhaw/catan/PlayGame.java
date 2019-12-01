package ch.zhaw.catan;

import ch.zhaw.hexboard.HexBoard;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;
import java.util.Scanner;


public class PlayGame {
    private HexBoard hexBoard = new HexBoard();
    private int numberOfPlayers;
    private SiedlerGame siedlerGame;
    private Scanner scanner;
    Config config = new Config();
    private Config.Faction faction;
    private Player player = new Player(faction);
    private TextIO textIO = TextIoFactory.getTextIO();
    private TextTerminal<SwingTextTerminal> textTerminal = (SwingTextTerminal)textIO.getTextTerminal();
    private Dice dice = new Dice();

    private void run() {

        UI.setupTerminal(textIO, textTerminal);
        textTerminal.setBookmark("BLANK_SCREEN");

        UI.buildStartMenu(textIO, textTerminal);



//        boolean running = true;
//        while (running) {
//            switch (getEnumValue(textIO, PlayGame.Actions.class)) {
//                case NEW_GAME:
//                    numberOfPlayers = numberOfPlayers();
//                    siedlerGame = new SiedlerGame(7, numberOfPlayers);
//                    siedlerGame.createPlayers(numberOfPlayers);
//                    textTerminal.resetToBookmark("START");
//                    textTerminal.printf("There are %d dickheads playing the game", numberOfPlayers);
//
//                    textTerminal.printf(System.lineSeparator());
//                    firstPhase();
//                    //TODO: Hier wird ein neues Spiel instanziert
//                    break;
//                case QUIT:
//                    boolean reallyQuit = textIO.newBooleanInputReader().read("Do you really want to quit, you son of a beach?");
//                    if (reallyQuit) {
//                        running = false;
//                    } else {
//                        textTerminal.resetToBookmark("START");
//                    }
//                    break;
//                default:
//                    throw new IllegalStateException("Internal error found - Command not implemented.");
//            }
//        }

    }


    private int numberOfPlayers() {
        return textIO.newIntInputReader()
                .withMinVal(2)
                .withMaxVal(4)
                .read("How many players will be playing?");
    }

    private void firstPhase() {
        for (int i = 0; i < siedlerGame.getPlayer().size(); i++) {


            int x = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for your first settlement");
            textTerminal.printf(System.lineSeparator());

            int y = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for your first settlement");
            textTerminal.printf(System.lineSeparator());

            Point point = new Point(x, y);
            siedlerGame.placeInitialSettlement(point, true);

            int xRoadStart = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for the start of your first road");
            textTerminal.printf(System.lineSeparator());

            int yRoadStart = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for the start of your first road");
            textTerminal.printf(System.lineSeparator());

            Point roadStart = new Point(xRoadStart, yRoadStart);

            int xRoadFinish = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for the finish of your first road");
            textTerminal.printf(System.lineSeparator());

            int yRoadFinish = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for the finish of your first road");
            textTerminal.printf(System.lineSeparator());

            Point roadEnd = new Point(xRoadFinish, yRoadFinish);
            siedlerGame.placeInitialRoad(roadStart, roadEnd);

            textTerminal.resetToBookmark("START");
        }
        for (int i = siedlerGame.getPlayer().size() - 1; i > 0; i--) {
            int x = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for your next settlement");
            textTerminal.printf(System.lineSeparator());

            int y = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for your next settlement");
            textTerminal.printf(System.lineSeparator());

            Point point = new Point(x, y);
            siedlerGame.placeSettlement(point);

            int xRoadStart = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for the start of your next road");
            textTerminal.printf(System.lineSeparator());

            int yRoadStart = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for the start of your next road");
            textTerminal.printf(System.lineSeparator());

            Point roadStart = new Point(xRoadStart, yRoadStart);

            int xRoadFinish = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for the finish of your next road");
            textTerminal.printf(System.lineSeparator());

            int yRoadFinish = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for the finish of your next road");
            textTerminal.printf(System.lineSeparator());

            Point roadEnd = new Point(xRoadFinish, yRoadFinish);
            siedlerGame.placeRoad(roadStart, roadEnd);
            textTerminal.resetToBookmark("START");
        }
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
        // TODO create method to check if the neighbor corners are free
        return true;

    }

    public static void main(String[] Args) {
        new PlayGame().run();

    }

}
