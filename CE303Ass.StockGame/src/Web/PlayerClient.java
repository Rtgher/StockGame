package Web;

import GameEngine.Models.Company;
import GameEngine.Models.Mechanics.GameState;
import GameEngine.Models.Player;

import javax.xml.ws.WebServiceRef;
import java.net.ConnectException;
import java.rmi.UnexpectedException;
import java.util.List;

/**
 * A player client class. Connects to the webserver.
 */
public class PlayerClient implements PlayerConnection
{
    /** A reference to the WebService hosted. */
    @WebServiceRef(wsdlLocation = "http://localhost:8080/Web/game?wsdl")
    static GameState gameConn;

    /** The name of the player.*/
    private String name;

    private int actualVotes = 0;

    public static void main (String[] args)
    {
         PlayerClient client = new PlayerClient();
         //initialise frame here.
    }

    /**
     * Initialiser for a webclient player.
     */
    public PlayerClient()
    {

    }

    /**
     * {@inheritDoc}
     */
    public void joinGame() throws ConnectException
    {
        boolean connected = gameConn.addPlayer(name);
        if(!connected)
            throw new ConnectException("Could not connect to WebService. Try connecting with a different name.");
    }

    /**
     * {@inheritDoc}
     */
    public void voteCard(String companyName, int vote)
    {
        if(actualVotes <= MAX_VOTES){
            actualVotes ++;
            gameConn.voteCard(companyName, vote);
        }
        else return;// do nothing if can't vote anymore.
    }

    /**
     * {@inheritDoc}
     */
    public List<Company> getCompaniesInPlay()
    {
        return gameConn.getCompanies();
    }

    /**
     * {@inheritDoc}
     */
    public void playerActed()
    {
        try {
            gameConn.playerActed(name);
        }
        catch(UnexpectedException ue)
        {
            //print the unexpected exception.
            ue.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    public Player getPlayer()
    {
        return gameConn.getPlayerByName(name);
    }

    /**
     * {@inheritDoc}
     */
    public void createGame(int nrPlayers, int nrBots)
    { gameConn = GameConnection.startNewGame(nrPlayers,nrBots);}


    /**
     * {@inheritDoc}
     */
    public void setName(String name)
    {
        this.name = name;
    }

}
