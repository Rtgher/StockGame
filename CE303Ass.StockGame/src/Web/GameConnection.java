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

    @WebMethod
    boolean isNotFinished(@WebParam int maxRounds );

    @WebMethod
    boolean isNotFinished();

    @WebMethod
    boolean isFull();

    @WebMethod
    Player getPlayerByName(@WebParam String name);

    @WebMethod
    Map<String, Player> getPlayers();
    /** Creates and returns a new GameState. */
    @WebMethod
    static GameState startNewGame(int nrPlayers, int nrBots)
    {
        return new GameState(nrPlayers,nrBots);
    }


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
