package Web;

import GameEngine.Models.Company;
import GameEngine.Models.Player;

import java.net.ConnectException;
import java.rmi.UnexpectedException;
import java.util.List;

/**
 * A player that connects via Sockets.
 */
public class PlayerSocketClient implements PlayerConnection
{
    GameConnection gameConn;

    String name;

    int actualVotes = 0;
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
