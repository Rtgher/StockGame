package GameEngine.Models.Mechanics;

import GameEngine.Models.Bot;
import GameEngine.Models.Card;
import GameEngine.Models.Company;
import GameEngine.Models.Player;

import java.io.Serializable;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The GameState class.
 * This is the class that actually
 * holds all the information of the game.
 */
public class GameState implements Serializable
{
    /** The list containing the players in the game. */
    private Map<String, Player> players = new HashMap<>();
    private int nrPlayers = 0;
    /** The list containing the companies in play. */
    private ArrayList<Company> companies = new ArrayList<>();
    /** List containing the bot players. */
    private ArrayList<Bot> bots =  new ArrayList<>();
    /** The round variable. Remembers which turn the game is at. */
    private int round;

    /**
     * Basic initialiser.
     * Effectively starts a new game.
     * @param nrPlayers -  a list containing all player names for the game. Will also dictate the # of players.
     * @param nrBots - the number of extra bots to appear in game.
     */
    public GameState(int nrPlayers,  int nrBots)
    {
        this.nrPlayers = nrPlayers;
        round = 1;
        // populate companies list with default companies.
        companies.add(new Company("Microsoft"));
        companies.add(new Company("Google"));
        companies.add(new Company("EA"));
        companies.add(new Company("Tesla"));
        companies.add(new Company("Cisco"));
        for(int i = 0; i< nrBots; i++)
        {
            bots.add(new Bot());
        }
    }

    /**
     * Getter for the whole Map of players.
     * @return - HasMap players.
     */
    public Map<String, Player> getPlayers() {
        return players;
    }

    /**
     * Allows another player to join the session.
     * @param name - the name of the player.
     */
    public void addPlayer(String name)
    {
        Player player = new Player(name);
        players.put(name, player);
    }


    /**
     * Returns the specific player by name.
     * @param name -  the name of the player.
     * @return player - the player object.
     */
    public Player getPlayerByName(String name)
    {
        return players.get(name);
    }

    /**
     * Getter for the companies in the game.
     * @return -ArrayList of Companies
     */
    public ArrayList<Company> getCompanies() {
        return companies;
    }

    /**
     * Checks if the game has passed its 5 round limit and
     * returns the result.
     * @param maxRounds - the maximum number of round for this game.
     * @return true if game is still running - false if the game has ended.
     */
    public boolean isNotFinished( int maxRounds )
    {
        if(this.round < maxRounds +1 )return true;
        else return false;
    }

    /**
     * Asks if enough players have joined.
     * @return - true if yes, false otherwise.
     */
    public boolean isFull()
    {
        return players.size() == nrPlayers;
    }


    /**
     * Resolves teh current round, and progresses the game.
     */
    public void resolveRound() throws UnexpectedException
    {
        round ++;
        for (Bot bot : bots) bot.act(companies); //allows the bots to act.
        for (Company company : companies)
        {
            if(!company.isDeckEmpty())
            {
                company.resolveCard(company.getTopCard());
            }
        }
    }

}
