package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;


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
            UI.print("Ok, there will be " + numberOfPlayers + " players");
            textIO.newStringInputReader()
                    .withMinLength(0)
                    .read("\nPress enter to continue");

            //Creating a new game
            siedlerGame = new SiedlerGame(7, numberOfPlayers);
            firstPhase();
        }

    }


    public void firstPhase() {

        for (int i = 0; i < siedlerGame.getPlayers().size() - 1; i++) {
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
    }

    private void setInitialSettlementsAndRoads (String currentPlayerFaction, Player currentPlayer, String turn){
        //ask for first settlement
        int x = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for your " + turn + " settlement");
        UI.resetBookmark("SHOW_MAP");
        int y = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for your " + turn + " settlement");
        UI.resetBookmark("SHOW_MAP");
        //set first settlement
        Point point = new Point(x, y);
        Point newPoint = siedlerGame.isPointACorner(point);
        hexBoard.setCorner(newPoint, siedlerGame.placeInitialSettlement(newPoint, currentPlayer, hexBoard));
        UI.printBoard(hexBoard); //TODO: delete; only for debugging

        //ask for first road start
        int xRoadStart = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for the start of your " + turn + " road");
        UI.resetBookmark("SHOW_MAP");
        int yRoadStart = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for the start of your " + turn + " road");
        UI.resetBookmark("SHOW_MAP");
        Point roadStart = new Point(xRoadStart, yRoadStart);
        //ask for first road finish
        int xRoadFinish = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for the finish of your " + turn + " road");
        UI.resetBookmark("SHOW_MAP");
        int yRoadFinish = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for the finish of your " + turn + " road");
        UI.resetBookmark("SHOW_MAP");
        UI.printBoard(hexBoard); //TODO: delete; only for debugging

        //set first road
        Point roadEnd = new Point(xRoadFinish, yRoadFinish);
        Road road = siedlerGame.placeRoad(roadStart, roadEnd, hexBoard, currentPlayer);
        hexBoard.setEdge(road.getStartingAt(), road.getEndingAt(), road);
        textTerminal.println();
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

    //TODO: not working: (0,0) is not a field
    public void giveResourcesAfterFirstPhase() {
        for (int i = 0; i < 13; i++) {
            for (int k = 0; k < 23; k++) {
                Point point = new Point(i, k);
                    if (hexBoard.hasFieldFixed(point)) {
                        if (!hexBoard.getCornersOfField(point).isEmpty()) {
                            for (int m = 0; m < hexBoard.getCornersOfField(point).size() - 1; m++) {
                                Point position = hexBoard.getCornersOfField(point).get(m).getPosition();
                                hexBoard.getCornersOfField(point).get(m).getPlayer().addResources(Config.getStandardLandPlacement().get(position).getResource(), 1);
                                textTerminal.print(hexBoard.getCornersOfField(point).get(m).getPlayer() + "has recieved 1 " + hexBoard.getCornersOfField(point).get(m).getPlayer().getResourcesInPossession().keySet());
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
