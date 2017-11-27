package GameEngine.Models;


import org.junit.Assert;
import org.junit.Test;

public class CardTest
{

    @Test
    public void createNewCard_CreatesValidCard()
    {
        Card card = new Card(10);
        Assert.assertNotNull("Error. Card object is null.", card);
        Assert.assertEquals("Wrong Modifier value.", card.getModifier(), 10);
        Assert.assertTrue("isActive value is wrong. Should be True.", card.isActive());
    }

    @Test
    public void validCardWithVotes_resolvesSuccessfully()
    {
        Card card1 =  new Card(20);
        Card card2 =  new Card(-20);
        Card card3 =  new Card(10);
        Card card4 =  new Card(-10);

        card1.votes(1);
        card2.votes(1);
        card3.votes(-1);
        card4.votes(1);
        card4.votes(-1);

        int res1 = card1.resolveCard();
        int res2 = card2.resolveCard();
        int res3 = card3.resolveCard();
        int res4 = card4.resolveCard();

        Assert.assertEquals("Positive card does not resolve properly.", 1,res1);
        Assert.assertEquals("Negative card does not resolve properly.", 1,res2);
        Assert.assertEquals("Card with negative votes resolves, although it shouldn't.", -1, res3);
        Assert.assertEquals("Card with neutral votes is resolved, but shouldn't.", 0, res4);
    }


}
