package Web;

import GameEngine.Models.Mechanics.GameState;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The Server the game runs on.
 * Will accept both Socket and Webservice connections.
 */
@WebService(name = "StockGame")
public class GameServer
{
    /** The Port that the game will run on. */
    public static final int PORT = 8888;
    /** The instance of the Server Socket for bots to connect to. */
    ServerSocket serverSocket;
    /** The game instance. */
    GameState game;
    /** A list containing all connections for the game. */
    List<GameConnection> connectionList =  new ArrayList();
    /** The list containing the player names */
    ArrayList<String> playerNames;
    /** The number of players waited for. */
    int nrPlayers = 0;
    int nrBots = 0;
    /** Bool that says whether the saerver has a game running or not. */
    boolean isRunning = false;

    /**
     * Initialiser.
     */
    public GameServer() throws IOException
    {
        serverSocket = new ServerSocket(PORT);
    }

    /**
     * MAIN METHOD
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        new GameServer().play();
    }

    /** Starts the game. */
    public void play() throws IOException
    {
        while (true)
        {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");
        }
    }
    /**
     * Starts a new game.
     * */
    @WebMethod
    public void startNewGame(@WebParam(name="myName") String myName, @WebParam(name = "nrPlayers") int nrPlayers, @WebParam(name = "nrBots") int nrBots)
    {
        playerNames = new ArrayList<>();
        playerNames.add(myName);
        this.nrPlayers = nrPlayers;
        this.nrBots = nrBots;

        try {
            play();
        }catch (IOException io){
            io.printStackTrace();
        }
    }

    /**
     * Try to connect to a running game.
     * @param name -  the name of the player
     * @return - true if connected successfully, false otherwise.
     */
    @WebMethod
    public boolean connect(@WebParam(name = "name") String name)
    {
        if(!isRunning)return  false;
        game.addPlayer(name);
        return true;
    }


    //////////////EXAMPLE METHOD ///////////////////////////////
    // The method element in the XML will now be SayHello
    @WebMethod(operationName="SayHello")
    // @WebParam will rename the input from arg0 to name
    public String sayHello(@WebParam(name="name") String name){
        return "Hello " + name + "!";
    }
}
