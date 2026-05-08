package cards;


import game.GameState;
import model.Player;

public abstract class Card {

    private final String name;

    protected Card(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Checks whether this card can legally be played by the given player
     * with the given target in the current game state.
     *
     * @param state    the current game state
     * @param player   the player attempting to play this card
     * @param target   the target of the action (may be null for cards
     *                 like Rain that have no target)
     * @return true if the move is legal, false otherwise
     */
    public abstract boolean canPlay(GameState state, Player player, Target target);

    /**
     * Applies this card's effect to the game state. Should only be called
     * after canPlay() has returned true.
     */
    public abstract void applyCard(GameState state, Player player, Target target);

    @Override
    public String toString() {
        return name;
    }
}