package GameEngine.Models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * The player class.
 * Contains a player object that can do stuff in the game.
 */
public class Player implements Serializable
{
    /** The total money available to the player. */
    private int money;

    /**
     * A stock map that holds the information regarding
     * to how many stocks the player owns for each company in play.
     */
    private Map<Company, Integer> stocks;
    /** The  name of the player. */
    private String name;

    /**
     * The initialiser.
     * @param name - the name of the player.
     */
    public Player(String name)
    {
        this.name = name;
        this.money = 500;
    }

    /**
     * Initialiser that also creates a random stock map.
     * @param name - The name of the player.
     * @param companies - The list containing the Companies in play.
     */
    public Player(String name, List<Company> companies)
    {
        this(name);
        int shares = 10;
        stocks = new HashMap<>();
        for (Company comp : companies)
        {
            int share = shares > 0 ? new Random().nextInt(shares) : 0;
            shares -= share;
            stocks.put(comp, new Integer(share));
        }

        if(shares>0)
        {
            int pos = new Random().nextInt(stocks.size());
            int i = 0;
            for(Company comp : stocks.keySet())
            {
                if (i == pos)
                    stocks.put(comp, stocks.get(comp) + shares);
                i++;
            }
        }
    }
    /** Getter for name */
    public String getName() {
        return name;
    }
    /** Getter for stocks. */
    public Map<Company, Integer> getStocks() {
        return stocks;
    }

    /**
     *  Get the stock value for the given company.
     *  @param company - Company the company.
     */
    public Integer getStockForCompany(Company company)
    {
        return stocks.get(company);
    }

    /**
     * Method to simulate a buy/sell stock action.
     * @param key -The company for which stock is modified.
     * @param amount - the amount by which the stock is modified.
     */
    public void modifyStock(Company key, Integer amount)
    {
        if(amount.intValue() <0 && stocks.get(key) > 0)
        {
            stocks.put(key, stocks.get(key) + amount);
            money += amount.intValue() * key.getStockValue();
        }else if(amount.intValue() > 0 && getMoney()> key.getStockValue())
        {
            stocks.put(key, stocks.get(key) + amount);
            money -= amount.intValue() * key.getStockValue();
        }
    }

    /** Getter for the Money of the player. */
    public int getMoney() {
        return money;
    }
}
