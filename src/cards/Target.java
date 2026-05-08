package cards;

import model.Player;

public class Target {

    public static final Target NONE = new Target(null, -1);

    private final Player targetPlayer;
    private final int targetPigIndex;

    private Target(Player targetPlayer, int targetPigIndex) {
        this.targetPlayer = targetPlayer;
        this.targetPigIndex = targetPigIndex;
    }

    public static Target ofPig(Player player, int pigIndex) {
        return new Target(player, pigIndex);
    }

    public static Target ofPlayer(Player player) {
        return new Target(player, -1);
    }

    public boolean hasPlayer() {
        return targetPlayer != null;
    }

    public boolean hasPigIndex() {
        return targetPigIndex >= 0;
    }

    public Player getTargetPlayer() {
        return targetPlayer;
    }

    public int getTargetPigIndex() {
        return targetPigIndex;
    }
}