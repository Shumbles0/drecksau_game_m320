import game.GameState;
import model.Deck;
import model.Player;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Player> players = new ArrayList<Player>();

        while (true) {

            menu();

            switch (sc.nextInt()) {

                case 1:
                    players.clear(); //Alte Spieler Löschen

                    System.out.println("Bitte Spieler Anzahl eingeben(2-4)");
                    sc.nextLine();

                    switch (sc.nextInt()) {
                        case 2:
                            createPlayers(players, sc, 2, 5);
                            break;
                        case 3:
                            createPlayers(players, sc, 3, 4);
                            break;
                        case 4:
                            createPlayers(players, sc, 4, 3);
                            break;
                        default:
                            System.out.println("Bitte valid input machen");
                    }

                    Deck deck = Deck.createStandardDeck(new Random());

                    for (int i = 0; i < 3; i++) {
                        for (Player player : players) {
                            player.addCard(deck.draw());
                        }
                    }

                    GameState state = new GameState(players, deck);

                    break;
                case 0:
                    System.out.println("Spiel wird Beendet");
                    return;
            }
        }

    }

    static void menu() {
        System.out.println("1 - Neues Spiel");
        System.out.println("0 - Beenden");
    }


    static void createPlayers(ArrayList<Player> players, Scanner sc, int count, int pigCount) {
        for (int i = 0; i < count; i++) {
            System.out.println("Bitte Name des " + (i + 1) + ". Spielers eingeben");
            players.add(new Player(sc.nextLine(), pigCount));
        }
    }
}