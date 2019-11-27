package ch.zhaw.catan;

import java.util.ArrayList;
import java.util.Scanner;

public class PlayGame {
    private Scanner scanner = new Scanner(System.in);
    Config config = new Config();

    public int numberOfPlayers() {
        System.out.println("How many players will be playing?");
        return scanner.nextInt();
    }

    public static void main(String[] Args) {

    }
}
