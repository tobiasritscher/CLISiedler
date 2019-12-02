package ch.zhaw.catan;

import ch.zhaw.hexboard.Label;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UI {


    public UI() {

    }

    public static void setupTerminal(TextIO textIO, TextTerminal<SwingTextTerminal> textTerminal) {

        //Setting properties of terminal window
        ((SwingTextTerminal) textTerminal).setPromptFontSize(12);
        textTerminal.getProperties().setPaneDimension(1040, 800);
    }

    public static void closeTerminal(TextIO textIO, TextTerminal<SwingTextTerminal> textTerminal) {
        textIO.dispose();
    }

    public static boolean buildStartMenu(TextIO textIO, TextTerminal<SwingTextTerminal> textTerminal) {
        boolean exit = false;
        List<String> startMenuElemente = new ArrayList<>();

        textTerminal.print("~~~~~~~~~~~~~~~~~~\n");
        textTerminal.print("Settlers of Catan\n");
        textTerminal.print("~~~~~~~~~~~~~~~~~~\n");
        startMenuElemente.add("1. Start a new game");
        startMenuElemente.add("2. Quit\n");
        textTerminal.print(startMenuElemente);

        Integer choice = textIO.newIntInputReader()
                .withMinVal(1)
                .withMaxVal(2)
                .read("Please choose an option: ");
        textTerminal.println();

        switch (choice) {
            case 1:
                UI.drawSiedlerBoard(textIO, textTerminal);
                exit = false;
                break;
            case 2:
                UI.closeTerminal(textIO, textTerminal);
                exit = true;
                break;
            default:
                textTerminal.print("This option isn't implemented yet!");
        }
        return exit;
    }

    public static void drawSiedlerBoard(TextIO textIO, TextTerminal<SwingTextTerminal> textTerminal) {
        SiedlerBoard board = new SiedlerBoard();

        board.setFields(board);
        SiedlerBoardTextView view = new SiedlerBoardTextView(board);
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
        lowerFieldLabel.put(new Point(7, 11), new ch.zhaw.hexboard.Label('0', '7'));
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
        textTerminal.resetToBookmark("BLANK_SCREEN");

        //This prints the map
        textTerminal.println(view.toString());


    }

    public static int askNumberOfPlayers(TextIO textIO) {
        return textIO.newIntInputReader()
                .withMinVal(2)
                .withMaxVal(4)
                .read("How many players will be playing?");
    }

    public static void newLine(TextTerminal<SwingTextTerminal> textTerminal) {
        textTerminal.printf(System.lineSeparator());
    }
}
