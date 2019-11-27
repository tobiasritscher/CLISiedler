package ch.zhaw.catan;

import ch.zhaw.hexboard.HexBoard;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;



public class PlayGame {
    HexBoard hexBoard = new HexBoard();
    SiedlerGame siedlerGame = new SiedlerGame(7,numberOfPlayers());
    private Scanner scanner;
    Config config = new Config();
    private Config.Faction faction;
    Player player = new Player(faction);

    public enum Actions {
        NEW_GAME, QUIT
    }

    private void run() {


        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> textTerminal = textIO.getTextTerminal();

        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, PlayGame.Actions.class)) {
                case NEW_GAME:
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
        System.out.println("How many players will be playing?");
        return scanner.nextInt();
    }

    public void firstPhase(){
        for(int i = 0; i < siedlerGame.getPlayer().size(); i++){
            System.out.println(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for your first settlement");
            Scanner x = new Scanner(System.in);
            System.out.println(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for your first settlement");
            Scanner y = new Scanner(System.in);
            Point point = new Point(x.nextInt(),y.nextInt());
            hexBoard.setCorner(point,hexBoard.getCorner(point));
            player.buildSettlement(point);
            System.out.println(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for the start of your first road");
            Scanner xroadStart = new Scanner(System.in);
            System.out.println(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for the start of your first road");
            Scanner yroadStart = new Scanner(System.in);
            Point roadpoint = new Point(xroadStart.nextInt(),yroadStart.nextInt());
            System.out.println(siedlerGame.getPlayer().get(i) + "please pick a x coordinate for the finish of your first road");
            Scanner xroadFinish = new Scanner(System.in);
            System.out.println(siedlerGame.getPlayer().get(i) + "please pick a y coordinate for the finish of your first road");
            Scanner yroadFinish = new Scanner(System.in);
            Point roadend = new Point(xroadFinish.nextInt(),yroadFinish.nextInt());
            hexBoard.setEdge(roadpoint,roadend,hexBoard.getEdge(roadpoint,roadend));
            // TODO cant place the road for a player
        }
    }
    public static void main(String[] Args) {
        new PlayGame().run();

    }
}
