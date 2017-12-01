package GameEngine.Models;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Random;

/**
 * The Bot class that hold the logic for the bot.
 */
public class Bot extends Player
{
    /** Array of random names for the bot. */
    private static String[] names = new String[]{"Steve Jobs", "Bill Gates", "Warren Buffett", "Larry Ellison", "Charles Koch", "David Koch", "Jim Walton", "Michael Bloomberg", "Mark Zuckerberg", "Elon Musk", "Yuri Gagarin"};

    /**
     * Creates a new Bot which inherits from player.
     */
    public Bot()
    {
        super(names[new Random().nextInt(names.length)] +" [BOT]");
    }

    /**
     * Lets the bot act.
     * @param companies
     */
    public void act(List<Company> companies)
    {
        for(Company company: companies)
        {
            if(company.getStockValue() > 100)
            {
                if( getStockForCompany(company) > 0 )
                modifyStock(company, -1);
            }else
                if(company.getStockValue()<100)
                {
                    if(getStockForCompany(company)<8 && getMoney()>company.getStockValue())
                        modifyStock(company,1);
                }
        }
        //vote randomly.
        companies.get(new Random().nextInt(5)).getTopCard().votes(1);
        companies.get(new Random().nextInt(5)).getTopCard().votes(-1);
        System.out.println("Bot {0} has acted. His cash is {1}".replace("{0}", getName()).replace("{1}", getMoney()+""));
    }


}
