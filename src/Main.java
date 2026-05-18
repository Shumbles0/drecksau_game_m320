import cards.*;
import game.GameState;
import model.Deck;
import model.Pig;
import model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static ArrayList<Player> players = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            menu();

            int menuChoice = 0;

            System.out.print("Bitte Auswahl eingeben: ");
            String input = sc.nextLine();

            try {
                menuChoice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe. Bitte eine gültige Zahl eingeben.");
            }

            switch (menuChoice) {
                case 1:
                    Deck.extension = false;
                    startGame(sc);
                    break;
                case 2:
                    Deck.extension = true;
                    startGame(sc);
                    break;
                case 0:
                    System.out.println("Spiel wird beendet");
                    return;
                default:
                    System.out.println("Ungültige Auswahl");
            }
        }
    }

    static void startGame(Scanner sc) {

        System.out.println("Bitte Spieler Anzahl eingeben (2-4)");

        int playerCount = sc.nextInt();

        int pigCount;

        switch (playerCount) {
            case 2:
                pigCount = 5;
                break;
            case 3:
                pigCount = 4;
                break;
            case 4:
                pigCount = 3;
                break;
            default:
                System.out.println("Ungültige Spieleranzahl");
                return;
        }

        createPlayers(players, sc, playerCount, pigCount);

        Deck deck = Deck.createStandardDeck(new Random());

        for (int i = 0; i < 3; i++) {
            for (Player player : players) {
                player.addCard(deck.draw());
            }
        }

        GameState state = new GameState(players, deck);

        gameLoop(state, sc);
    }

    static void gameLoop(GameState state, Scanner sc) {

        while (!state.isGameOver()) {

            Player current = state.getCurrentPlayer();

            System.out.println(current.getNickname() + " ist dran.");
            System.out.println("Bitte wähle die Karte welche du spielen möchtest.");

            List<Card> hand = current.getHand();

            for (int i = 0; i < hand.size(); i++) {
                System.out.println((i + 1) + " - " + hand.get(i).getName());
            }

            int choice;

            while (true) {
                try {
                    System.out.print("Auswahl: ");
                    choice = sc.nextInt() - 1;
                    sc.nextLine();

                    if (choice < 0 || choice >= hand.size()) {
                        System.out.println("Ungültige Kartenauswahl!");
                        continue;
                    }
                    break;
                } catch (Exception e) {
                    System.out.println("Bitte gib eine Zahl ein!");
                    sc.nextLine();
                }
            }

            Card selectedCard = hand.get(choice);

            boolean isTargetOwnPig = false;
            for (int i = 0; i < current.getPigs().size(); i++) {
                if (selectedCard.canPlay(state, current, Target.ofPig(current, i))) {
                    isTargetOwnPig = true;
                    break;
                }
            }

            boolean isTargetOpponent = false;
            for (Player gegner : state.getOpponents(current)) {
                for (int i = 0; i < gegner.getPigs().size(); i++) {
                    if (selectedCard.canPlay(state, current, Target.ofPig(gegner, i))) {
                        isTargetOpponent = true;
                        break;
                    }
                }
            }


            Target target = null;
            if (isTargetOwnPig) {

                for (int i = 0; i < current.getPigs().size(); i++) {
                    System.out.println(i + 1 + ": " + current.getPig(i).toString());
                }
                target = Target.ofPig(current, sc.nextInt() - 1);

            } else if (isTargetOpponent) {

                for (int i = 0; i < state.getOpponents(current).size(); i++) {
                    System.out.println(i + 1 + ": " + state.getOpponents(current).get(i).getNickname());
                }
                Player targetPlayer = state.getOpponents(current).get(sc.nextInt() - 1);

                for (int j = 0; j < targetPlayer.getPigs().size(); j++) {
                    System.out.println(j + 1 + ": " + targetPlayer.getPig(j).toString());

                }
                target = Target.ofPig(targetPlayer, sc.nextInt() - 1);
            }

            // Karte anwenden
            selectedCard.applyCard(state, current, target);
            current.removeCard(selectedCard);
            current.addCard(state.getDeck().draw());

            System.out.println(selectedCard.getName() + " wurde gespielt");

            // Gewinnbedingung prüfen NACH dem Spielen der Karte
            state.checkWinCondition();

            if (state.isGameOver()) {
                System.out.println(state.getWinner().getNickname() + " hat gewonnen!");
            } else {
                state.advanceTurn();
            }
        }
    }

    static void createPlayers(ArrayList<Player> players, Scanner sc, int count, int pigCount) {

        sc.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.println("Bitte Name des " + (i + 1) + ". Spielers eingeben");
            players.add(new Player(sc.nextLine(), pigCount));
        }
    }

    static void menu() {
        System.out.println("1 - Neues Normales Spiel");
        System.out.println("2 - Neues Spiel mit Extension");
        System.out.println("0 - Beenden");
    }
}