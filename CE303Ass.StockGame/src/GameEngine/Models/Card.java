package GameEngine.Models;

import java.io.Serializable;

/***
 * A card representing the playable cards in the game.
 */
public class Card implements Serializable
{
    /**
     * The modifier of the card.
     */
    private int modifier;
    /** The variable holding the votes count for this card. */
    private int votes = 0;
    /** Variable that remembers whether the card is active, or has been resolved. */
    private boolean isActive = true;

    /**
     * Constructor for Card.
     * @param modifier -  the stock modifier value
     */
    public Card (int modifier)
    {
        this.modifier = modifier;
    }

    /**
     * Getter for the modifier value.
     * This value should only be set at object initialisation.
     * @return the modifier value
     */
    public int getModifier()
    {
        return this.modifier;
    }

    /**
     * Increments or decrements the card depending on the vote
     * variable.
     * @param vote - the vote: positive or negative.
     */
    public void votes(int vote)
    {
        this.votes += vote;
    }

    /**
     * Getter for votes. Used for copying.
     * @return
     */
    public int getVotes() {
        return votes;
    }

    /**
     * Checks whether the card is active or has
     * already been resolved.
     * @return - is card active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Method to resolve card.
     * @return - 1 if vote passes, -1 if vote fails. 0 otherwise.
     */
    public int resolveCard()
    {
        if(votes !=0)
        {
            System.out.println("Resolved card with value "+ modifier +" for company: " );
            this.isActive = false;
            return votes > 0? 1:-1;
        }
        else return 0;
    }
}
