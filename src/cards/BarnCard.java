package cards;

import game.GameState;
import model.Pig;
import model.Player;

public class BarnCard extends Card{

   public BarnCard() {
        super("Barn");
    }


    @Override
    public boolean canPlay(GameState state, Player player, Target target) {
        if (!target.hasPlayer() || target.getTargetPlayer() != player) {
            return false;
        }
        if (!target.hasPigIndex()) {
            return false;
        }

        Pig pig = player.getPig(target.getTargetPigIndex());
        return !pig.isInBarn();
    }

    @Override

    public void applyCard(GameState state, Player player, Target target) {
        Pig pig = player.getPig(target.getTargetPigIndex());
        pig.buildBarn();
    }
}
