package model;

import cards.Card;
import cards.MudCard;
// import the other card classes here once they exist

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    private final List<Card> drawPile;
    private final List<Card> discardPile;
    private final Random random;

    public Deck(Random random) {
        this.drawPile = new ArrayList<>();
        this.discardPile = new ArrayList<>();
        this.random = random;
    }

    public void addCard(Card card) {
        drawPile.add(card);
    }

    public void shuffle() {
        Collections.shuffle(drawPile, random);
    }

    public Card draw() {
        if (drawPile.isEmpty()) {
            reshuffleDiscardIntoDraw();
        }
        if (drawPile.isEmpty()) {
            throw new IllegalStateException("No cards left in deck or discard");
        }
        return drawPile.remove(drawPile.size() - 1);
    }

    public void discard(Card card) {
        discardPile.add(card);
    }

    public int drawPileSize() {
        return drawPile.size();
    }

    public int discardPileSize() {
        return discardPile.size();
    }

    private void reshuffleDiscardIntoDraw() {
        drawPile.addAll(discardPile);
        discardPile.clear();
        Collections.shuffle(drawPile, random);
    }

    /**
     * Builds the standard Drecksau deck with all action cards and shuffles it.
     * Verify the exact card counts against your physical rulebook.
     */
    public static Deck createStandardDeck(Random random) {
        Deck deck = new Deck(random);

        for (int i = 0; i < 21; i++) deck.addCard(new MudCard());
        // for (int i = 0; i < 4; i++) deck.addCard(new RainCard());
        // for (int i = 0; i < 9; i++) deck.addCard(new BarnCard());
        // for (int i = 0; i < 6; i++) deck.addCard(new BarnDoorCard());
        // for (int i = 0; i < 4; i++) deck.addCard(new LightningRodCard());
        // for (int i = 0; i < 4; i++) deck.addCard(new LightningCard());
        // for (int i = 0; i < 8; i++) deck.addCard(new FarmerCard());

        deck.shuffle();
        return deck;
    }
}