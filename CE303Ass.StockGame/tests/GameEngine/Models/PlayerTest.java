package GameEngine.Models;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class PlayerTest
{
    @Test
    public void CreatePlayer_CreatesValidPlayer()
    {
        Player player1 = new Player("name");
        Assert.assertEquals("Incorrect name.",player1.getName(), "name");
        Assert.assertEquals("",500, player1.getMoney());
        Assert.assertNull("", player1.getStocks());
        Company comp2 = new Company("comp2");
        Player player2  = new Player("NewName", Arrays.asList(new Company("comp1"), comp2, new Company("comp3"), new Company("comp4")) );
        Assert.assertEquals("Incorrect name.", player2.getName(), "NewName");
        Assert.assertTrue("Map is empty.", player2.getStocks().size() > 1 );
        Assert.assertNotNull("No Stock returned.", player2.getStockForCompany(comp2));
        System.out.println(player2.getStocks().toString());
        int sum = 0;
        for(Integer x : player2.getStocks().values())
        {
            sum += x.intValue();
        }

        Assert.assertEquals("Player has less than 10 starting stocks.", 10, sum);
    }

}
