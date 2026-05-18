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

            //TODO Target funktion fixen (nur ein target player wählen wenn benötigt)
            // Target bestimmen
            Target target = Target.NONE;

            // Prüfen ob die Karte ein Target braucht9
            if (!selectedCard.canPlay(state, current, Target.NONE)) {

                // Testen ob die Karte sich selbst targetten kann
                List<Player> targets = new ArrayList<>();

                boolean canTargetSelf = false;
                for (int i = 0; i < current.getPigs().size(); i++) {
                    if (selectedCard.canPlay(state, current, Target.ofPig(current, i))) {
                        canTargetSelf = true;
                        break;
                    }
                }

                if (canTargetSelf) targets.add(current);
                targets.addAll(state.getOpponents(current));

                System.out.println("Wähle einen Zielspieler:");
                for (int i = 0; i < targets.size(); i++) {
                    System.out.println((i + 1) + ": " + targets.get(i).getNickname());
                }

                Player targetPlayer;
                while (true) {
                    try {
                        System.out.print("Auswahl: ");
                        int playerChoice = sc.nextInt() - 1;
                        sc.nextLine();
                        if (playerChoice < 0 || playerChoice >= targets.size()) {
                            System.out.println("Ungültige Auswahl!");
                            continue;
                        }
                        targetPlayer = targets.get(playerChoice);
                        break;
                    } catch (Exception e) {
                        System.out.println("Bitte eine Zahl eingeben!");
                        sc.nextLine();
                    }
                }

                // Schwein auswählen
                System.out.println("Wähle ein Schwein von " + targetPlayer.getNickname() + ":");
                for (int i = 0; i < targetPlayer.getPigs().size(); i++) {
                    Pig pig = targetPlayer.getPig(i);
                    String status = pig.isDirty() ? "dreckig" : "sauber";
                    String barn = pig.isInBarn() ? ", in Scheune" : "";
                    System.out.println((i + 1) + ": Schwein " + (i + 1) + " (" + status + barn + ")");
                }

                while (true) {
                    try {
                        System.out.print("Schwein auswählen: ");
                        int pigChoice = sc.nextInt() - 1;
                        sc.nextLine();
                        if (pigChoice < 0 || pigChoice >= targetPlayer.getPigs().size()) {
                            System.out.println("Ungültige Auswahl!");
                            continue;
                        }
                        target = Target.ofPig(targetPlayer, pigChoice);
                        if (!selectedCard.canPlay(state, current, target)) {
                            System.out.println("Diese Karte kann nicht auf dieses Schwein gespielt werden!");
                            continue;
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println("Bitte eine Zahl eingeben!");
                        sc.nextLine();
                    }
                }
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