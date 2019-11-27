package ch.zhaw.catan;
import java.util.Scanner;
public class PlayGame {
    Scanner scanner = new Scanner(System.in);
    Config config = new Config();
    private Player playerBlue;
    private Player playerGreen;
    private Player playerRed;
    private Player playerYellow;

    public int numberOfPlayers(){
        System.out.println("How many players will be playing?");
        return scanner.nextInt();
    }

    public void createPlayers(){
        int number = numberOfPlayers();
        if(number == 2){
            playerBlue = new Player(Config.Faction.BLUE);
            playerGreen = new Player(Config.Faction.GREEN);
        } else if(number == 3){
            playerBlue = new Player(Config.Faction.BLUE);
            playerGreen = new Player(Config.Faction.GREEN);
            playerRed = new Player(Config.Faction.RED);
        } else if(number == 4){
            playerBlue = new Player(Config.Faction.BLUE);
            playerGreen = new Player(Config.Faction.GREEN);
            playerRed = new Player(Config.Faction.RED);
            playerYellow = new Player(Config.Faction.YELLOW);
        } else{
            System.out.println("Error only between 2 and 4 players are allowed");
        }
    }

    public void firstPhase(){

    }

    public static void main(String []Args){

    }
}
