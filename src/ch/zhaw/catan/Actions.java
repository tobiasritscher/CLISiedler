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

public class Actions {

    private List<String> startMenuElemente = new ArrayList<>();

    public Actions(){

    }


    public Integer buildStartMenu(TextIO textIO, TextTerminal<SwingTextTerminal> textTerminal){
        textTerminal.print("Why are you gae?\n");
        startMenuElemente.add("1. Who says am gae?");
        startMenuElemente.add("2. Because Barack Obama brought gaeness to Uganda\n");
        textTerminal.print(startMenuElemente);

        Integer question = textIO.newIntInputReader()
                .read("Pls answer?");
        textTerminal.println();
        return question;
    }

    public static void drawSiedlerBoard(TextIO textIO,TextTerminal<SwingTextTerminal> textTerminal){
        SiedlerBoard board = new SiedlerBoard();

        board.setFields(board);
        SiedlerBoardTextView view = new SiedlerBoardTextView(board);
        board.addField(new Point(2, 2), Config.Land.FOREST);
        board.setCorner(new Point(3, 3), "RR");
        board.setEdge(new Point(2, 0), new Point(3, 1), "r");
        board.addFieldAnnotation(new Point(2, 2), new Point(3, 1), "AA");
        Map<Point, ch.zhaw.hexboard.Label> lowerFieldLabel = new HashMap<>();
        lowerFieldLabel.put(new Point(2, 2), new ch.zhaw.hexboard.Label('0', '9'));

        for (Map.Entry<Point, Label> e : lowerFieldLabel.entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }
        textTerminal.resetToBookmark("MAIN");

        //This prints the map
        textTerminal.println(view.toString());
    }
}
