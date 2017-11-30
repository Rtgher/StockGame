package Web;

import GameEngine.Models.Company;
import GameEngine.Models.Mechanics.GameState;
import GameEngine.Models.Player;
import javafx.scene.control.Alert;
import org.omg.CORBA.Request;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.rmi.UnexpectedException;
import java.util.List;
import java.util.Random;

/**
 * A player that connects via Sockets.
 */
public class PlayerSocketClient implements PlayerConnection, Runnable
{
    /** The Host string */
    public static final String HOST = "localhost";
    /** A string constant to use when player finished the turn */
    public static final String FINISHED = "FINISH";

    private Socket server;

    GameState gameConn = null;
    /** The name of the player */
    String name;

    private int actualVotes = 0;
    //Object input stream.
    private ObjectInputStream fromServer;
    private  ObjectOutputStream toServer;



    /**
     * Initializer.
     */
    public PlayerSocketClient()
    {
        try {
            setName("Test"+new Random().nextInt(100));
            server = new Socket(HOST, GameServer.PORT);
            fromServer = new ObjectInputStream(server.getInputStream());
            toServer = new ObjectOutputStream(server.getOutputStream());
        } catch (IOException io)
        {
            System.out.println("Failed to connect to server. Please check if server is running.");
            return;
        }


    }

    /**
     * {@inheritDoc}
     */
    public void joinGame() throws ConnectException
    {
        GameRequest request =  new GameRequest(RequestType.JOIN_GAME);
        request.setName(name);
        sendRequest(request);
        while(gameConn == null)
        {
            getGameState();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void voteCard(String companyName, int vote)
    {
        if(actualVotes <= MAX_VOTES){
            actualVotes ++;
            GameRequest request = new GameRequest(RequestType.VOTE);
            request.setName(name);
            request.setCompanyName(companyName);
            request.setVote(vote);
            sendRequest(request);
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
        GameRequest request = new GameRequest(RequestType.NOTIFY);
        request.setName(name);
        request.setMessage(FINISHED);
        sendRequest(request);
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
        GameRequest request =  new GameRequest(RequestType.START_GAME);
        request.setNrBots(nrBots);
        request.setNrPlayers(nrPlayers);
        request.setName(name);

        sendRequest(request);
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
            getGameState();
        }

        new Alert(Alert.AlertType.INFORMATION, "Game Over.");

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
            if(gameConn != null) {
                gameConn.setSocketName(name);

            }
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
     * {@inheritDoc}
     * @param name
     * @param player
     */
    @Override
    public void tradeStock(String name, Player player)
    {
        GameRequest request = new GameRequest(RequestType.TRADE);
        request.setName(name);
        request.setPlayer(player);
    }

    /**
     * Call this method to update the server game state.
     */
    public void sendRequest(GameRequest request)
    {
        try
        {
            System.out.println("Sending a "+request.getType()+" request to server.");

            toServer.writeObject(request);

        }catch (IOException io)
        {
            System.out.println("Caught IO exception while writing GameState to server.");
            io.printStackTrace();
        }
    }

}
