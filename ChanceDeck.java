/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package monopoly;

/**
 *
 * @author DemetriosLiousas
 */
public class ChanceDeck extends Deck {

    public ChanceDeck() {
        super();
        //msg, value, bindex
        //msg, value
        deck.add(new Card("Advance to Go (Collect $200)", 0, 0));
        deck.add(new Card("Advance to Illinois Ave—If you pass Go, collect $200", 0, 24));
        deck.add(new Card("Advance to St. Charles Place – If you pass Go, collect $200", 0, 11));
        deck.add(new Card("Advance token to nearest Utility. If unowned, you may buy it from the Bank. "
                + "If owned, throw dice and pay owner a total ten times the amount thrown.", 0, -1));
        deck.add(new Card("Advance token to the nearest Railroad and pay owner twice the rental to which he/she {he} is otherwise entitled. "
                + "If Railroad is unowned, you may buy it from the Bank.", 0, -1));
        deck.add(new Card("Bank pays you dividend of $50", 50));
        deck.add(new Card("Get Out of Jail Free", 0, -1));
        deck.add(new Card("Go Back 3 Spaces", 0, -3));
        deck.add(new Card("Go to Jail–Go directly to Jail–Do not pass Go, do not collect $200", 0, 10));
        deck.add(new Card("Make general repairs on all your property–For each house pay $25–For each hotel $100", 0, -1));
        deck.add(new Card("Pay poor tax of $15", -15));
        deck.add(new Card("Take a trip to Reading Railroad–If you pass Go, collect $200", 0, 5));
        deck.add(new Card("Take a walk on the Boardwalk–Advance token to Boardwalk", 0, 39));
        deck.add(new Card("You have been elected Chairman of the Board–Pay each player $50", 0, -1));
        deck.add(new Card("Your building and loan matures—Collect $150", 150));
        deck.add(new Card("You have won a crossword competition—Collect $100", 100));
        deck = shuffle(deck);
    }
}
