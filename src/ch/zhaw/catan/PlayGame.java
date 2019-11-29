package ch.zhaw.catan;

import ch.zhaw.hexboard.HexBoard;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.LinkedList;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

    public enum Actions {
        NEW_GAME, QUIT
    }

    private void run() {

//        System.out.println(siedlerGame.getCurrentPlayer());
//        siedlerGame.switchToNextPlayer();
//        System.out.println(siedlerGame.getCurrentPlayer());

        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, PlayGame.Actions.class)) {
                case NEW_GAME:
                    numberOfPlayers = numberOfPlayers();
                    siedlerGame = new SiedlerGame(7, numberOfPlayers);
                    siedlerGame.createPlayers(numberOfPlayers);
                    firstPhase();
                    //TODO: Hier wird ein neues Spiel instanziert
                    break;
                case QUIT:
                    running = false;
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
        scanner = new Scanner(System.in);
        textTerminal.print("How many players will be playing?");
        return scanner.nextInt();
    }

    public void firstPhase() {
        for (int i = 0; i < siedlerGame.getPlayer().size(); i++) {
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for your first settlement");
            Scanner x = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for your first settlement");
            Scanner y = new Scanner(System.in);
            placeSettlement(x.nextInt(), y.nextInt());
            Point point = new Point(x.nextInt(), y.nextInt());
            player.buildSettlement(point);
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for the start of your first road");
            Scanner xroadStart = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for the start of your first road");
            Scanner yroadStart = new Scanner(System.in);
            Point roadpoint = new Point(xroadStart.nextInt(), yroadStart.nextInt());
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for the finish of your first road");
            Scanner xroadFinish = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for the finish of your first road");
            Scanner yroadFinish = new Scanner(System.in);
            Point roadend = new Point(xroadFinish.nextInt(), yroadFinish.nextInt());
            placeRoad(roadpoint, roadend);
            player.buildRoad(roadpoint, roadend);
        }
        for (int i = siedlerGame.getPlayer().size() - 1; i > 0; i--) {
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for your next settlement");
            Scanner x = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for your next settlement");
            Scanner y = new Scanner(System.in);
            Point point = new Point(x.nextInt(), y.nextInt());
            placeSettlement(x.nextInt(), y.nextInt());
            player.buildSettlement(point);
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for the start of your next road");
            Scanner xroadStart = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for the start of your next road");
            Scanner yroadStart = new Scanner(System.in);
            Point roadpoint = new Point(xroadStart.nextInt(), yroadStart.nextInt());
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for the finish of your next road");
            Scanner xroadFinish = new Scanner(System.in);
            textTerminal.print(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for the finish of your next road");
            Scanner yroadFinish = new Scanner(System.in);
            Point roadend = new Point(xroadFinish.nextInt(), yroadFinish.nextInt());
            placeRoad(roadpoint, roadend);
            player.buildRoad(roadpoint, roadend);
        }
    }

    public boolean isCornerFree(Point corner) {
        // TODO create method to check if the neighbor corners are free
        return true;
    }

    public void placeSettlement(int x, int y) {
        Point point = new Point(x, y);
        hexBoard.setCorner(point, hexBoard.getCorner(point));
    }

    public void placeRoad(Point start, Point finish) {
        hexBoard.setEdge(start, finish, hexBoard.getEdge(start, finish));
    }

    public static void main(String[] Args) {
        new PlayGame().run();

    }

}
