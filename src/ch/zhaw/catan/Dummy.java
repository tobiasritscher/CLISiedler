package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.Label;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Dummy {

    public enum Actions {
        SHOW, QUIT
    }

    private void run() {
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> textTerminal = textIO.getTextTerminal();
        textTerminal.getProperties().setPaneDimension(1280,960);

        SiedlerBoard board = new SiedlerBoard();
        textTerminal.setBookmark("MAIN");
        board.setFields(board);
        SiedlerBoardTextView view = new SiedlerBoardTextView(board);
       board.addField(new Point(2, 2), Land.FOREST);
        board.setCorner(new Point(3, 3), "RR");
        board.setEdge(new Point(2, 0), new Point(3, 1), "r");
        board.addFieldAnnotation(new Point(2, 2), new Point(3, 1), "AA");
      Map<Point, Label> lowerFieldLabel = new HashMap<>();
        lowerFieldLabel.put(new Point(2, 2), new Label('0', '9'));

        for (Map.Entry<Point, Label> e : lowerFieldLabel.entrySet()) {
            view.setLowerFieldLabel(e.getKey(), e.getValue());
        }
        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, Actions.class)) {
                case SHOW:
                    textTerminal.println(view.toString());

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

    public static void main(String[] args) {
        new Dummy().run();
    }

}
