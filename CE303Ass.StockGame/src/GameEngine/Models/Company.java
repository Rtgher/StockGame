package GameEngine.Models;

import java.io.Serializable;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Company class, that holds the details of the stock
 * and cards.
 */
public class Company implements Serializable
{
    /** The name of the company */
    private String name;
    /** The stock value of the company */
    private int stockValue;
    /** The cards list. */
    private List<Card> deck;

    /**
     * Public initializer.
     * Creates a new Company, with stocks.
     * @param name - the name of the company.
     */
    public Company (String name)
    {
        this.name = name;
        this.stockValue = 100;
        this.deck =  Arrays.asList
            (
                new Card(-20),
                new Card(-10),
                new Card( -5),
                new Card(5),
                new Card(10),
                new Card(20)
            );
    }

    /**
     * Returns the deck containing only the active cards.
     * Deck should only be set at start.
     */
    public List<Card> getDeck()
    {
        ArrayList<Card> deck = new ArrayList<>();
        for(Card card: this.deck)
        {
            if(card.isActive())
                deck.add(card);
        }
        return deck;
    }

    /**
     * Shuffles the deck.
     */
    public void shuffleDeck()
    {
        Collections.shuffle(deck);
    }

    /**
     * Resolves the card in play.
     * @param card -  the card to be resolved.
     */
    public void resolveCard(Card card) throws UnexpectedException
    {
        int resolve = card.resolveCard();
        if (resolve != 0)
        {
            int modifier =  card.getModifier();
            switch (resolve)
            {
                case -1 : break;//Do nothing.
                case 1:
                    stockValue += modifier;
                    break;
                default : throw new UnexpectedException("Something weird happened during card resolve. Unexpected value for resolve variable encountered");
            }

        }
    }
    /**
     * Returns the top card of the deck.
     * @return - Card the tp card.
     */
    public Card getTopCard()
    {
        for (Card card : deck)
        {
            if (card.isActive()) return card;
        }
        return  null;
    }

    /**
     * Checks if all the cards in the deck have
     * been resolved or not.
     * @return - true if deck has no unresolved cards; false otherwise.
     */
    public boolean isDeckEmpty()
    {
        for(Card card : deck)
        {
            if(card.isActive()) return false;
        }
        return true;
    }

    /**
     * Getter for the stock value.
     * The stock value should only be modified by
     * cards, when they resolve.
     * @return - the stock value.
     */
    public int getStockValue()
    {
        return stockValue;
    }
    /** Returns the name */
    public String getName()
    {
        return name;
    }
}
