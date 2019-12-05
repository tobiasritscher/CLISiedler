package ch.zhaw.catan;

import ch.zhaw.hexboard.Label;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UI {

    private static TextIO textIO = TextIoFactory.getTextIO();
    private static TextTerminal<SwingTextTerminal> textTerminal = (SwingTextTerminal) textIO.getTextTerminal();
    private static Dice dice = new Dice();
    private static SiedlerBoard board = new SiedlerBoard();
    private static SiedlerBoardTextView view = new SiedlerBoardTextView(board);
    public static SiedlerGame siedlerGame;
    private static Player player;

    public UI() {

    }

    public static void setupTerminal() {

        //Setting properties of terminal window
        ((SwingTextTerminal) textTerminal).setPromptFontSize(12);
        textTerminal.getProperties().setPaneDimension(1040, 840);
    }

    public static void closeTerminal() {
        textIO.dispose();
    }

    public static boolean buildStartMenu() {
        boolean exit = false;
        List<String> startMenu = new ArrayList<>();

        UI.print("~~~~~~~~~~~~~~~~~~\n");
        UI.print("Settlers of Catan\n");
        UI.print("~~~~~~~~~~~~~~~~~~\n");
        startMenu.add("1. Start a new game");
        startMenu.add("2. Quit\n");
        UI.printList(startMenu);


        Integer choice = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(2)
                .read("Please choose an option: ");

        switch (choice) {
            case 1:
                UI.initSiedlerBoard();
                exit = false;
                break;
            case 2:
                UI.closeTerminal();
                exit = true;
                break;
            default:
                UI.print("This option isn't implemented yet!");
        }
        return exit;
    }

    public static void initSiedlerBoard() {

        initBoard();
//        board.addField(new Point(2, 2), Config.Land.FOREST);
//        board.setCorner(new Point(3, 3), "RR");
//        board.setEdge(new Point(2, 0), new Point(3, 1), "r");
//        board.addFieldAnnotation(new Point(2, 2), new Point(3, 1), "AA");
        Map<Point, ch.zhaw.hexboard.Label> lowerFieldLabel = new HashMap<>();
        lowerFieldLabel.put(new Point(5, 5), new ch.zhaw.hexboard.Label('0', '6'));
        lowerFieldLabel.put(new Point(7, 5), new ch.zhaw.hexboard.Label('0', '3'));
        lowerFieldLabel.put(new Point(9, 5), new ch.zhaw.hexboard.Label('0', '8'));
        lowerFieldLabel.put(new Point(4, 8), new ch.zhaw.hexboard.Label('0', '2'));
        lowerFieldLabel.put(new Point(6, 8), new ch.zhaw.hexboard.Label('0', '4'));
        lowerFieldLabel.put(new Point(8, 8), new ch.zhaw.hexboard.Label('0', '5'));
        lowerFieldLabel.put(new Point(10, 8), new ch.zhaw.hexboard.Label('1', '0'));
        lowerFieldLabel.put(new Point(3, 11), new ch.zhaw.hexboard.Label('0', '5'));
        lowerFieldLabel.put(new Point(5, 11), new ch.zhaw.hexboard.Label('0', '9'));
        lowerFieldLabel.put(new Point(9, 11), new ch.zhaw.hexboard.Label('0', '6'));
        lowerFieldLabel.put(new Point(11, 11), new ch.zhaw.hexboard.Label('0', '9'));
        lowerFieldLabel.put(new Point(4, 14), new ch.zhaw.hexboard.Label('1', '0'));
        lowerFieldLabel.put(new Point(6, 14), new ch.zhaw.hexboard.Label('1', '1'));
        lowerFieldLabel.put(new Point(8, 14), new ch.zhaw.hexboard.Label('0', '3'));
        lowerFieldLabel.put(new Point(10, 14), new ch.zhaw.hexboard.Label('1', '2'));
        lowerFieldLabel.put(new Point(5, 17), new ch.zhaw.hexboard.Label('0', '8'));
        lowerFieldLabel.put(new Point(7, 17), new ch.zhaw.hexboard.Label('0', '4'));
        lowerFieldLabel.put(new Point(9, 17), new ch.zhaw.hexboard.Label('1', '1'));


        for (Map.Entry<Point, Label> e : lowerFieldLabel.entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }
        //Bookmark for a blank screen
        UI.resetBookmark("BLANK_SCREEN");

        //This prints the map
        UI.print(view.toString());


    }

    public static SiedlerBoard printBoard(SiedlerBoard hexBoard) {
        view = new SiedlerBoardTextView(hexBoard);
        textTerminal.println(view.toString());
        return hexBoard;
    }

    protected static void initBoard() {
        Map<Point, Label> lowerFieldLabel = board.getLowerFieldLabels();
        SiedlerBoardTextView view = new SiedlerBoardTextView(board);

        for (Map.Entry<Point, Label> e : lowerFieldLabel.entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }
    }

    public static int askNumberOfPlayers() {
        return textIO.newIntInputReader()
                .withMinVal(2)
                .withMaxVal(4)
                .read("How many players will be playing?");
    }

    public static Config.Resource tradeAsk(){
        Config.Resource choice = null;
        String prompt = "Which resource are you asking?";
        return choice = resourceChoice(prompt);
    }

    public static Config.Resource tradeBid(){
        Config.Resource choice = null;
        String prompt = "Which resource are you bidding?";
        return choice = resourceChoice(prompt);
    }

    public static Config.Resource resourceChoice(String prompt){

        Config.Resource choice = null;
            switch (getEnumValue(Config.Resource.class, prompt)){
                case GRAIN:
                    choice = Config.Resource.GRAIN;
                case WOOL:
                    choice = Config.Resource.WOOL;
                    break;
                case WOOD:
                    choice = Config.Resource.WOOD;
                    break;
                case STONE:
                    choice = Config.Resource.STONE;
                    break;
                case CLAY:;
                    choice = Config.Resource.CLAY;
                    break;
                default:
                    throw new IllegalStateException("Internal error found - Command not implemented.");
        }
        return choice;

    }

    public static void newLine() {
        textTerminal.printf(System.lineSeparator());
    }

    public static int throwDices() {
        int diceNumber = dice.roll();
        String currentPlayer = siedlerGame.getCurrentPlayer().toString();
        textTerminal.printf("Player has thrown a %d", diceNumber);
        return diceNumber;
    }

    public static String setBookmark(String bookmark) {
        textTerminal.setBookmark(bookmark);
        return bookmark;
    }

    public static void resetBookmark(String bookmark) {
        textTerminal.resetToBookmark(bookmark);
    }

    public static String print(String text) {
        textTerminal.print(text);
        return text;
    }

    public static List<String> printList(List<String> elements) {
        textTerminal.print(elements);
        return elements;
    }

    public static <T extends Enum<T>> T getEnumValue(Class<T> commands, String prompt) {
        return textIO.newEnumInputReader(commands)
                .read(prompt);
    }

    public static SiedlerBoard getBoard() {
        return board;
    }
}
