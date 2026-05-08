package game;

import model.Deck;
import model.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GameState {

    private final List<Player> players;
    private final Deck deck;
    private int currentPlayerIndex;
    private boolean gameOver;
    private Player winner;

    public GameState(List<Player> players, Deck deck) {
        this.players = players;
        this.deck = deck;
        this.currentPlayerIndex = 0;
        this.gameOver = false;
        this.winner = null;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Deck getDeck() {
        return deck;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public void advanceTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void checkWinCondition() {
        for (Player p : players) {
            if (p.hasAllPigsDirty()) {
                this.winner = p;
                this.gameOver = true;
                return;
            }
        }
    }

    public List<Player> getOpponents(Player player) {
        List<Player> opponents = new ArrayList<>();
        for (Player p : players) {
            if (p != player) {
                opponents.add(p);
            }
        }
        return opponents;
    }
}