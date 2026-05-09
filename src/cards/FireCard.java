package cards;

import game.GameState;
import model.Pig;
import model.Player;

public class FireCard extends Card {

    public FireCard() {
        super("Fire");
    }


    @Override
    public boolean canPlay(GameState state, Player player, Target target) {
        // muss ein anderen spieler targetten
        if (!target.hasPlayer() || target.getTargetPlayer() == player) {
            return false;
        }
        if (!target.hasPigIndex()) {
            return false;
        }


        Pig pig = target.getTargetPlayer().getPig(target.getTargetPigIndex());

        if (!pig.isInBarn()) {
            return false;
        }
        // Barn kann nicht schon brennen
        return !pig.getBarn().isBurning();
    }

    @Override
    public void applyCard(GameState state, Player player, Target target) {
        Pig pig = target.getTargetPlayer().getPig(target.getTargetPigIndex());
        pig.getBarn().commitArson();
    }
}
