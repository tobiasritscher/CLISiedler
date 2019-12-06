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
    public static SiedlerBoard board = new SiedlerBoard();
    private static SiedlerBoardTextView view = new SiedlerBoardTextView(board);

    public UI() {

    }

    public static void setupTerminal() {

        //Setting properties of terminal window
        ((SwingTextTerminal) textTerminal).setPromptFontSize(11);
        ((SwingTextTerminal) textTerminal).setInputFontSize(11);
        textTerminal.getProperties().setPaneDimension(1040, 890);
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
        Map<Point, Label> lowerFieldLabel = new HashMap<>();
        lowerFieldLabel.put(new Point(5, 5), new Label('0', '6'));
        lowerFieldLabel.put(new Point(7, 5), new Label('0', '3'));
        lowerFieldLabel.put(new Point(9, 5), new Label('0', '8'));
        lowerFieldLabel.put(new Point(4, 8), new Label('0', '2'));
        lowerFieldLabel.put(new Point(6, 8), new Label('0', '4'));
        lowerFieldLabel.put(new Point(8, 8), new Label('0', '5'));
        lowerFieldLabel.put(new Point(10, 8), new Label('1', '0'));
        lowerFieldLabel.put(new Point(3, 11), new Label('0', '5'));
        lowerFieldLabel.put(new Point(5, 11), new Label('0', '9'));
        lowerFieldLabel.put(new Point(9, 11), new Label('0', '6'));
        lowerFieldLabel.put(new Point(11, 11), new Label('0', '9'));
        lowerFieldLabel.put(new Point(4, 14), new Label('1', '0'));
        lowerFieldLabel.put(new Point(6, 14), new Label('1', '1'));
        lowerFieldLabel.put(new Point(8, 14), new Label('0', '3'));
        lowerFieldLabel.put(new Point(10, 14), new Label('1', '2'));
        lowerFieldLabel.put(new Point(5, 17), new Label('0', '8'));
        lowerFieldLabel.put(new Point(7, 17), new Label('0', '4'));
        lowerFieldLabel.put(new Point(9, 17), new Label('1', '1'));


        for (Map.Entry<Point, Label> e : lowerFieldLabel.entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }
        //Bookmark for a blank screen
        UI.resetBookmark("BLANK_SCREEN");

        //This prints the map
        UI.print(view.toString());


    }

    public static SiedlerBoard printBoard(SiedlerBoard board) {
        view = new SiedlerBoardTextView(board);
        textTerminal.println(view.toString());
        return board;
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
                .withPossibleValues(2,3,4,69)
                .read("How many players will be playing?");
    }

    public static void newLine() {
        textTerminal.printf(System.lineSeparator());
    }

    public static int throwDices() {
        int diceNumber = Dice.roll();
        textTerminal.printf("Player has thrown a %d", diceNumber);
        return diceNumber;
    }

    /*
    * @param
     */
    public static void setBookmark(String bookmark) {
        textTerminal.setBookmark(bookmark);
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

    public static SiedlerBoard getBoard() {
        return board;
    }

    public static void promptEnter(){
        textIO.newStringInputReader()
                .withMinLength(0)
                .read("\nPress enter to continue");
    }

    /*
    *
     */
    public static void refresh(SiedlerBoard board){
        UI.resetBookmark("BLANK_SCREEN");
        UI.printBoard(board);
    }

    public static int printSecondPhaseMenu(){
        UI.print("1: Trade with bank\n");
        UI.print("2: Build Settlement\n");
        UI.print("3: Build Road\n");
        UI.print("4: Build City\n");
        UI.print("5: Check my resources\n");
        UI.print("6: End my turn\n");
        UI.print("7: Quit game\n");
        return textIO.newIntInputReader().read("What would you like to do now?\n");
    }
}
