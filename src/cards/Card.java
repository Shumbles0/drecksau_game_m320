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
//von ki generiert damit über der canplay funktion, wenn man über die inputs hover es anzeigt, für was der input gut ist
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

    //nutzt den effekt der karte tatsächlich im game sollte nur benutzt werden, nachdem canPlay true ist

    public abstract void applyCard(GameState state, Player player, Target target);

    @Override
    public String toString() {
        return name;
    }
}