package Web;

import GameEngine.Models.Company;
import GameEngine.Models.Mechanics.GameState;
import GameEngine.Models.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.rmi.UnexpectedException;
import java.util.List;

/**
 * A player that connects via Sockets.
 */
public class PlayerSocketClient implements PlayerConnection, Runnable
{
    /** The Host string */
    public static final String HOST = "localhost";

    private Socket server;

    GameState gameConn = null;
    /** The name of the player */
    String name;

    private int actualVotes = 0;
    //Object input stream.
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;



    /**
     * Initializer.
     */
    public PlayerSocketClient()
    {
        try {
            server = new Socket(HOST, GameServer.PORT);
            fromServer = new ObjectInputStream(server.getInputStream());
            toServer = new ObjectOutputStream(server.getOutputStream());
            //gameConn = getGameState();
        } catch (IOException io) {
            System.out.println("Failed to connect to server. Please check if server is running.");
            return;
        }


    }

    /**
     * {@inheritDoc}
     */
    public void joinGame() throws ConnectException
    {
        getGameState();
        boolean connected = gameConn.addPlayer(name);
        if(!connected)
            throw new ConnectException("Could not connect to WebService. Try connecting with a different name.");
        sendGameState();
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
            sendGameState();
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
    {
        gameConn = GameConnection.startNewGame(nrPlayers,nrBots);
        sendGameState();
    }


    /**
     * {@inheritDoc}
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Runs the game logic.
     */
    public void run()
    {
        while(gameConn.isNotFinished())
        {
            //do game logic.
            //setName
            //create(orjoin)game
            //
        }
    }

    /**
     * Retrieve the up-to-date Game Connection.
     * @return - the game state.
     */
    public GameState getGameState()
    {
        try
        {
            gameConn = (GameState)fromServer.readObject();
            if(gameConn!= null)
                gameConn.setSocketName(name);
            else
            {
                System.out.println("Asked for connection when no game was started.");
            }
        }catch (ClassNotFoundException cnfe)
        {
            System.out.println("Encountered exception while reading GameState from Server.");
            cnfe.printStackTrace();
        }
        catch(IOException io)
        {
            System.out.println("Encountered exception while reading GameState from Server.");
            io.printStackTrace();
        }
            return gameConn;
    }

    /**
     * Call this method to update the server game state.
     */
    public void sendGameState()
    {
        try{
            toServer.writeObject(gameConn);
        }catch (IOException io)
        {
            System.out.println("Caught IO exception while writing GameState to server.");
            io.printStackTrace();
        }
    }



    public static void main(String[] args)
    {
        PlayerSocketClient client = new PlayerSocketClient();
    }

}
