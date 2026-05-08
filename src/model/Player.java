package model;

import cards.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {

    private final String nickname;
    private final List<Pig> pigs;
    private final List<Card> hand;

    public Player(String nickname, int pigCount) {
        this.nickname = nickname;
        this.pigs = new ArrayList<>();
        for (int i = 0; i < pigCount; i++) {
            this.pigs.add(new Pig());
        }
        this.hand = new ArrayList<>();
    }

    public String getNickname() { return nickname; }

    public List<Pig> getPigs() {
        return Collections.unmodifiableList(pigs);
    }

    public Pig getPig(int index) {
        return pigs.get(index);
    }

    public List<Card> getHand() {
        return Collections.unmodifiableList(hand);
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public boolean hasAllPigsDirty() {
        for (Pig pig : pigs) {
            if (!pig.isDirty()) return false;
        }
        return true;
    }
}