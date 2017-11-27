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
        throw new NotImplementedException(); // standin
    }


}
