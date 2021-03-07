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
public class CommunityChestDeck extends Deck {

    public CommunityChestDeck() {
        super();
        deck.add(new Card("Advance to Go (Collect $200)", 0, 0));
        deck.add(new Card("Bank error in your favor—Collect $200", 200));
        deck.add(new Card("Doctor's fee—Pay $50", -50));
        deck.add(new Card("From sale of stock you get $50", 50));
        deck.add(new Card("Get Out of Jail Free", 0));
        deck.add(new Card("Go to Jail–Go directly to jail–Do not pass Go–Do not collect $200", 0));
        deck.add(new Card("Grand Opera Night—Collect $50 from every player for opening night seats", 0));
        deck.add(new Card("Holiday Fund matures—Receive $100", 100));
        deck.add(new Card("Income tax refund–Collect $20", 20));
        deck.add(new Card("It is your birthday—Collect $10", 10));
        deck.add(new Card("Life insurance matures–Collect $100", 100));
        deck.add(new Card("Pay hospital fees of $100", -100));
        deck.add(new Card("Pay school fees of $150", -150));
        deck.add(new Card("Receive $25 consultancy fee", 25));
        deck.add(new Card("You are assessed for street repairs–$40 per house–$115 per hotel", 0));
        deck.add(new Card("You have won second prize in a beauty contest–Collect $10", 10));
        deck.add(new Card("You inherit $100", 100));
        deck = shuffle(deck);
    }

}
