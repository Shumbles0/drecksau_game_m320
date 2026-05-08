package cards;

import game.GameState;
import model.Pig;
import model.Player;

public class MudCard extends Card {

    public MudCard() {
        super("Mud");
    }

@Override
    public boolean canPlay(GameState state, Player player, Target target) {
        // Mud requires targeting your own pig
        if (!target.hasPlayer() || target.getTargetPlayer() != player) {
            return false;
        }
        if (!target.hasPigIndex()) {
            return false;
        }

        // The target pig must be clean (you can't dirty an already-dirty pig)
        Pig pig = player.getPig(target.getTargetPigIndex());
        return pig.isClean();
    }

@Override
    public void applyCard(GameState state, Player player, Target target) {
        Pig pig = player.getPig(target.getTargetPigIndex());
        pig.makeDirty();
    }
}