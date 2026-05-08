package cards;


import game.GameState;
import model.Pig;
import model.Player;

public class RainCard extends Card {

    public RainCard() {
        super("Rain");
    }

    @Override
    public boolean canPlay(GameState state, Player player, Target target) {
        // hat kein requirement also leer man könnte target=NONE machen aber nicht nötig
        return true;
    }

    @Override
    public void applyCard(GameState state, Player player, Target target) {
        for (Player p : state.getPlayers()) {
            for (Pig pig : p.getPigs()) {
                if (pig.isDirty() && !pig.isProtectedFromRain()) {
                    pig.makeClean();
                }
                else if (pig.isInBarn()) {
                    if ((pig.getBarn().isBurning())) {
                        pig.getBarn().extinguishFireRain();
                    }
                }
            }
        }
    }
}