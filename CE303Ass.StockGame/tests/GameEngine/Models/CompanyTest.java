package GameEngine.Models;

import org.junit.Assert;
import org.junit.Test;

import java.rmi.UnexpectedException;

public class CompanyTest
{
    @Test
    public void CreateNewCompany_CreatesValidCompany()
    {
        Company microsoft = new Company("Microsoft");
        Assert.assertEquals("Name is not correct", "Microsoft", microsoft.getName());
        Assert.assertTrue("Deck is too small.", microsoft.getDeck().size() == 6);
        Assert.assertEquals("Stock value invalid.", 100, microsoft.getStockValue());
    }

    @Test
    public void Company_ResolvesCard_DoesntReturnMissingCard() throws UnexpectedException
    {
        Company hp = new Company("HP");

        hp.shuffleDeck();
        Assert.assertNotEquals("Deck wasn't properly shuffled.", new Company("test").getDeck(), hp.getDeck());

        Card firstTopCard = hp.getTopCard();
        firstTopCard.votes(1);
        hp.resolveCard(firstTopCard);
        Assert.assertNotEquals("Stock wasn't changed.", 100, hp.getStockValue());
        Card secondTopCard = hp.getTopCard();
        Assert.assertNotSame("Same card returned :(", firstTopCard, secondTopCard);

    }
}
