package cards;

import game.GameState;
import model.Pig;
import model.Player;

public class FarmerCard extends Card {

    public FarmerCard() {
        super("Farmer");
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


        if (!pig.isDirty()) {
            return false;
        }

        //check das das schwein nicht beschützt ist
        return !pig.isProtectedFromFarmer();
    }

    @Override
    public void applyCard(GameState state, Player player, Target target) {
        Pig pig = target.getTargetPlayer().getPig(target.getTargetPigIndex());
        pig.makeClean();
    }
}