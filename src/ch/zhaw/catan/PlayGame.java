package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;
import java.util.ArrayList;


public class PlayGame {
    private static SiedlerGame siedlerGame;
    private SiedlerBoard hexBoard = new SiedlerBoard();
    private int numberOfPlayers;
    Config config = new Config();
    private static TextIO textIO = TextIoFactory.getTextIO();
    private static TextTerminal<SwingTextTerminal> textTerminal = (SwingTextTerminal) textIO.getTextTerminal();
    private Dice dice = new Dice();


    public PlayGame() {
    }

    private void run() {

        UI.setupTerminal();
        UI.setBookmark("BLANK_SCREEN");

        if (!UI.buildStartMenu()) {
            UI.setBookmark("SHOW_MAP");

            numberOfPlayers = UI.askNumberOfPlayers();
            textTerminal.print("Ok, there will be " + numberOfPlayers + " players");
            textIO.newStringInputReader()
                    .withMinLength(0)
                    .read("\nPress enter to continue");

            //Creating a new game
            siedlerGame = new SiedlerGame(7, numberOfPlayers);
            siedlerGame.createPlayers(numberOfPlayers);
            firstPhase();
        }

    }


    public void firstPhase() {

        for (int i = 0; i < siedlerGame.getPlayer().size(); i++) {
            UI.resetBookmark("SHOW_MAP");

            int x = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a x coordinate for your first settlement");
            UI.resetBookmark("SHOW_MAP");

            int y = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a y coordinate for your first settlement");
            UI.resetBookmark("SHOW_MAP");


            Point point = new Point(x, y);
            Point newPoint = siedlerGame.isPointACorner(point);
            hexBoard.setCorner(newPoint, siedlerGame.placeInitialSettlement(newPoint, siedlerGame.getPlayer().get(i), hexBoard));
            UI.printBoard(hexBoard); //TODO: delete; only for debugging

            int xRoadStart = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a x coordinate for the start of your first road");
            UI.resetBookmark("SHOW_MAP");

            int yRoadStart = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a y coordinate for the start of your first road");
            UI.resetBookmark("SHOW_MAP");

            Point roadStart = new Point(xRoadStart, yRoadStart);

            int xRoadFinish = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a x coordinate for the finish of your first road");
            UI.resetBookmark("SHOW_MAP");

            int yRoadFinish = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a y coordinate for the finish of your first road");
            UI.resetBookmark("SHOW_MAP");

            Point roadEnd = new Point(xRoadFinish, yRoadFinish);
            hexBoard.setEdge(roadStart, roadEnd, siedlerGame.placeRoad(roadStart, roadEnd, hexBoard));
            textTerminal.println();
        }

        for (int i = siedlerGame.getPlayer().size() - 1; i >= 0; i--) {
            int x = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a x coordinate for your next settlement");
            UI.resetBookmark("SHOW_MAP");

            int y = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a y coordinate for your next settlement");
            UI.resetBookmark("SHOW_MAP");

            Point point = new Point(x, y);
            hexBoard.setCorner(point, siedlerGame.placeInitialSettlement(point, siedlerGame.getPlayer().get(i), hexBoard));

            int xRoadStart = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a x coordinate for the start of your next road");
            UI.resetBookmark("SHOW_MAP");

            int yRoadStart = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a y coordinate for the start of your next road");
            UI.resetBookmark("SHOW_MAP");

            Point roadStart = new Point(xRoadStart, yRoadStart);

            int xRoadFinish = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a x coordinate for the finish of your next road");
            UI.resetBookmark("SHOW_MAP");

            int yRoadFinish = textIO.newIntInputReader().read(siedlerGame.getPlayer().get(i).getFaction().name() + " please pick a y coordinate for the finish of your next road");
            UI.resetBookmark("SHOW_MAP");

            Point roadEnd = new Point(xRoadFinish, yRoadFinish);
            hexBoard.setEdge(roadStart, roadEnd, siedlerGame.placeRoad(roadStart, roadEnd, hexBoard));
            textTerminal.println();
            giveResourcesAfterFirstPhase();
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
        return hexBoard.getNeighboursOfCorner(corner).isEmpty();
    }

    public void giveResourcesAfterFirstPhase() {
        for (int i = 0; i < 13; i++) {
            for (int k = 0; k < 23; k++) {
                Point point = new Point(i, k);
                if (hexBoard.hasField(point)) {
                    if (hexBoard.getCornersOfField(point).isEmpty()) {
                    } else {
                        for (int m = 0; m < hexBoard.getCornersOfField(point).size() - 1; m++) {
                            Point position = hexBoard.getCornersOfField(point).get(m).getPosition();
                            hexBoard.getCornersOfField(point).get(m).getPlayer().addResources(Config.getStandardLandPlacement().get(position).getResource(), 1);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] Args) {
        new PlayGame().run();

    }

}
