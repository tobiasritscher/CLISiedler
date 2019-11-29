package ch.zhaw.catan;

import ch.zhaw.hexboard.HexBoard;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

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
    private TextTerminal<?> textTerminal = textIO.getTextTerminal();
    private Dice dice = new Dice();

    private String clearScreen;


    public enum Actions {
        NEW_GAME, QUIT
    }

    private void run() {
        textTerminal.setBookmark("START");

        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, PlayGame.Actions.class)) {
                case NEW_GAME:
                    numberOfPlayers = numberOfPlayers();
                    siedlerGame = new SiedlerGame(7, numberOfPlayers);
                    siedlerGame.createPlayers(numberOfPlayers);
                    textTerminal.resetToBookmark("START");
                    textTerminal.printf("There are %d dickheads playing the game", numberOfPlayers);
                    textTerminal.printf(System.lineSeparator());
                    firstPhase();
                    //TODO: Hier wird ein neues Spiel instanziert
                    break;
                case QUIT:
                    int reallyQuit = textIO.newIntInputReader()
                            .withMinVal(1)
                            .withMaxVal(2)
                            .read("Bisch du Spasst sicher? \n  1: Ja \n  2: Nei\n");
                    if(reallyQuit == 1) {
                        running = false;
                    } else if (reallyQuit == 2){
                        textTerminal.resetToBookmark("START");
                    }
                    break;
                default:
                    throw new IllegalStateException("Internal error found - Command not implemented.");
            }
        }
        textIO.dispose();
    }

    public static <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What would you like to do?");
    }

    public int numberOfPlayers() {
        return textIO.newIntInputReader()
                .withMinVal(2)
                .withMaxVal(4)
                .read("How many players will be playing?");
    }

    public void firstPhase() {
        for (int i = 0; i < siedlerGame.getPlayer().size(); i++) {
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for your first settlement");
            Scanner x = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for your first settlement");
            Scanner y = new Scanner(System.in);
            Point point = new Point(x.nextInt(), y.nextInt());
            siedlerGame.placeInitialSettlement(point, true);
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for the start of your first road");
            Scanner xroadStart = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for the start of your first road");
            Scanner yroadStart = new Scanner(System.in);
            Point roadStart = new Point(xroadStart.nextInt(), yroadStart.nextInt());
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for the finish of your first road");
            Scanner xroadFinish = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for the finish of your first road");
            Scanner yroadFinish = new Scanner(System.in);
            Point roadEnd = new Point(xroadFinish.nextInt(), yroadFinish.nextInt());
            siedlerGame.placeInitialRoad(roadStart, roadEnd);
        }
        for (int i = siedlerGame.getPlayer().size() - 1; i > 0; i--) {
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for your next settlement");
            Scanner x = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for your next settlement");
            Scanner y = new Scanner(System.in);
            Point point = new Point(x.nextInt(), y.nextInt());
            siedlerGame.placeSettlement(point);
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for the start of your next road");
            Scanner xroadStart = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for the start of your next road");
            Scanner yroadStart = new Scanner(System.in);
            Point roadStart = new Point(xroadStart.nextInt(), yroadStart.nextInt());
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a x coordinate for the finish of your next road");
            Scanner xroadFinish = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i).getFaction() + " please pick a y coordinate for the finish of your next road");
            Scanner yroadFinish = new Scanner(System.in);
            Point roadEnd = new Point(xroadFinish.nextInt(), yroadFinish.nextInt());
            siedlerGame.placeRoad(roadStart, roadEnd);
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
