package model;

import cards.*;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Deck {

    private final List<Card> drawPile;
    private final List<Card> discardPile;
    private final Random random;
    public static boolean extension;

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
            throw new IllegalStateException("keine Karten mehr verfügbar");
        }
        return drawPile.removeLast();
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
     * Entscheidet, was alles in das action karten deck kommt.
     * Kann man ohne probleme verändern, wenn etwas unbalanced ist.
     */
    public static Deck createStandardDeck(Random random) {
        Deck deck = new Deck(random);

        for (int i = 0; i < 21; i++) deck.addCard(new MudCard());
        for (int i = 0; i < 4; i++) deck.addCard(new RainCard());
        for (int i = 0; i < 9; i++) deck.addCard(new BarnCard());
        for (int i = 0; i < 6; i++) deck.addCard(new BarnDoorCard());
        for (int i = 0; i < 4; i++) deck.addCard(new TeslaCard());
        for (int i = 0; i < 4; i++) deck.addCard(new LightningCard());
        for (int i = 0; i < 8; i++) deck.addCard(new FarmerCard());

        if (Deck.extension) {
            for (int i = 0; i < 4; i++) deck.addCard(new FireCard());
            for (int i = 0; i < 8; i++) deck.addCard(new WaterbucketCard());
        }

        deck.shuffle();
        return deck;
    }
}