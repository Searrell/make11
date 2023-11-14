import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        initializeDeck();
    }

    private void initializeDeck() {
        cards = new ArrayList<>();
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }

        Collections.shuffle(cards);
    }

    public Card dealCard() {
        if (cards.isEmpty()) {
            return null;  // No more cards in the deck
        }
        return cards.remove(0);
    }

    public ArrayList<Card> dealCards(int numCards) {
        if (cards.size() < numCards) {
            return null;  // Not enough cards in the deck
        }
        ArrayList<Card> dealtCards = new ArrayList<>(cards.subList(0, numCards));
        cards.subList(0, numCards).clear();
        return dealtCards;
    }

    public int size() {
        return cards.size();
    }
}