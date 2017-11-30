package Web;

import GameEngine.Models.Company;
import GameEngine.Models.Player;

import java.net.ConnectException;
import java.util.List;

/**
 * An interface to declare a player connection.
 * Contains method declarations required to play the game.
 */
public interface PlayerConnection
{
    int MAX_VOTES = 2;

    /**
     * Creates a new game.
     * @param nrPlayers
     * @param nrBots
     */
    void createGame(int nrPlayers, int nrBots);

    /**
     * A trigger for the player to join a game.
     */
    void joinGame() throws ConnectException;

    /**
     * Triggers a vote for the player.
     * @param companyName -  the company name for which card it is voting.
     * @param vote - the actual vote as an int. 1 is yes, -1 is no.
     */
    void voteCard(String companyName, int vote);

    /**
     * Retrieves the companies in play.
     * Use it to get the top cards from each company, the stock values, and
     * the names of the companies.
     * @return
     */
    List<Company> getCompaniesInPlay();

    /**
     * Notify the game client that the player has acted.
     */
    void playerActed();

    /**
     * Set, or change the name of the player.
     * @param name - the name
     */
    void setName(String name);

    /**
     * Fetches the player data from the game.
     * @return Player player - the player obj associated with this obj.
     */
    Player getPlayer();

    /**
     * allows the player to trade stock.
     * @param name
     * @param player
     */
    void tradeStock(String name, Player player);

}
