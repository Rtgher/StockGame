package Web;

import GameEngine.Models.Card;
import GameEngine.Models.Company;
import GameEngine.Models.Mechanics.GameState;
import GameEngine.Models.Player;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Map;

/**
 * The Game Connection interface.
 * Used as template for both socket and webservice connections.
 */
@WebService(name = "StockGame")
public interface GameConnection
{

    /**
     * Getter for the companies in the game.
     * @return -ArrayList of Companies
     */
    @WebMethod
    ArrayList<Company> getCompanies();

    /**
     * Checks if the game is not finished, but not by the
     * default maximum rounds, but by a new specified limit.
     * @param maxRounds -  the max rounds of the game.
     * @return
     */
    @WebMethod
    boolean isNotFinished(@WebParam int maxRounds );

    /**
     * Checks if the game has finished or not.
     * @return
     */
    @WebMethod
    boolean isNotFinished();

    /**
     * Checks if all players have joined the game.
     * @return - boolean
     */
    @WebMethod
    boolean isFull();

    /**
     * Returns a specific player by its name.
     * @param name -  the name of the player.
     * @return
     */
    @WebMethod
    Player getPlayerByName(@WebParam String name);

    /**
     * Get a map of all players.
     * @return Map(PlayerName, PlayerObject)
     */
    @WebMethod
    Map<String, Player> getPlayers();
    /** Creates and returns a new GameState. */
    @WebMethod
    static GameState startNewGame(int nrPlayers, int nrBots)
    {
        return new GameState(nrPlayers,nrBots);
    }

    /**
     * Adds a new player to the game.
     * @param name - name of the player
     * @return
     */
    @WebMethod
    boolean addPlayer(@WebParam String name);

    /**
     * Method to announce that player has acted.
     * @param name - the name of the player who acted.
     */
    @WebMethod
    void playerActed(@WebParam String name) throws UnexpectedException;

    /**
     * Allows the player to vote for a particular card for the company.
     * @param companyName - the company name for which card the vote happens.
     * @param vote - the int vote.
     */
    @WebMethod
    void voteCard(@WebParam String companyName, @WebParam int vote);

    /**
     * Allows the player to trade stock.
     * uses its own object.
     * @param name - the name of the player.
     * @param player - the player object.
     */
    @WebMethod
    void tradeStock(@WebParam String name,@WebParam Player player);

}
