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

    public UI() {}

    //This function sets up the terminal window and allows to customize font- and size-properties
    public static void setupTerminal() {
        ((SwingTextTerminal) textTerminal).setPromptFontSize(11);
        ((SwingTextTerminal) textTerminal).setInputFontSize(11);
        textTerminal.getProperties().setPaneDimension(1040, 890);
    }

    /**
     * This function closes the terminal window
     */
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
        UI.resetBookmark("BLANK_SCREEN");
        UI.print(view.toString());


    }

    /**
     * This function prints the gameboard
     * @param board The gameboard object
     * @return Necessary return for the test class
     */
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

    /**
     * This functions prompts the user to indicate how many players will be playing
     * @return The number of players that will be playing
     */
    public static int askNumberOfPlayers() {
        return textIO.newIntInputReader()
                .withPossibleValues(2,3,4,69)
                .read("How many players will be playing?");
    }

    /**
     * This function adds a new line in the terminal window
     */
    public static void newLine() {
        textTerminal.printf(System.lineSeparator());
    }

    /**
     * This function
     * @return Is used by the test class
     */
    public static int throwDices() {
        int diceNumber = Dice.roll();
        textTerminal.printf("Player has thrown a %d", diceNumber);
        return diceNumber;
    }

    /**
     * This function sets a bookmark in the terminal window
     * @param bookmark Name of the bookmark that will be set
     */
    public static void setBookmark(String bookmark) {
        textTerminal.setBookmark(bookmark);
    }

    /**
     * This function allows the terminal window to jump to a bookmark that has been set before
     * @param bookmark Name of the bookmark that the window will be reset to
     */
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

    /**
     * This function prints a prompt in the terminal window that asks the user to press enter
     */
    public static void promptEnter(){
        textIO.newStringInputReader()
                .withMinLength(0)
                .read("\nPress enter to continue");
    }

    /**
     * This function refreshes the gameboard, when e.g. a new settlement has been built.
     * It does so by jumping back to a "blank screen" that has been set in the beginning
     * of the game and prints an instance of the gameboard.
     * @param board This parameter indicates the gameboard object
     */
    public static void refresh(SiedlerBoard board){
        UI.resetBookmark("BLANK_SCREEN");
        UI.printBoard(board);
    }

    /**
     * This option prints the menu for the second phase of the game
     * @return Prompts the user to indicate his choice
     */
    public static int printSecondPhaseMenu(){
        PlayGame.ChosenOption[] values = PlayGame.ChosenOption.values();
        for (int i = 1, valuesLength = values.length; i < valuesLength - 1; i++) {
            PlayGame.ChosenOption chosenOption = values[i];
            UI.print(chosenOption.getChosenOptionCode() + ": " + chosenOption.getTextForUser() + "\n");
        }
        UI.print("");
        return textIO.newIntInputReader().read("What would you like to do?\n");
    }
}
