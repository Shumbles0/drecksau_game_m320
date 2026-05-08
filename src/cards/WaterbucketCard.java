package cards;

import game.GameState;
import model.Pig;
import model.Player;

public class WaterbucketCard extends Card {

    public WaterbucketCard() {
        super("Waterbucket");
    }

    @Override
    public boolean canPlay(GameState state, Player player, Target target) {
        // Mud muss eines deiner eigenen schweine targetten können
        if (!target.hasPlayer() || target.getTargetPlayer() != player) {
            return false;
        }
        if (!target.hasPigIndex()) {
            return false;
        }

        // Target muss brennen
        Pig pig = player.getPig(target.getTargetPigIndex());
        return pig.getBarn().isBurning();
    }

    @Override
    public void applyCard(GameState state, Player player, Target target) {
        Pig pig = player.getPig(target.getTargetPigIndex());
        pig.getBarn().extinguishFire();
    }

}
