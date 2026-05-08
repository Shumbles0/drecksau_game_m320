package cards;

import game.GameState;
import model.Pig;
import model.Player;
public class LightningCard extends Card{

    public LightningCard() {
        super("Lightning");
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

        //check das das schwein nicht beschützt ist
        return !pig.isBarnProtectedFromLightning();
    }

    @Override
    public void applyCard(GameState state, Player player, Target target) {
        Pig pig = player.getPig(target.getTargetPigIndex());
        pig.destroyBarn();
    }
}
