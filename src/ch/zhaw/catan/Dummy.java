package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

public class Dummy {

    private Integer choice = null;

    public Dummy(){}



    private void run() {


        //Initializing terminal
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<SwingTextTerminal> textTerminal = (SwingTextTerminal)textIO.getTextTerminal();

        UI.setupTerminal(textIO, textTerminal);

        //Setting start point
        textTerminal.setBookmark("MAIN");

        UI.buildStartMenu(textIO, textTerminal);
//        if(choice == 1){
//            UI.drawSiedlerBoard(textIO, textTerminal);
//        } else if (choice == 2){
//            UI.closeTerminal(textIO);
//        }



    }






    public static void main(String[] args) {
        new Dummy().run();
    }

}
