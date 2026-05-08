package cards;

import game.GameState;
import model.Pig;
import model.Player;
public class BarnDoorCard extends Card{

    public BarnDoorCard() {
        super("Barn Door");
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
        return pig.isInBarn() && !pig.getBarn().hasDoor();
    }

    @Override

    public void applyCard(GameState state, Player player, Target target) {
        Pig pig = player.getPig(target.getTargetPigIndex());
        pig.getBarn().addDoor();
    }
}
