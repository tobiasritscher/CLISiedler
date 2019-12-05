package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.beryx.textio.swing.SwingTextTerminal;

public class Dummy {

    private Integer choice = null;
    private Object Player;
    private Config.Resource resource;

    public Dummy() {
    }


    private void run() {


        //Initializing terminal
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<SwingTextTerminal> textTerminal = (SwingTextTerminal) textIO.getTextTerminal();

        UI.setupTerminal();

        //Setting start point
        textTerminal.setBookmark("MAIN");

        SiedlerGame siedlerGame = new SiedlerGame(20, 4);

        siedlerGame.tradeWithBankFourToOne(UI.tradeBid(), UI.tradeAsk());

    }


    public static void main(String[] args) {
        new Dummy().run();
    }

}
