package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;
import java.util.HashMap;


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

        for (int i = 0; i < siedlerGame.getPlayers().size(); i++) {
            String currentPlayerFaction = siedlerGame.getPlayers().get(i).getFaction().name();
            Player currentPlayer = siedlerGame.getPlayers().get(i);
            UI.resetBookmark("SHOW_MAP");

            setInitialSettlementsAndRoads(currentPlayerFaction, currentPlayer, "first");
        }

        for (int i = siedlerGame.getPlayers().size() - 1 ; i >= 0; i--) {
            String currentPlayerFaction = siedlerGame.getPlayers().get(i).getFaction().name();
            Player currentPlayer = siedlerGame.getPlayers().get(i);

            setInitialSettlementsAndRoads(currentPlayerFaction, currentPlayer, "second");

        }
        giveResourcesAfterFirstPhase(hexBoard);
    }

    private void setInitialSettlementsAndRoads (String currentPlayerFaction, Player currentPlayer, String turn){
        //ask for first settlement
        int x = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for your " + turn + " settlement");
        UI.resetBookmark("BLANK_SCREEN");
        UI.printBoard(hexBoard);
        int y = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for your " + turn + " settlement");
        //set first settlement
        Point point = new Point(x, y);
        Point newPoint = siedlerGame.isPointACorner(point);
        Settlement settlement = siedlerGame.placeInitialSettlement(newPoint, currentPlayer, hexBoard);
        hexBoard.setCorner(settlement.getPosition(), settlement);
        UI.resetBookmark("BLANK_SCREEN");
        UI.printBoard(hexBoard);

        //ask for first road start
        int xRoadStart = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for the start of your " + turn + " road");
        UI.resetBookmark("BLANK_SCREEN");
        UI.printBoard(hexBoard);
        int yRoadStart = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for the start of your " + turn + " road");
        UI.resetBookmark("BLANK_SCREEN");
        UI.printBoard(hexBoard);
        Point roadStart = new Point(xRoadStart, yRoadStart);
        //ask for first road finish
        int xRoadFinish = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a x coordinate for the finish of your " + turn + " road");
        UI.resetBookmark("BLANK_SCREEN");
        UI.printBoard(hexBoard);
        int yRoadFinish = textIO.newIntInputReader().read(currentPlayerFaction + " please pick a y coordinate for the finish of your " + turn + " road");

        //set first road
        Point roadEnd = new Point(xRoadFinish, yRoadFinish);
        Road road = siedlerGame.placeRoad(roadStart, roadEnd, hexBoard, currentPlayer);
        hexBoard.setEdge(road.getStartingAt(), road.getEndingAt(), road);
        textTerminal.println();
        UI.resetBookmark("BLANK_SCREEN");
        UI.printBoard(hexBoard);
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
    public void giveResourcesAfterFirstPhase(SiedlerBoard hexBoard) {
        for (Point field : hexBoard.getFields()) {
            if (!hexBoard.getCornersOfField(field).isEmpty()) {
                for (Settlement settlement : hexBoard.getCornersOfField(field)) {
                    settlement.getPlayer().addResources(Config.getStandardLandPlacement().get(field).getResource(), 1);
                    HashMap<Config.Resource, Integer> hashMap = new HashMap<>();
                    hashMap.put(Config.getStandardLandPlacement().get(field).getResource(), 1);
                    UI.print(settlement.getFaction() + " has recieved 1 " + Config.getStandardLandPlacement().get(field).getResource().toString() + '\n');
                }
            }
        }
    }

    public static void main(String[] Args) {
        new PlayGame().run();

    }

}
