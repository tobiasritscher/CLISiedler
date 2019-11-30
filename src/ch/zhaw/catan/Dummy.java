package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

public class Dummy {

    private Integer choice = null;

    public Dummy(){}



    private void run() {
        Actions actions = new Actions();

        //Initializing terminal
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<SwingTextTerminal> textTerminal = (SwingTextTerminal)textIO.getTextTerminal();

        //Setting properties of terminal window
        ((SwingTextTerminal) textTerminal).setPromptFontSize(12);
        textTerminal.getProperties().setPaneDimension(1040,800);

        //Setting start point
        textTerminal.setBookmark("MAIN");

        choice = actions.buildStartMenu(textIO, textTerminal);
        if(choice == 1){
            Actions.drawSiedlerBoard(textIO, textTerminal);
        }


        //textIO.dispose();
    }






    public static void main(String[] args) {
        new Dummy().run();
    }

}
