/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

import java.util.ArrayList;

/**
 *
 * @author DemetriosLiousas
 */
public abstract class Deck {

    public ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
    }

    public ArrayList<Card> shuffle(ArrayList<Card> cards) {
        int swap;
        Card temp;
        for (int i = 0; i < cards.size(); i++) {
            swap = (int) (Math.random() * cards.size());
            temp = cards.get(i);
            cards.set(i, cards.get(swap));
            cards.set(swap, temp);
        }
        return cards;
    }

    public Card getCard() {
        Card out = deck.remove(0);
        deck.add(out);
        return out;
    }

}
