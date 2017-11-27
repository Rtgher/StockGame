package Web;

import GameEngine.Models.Mechanics.GameState;
import GameEngine.Models.Player;

import javax.xml.ws.WebServiceRef;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * The Server the game runs on.
 * Will accept both Socket and Webservice connections.
 */
public class GameServer
{
    /** The Port that the game will run on. */
    public static final int PORT = 8888;
    /** The instance of the Server Socket for bots to connect to. */
    ServerSocket serverSocket;
    /** The game instance. */
    @WebServiceRef(wsdlLocation = "http://localhost:8080/Web/game?wsdl")
    GameConnection game;
    /** The list containing the player names */
    ArrayList<String> playerNames;
    /** The number of players waited for. */
    int nrPlayers = 0;
    int nrBots = 0;
    /** Bool that says whether the server has a game running or not. */
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
            if(game.isFull())
            {
                //start game;
            }
        }
    }
    /**
     * Starts a new game.
     * */
    public void startNewGame( String myName, int nrPlayers, int nrBots)
    {
        game = new GameState(nrPlayers, nrBots);
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
    public boolean connect(String name)
    {
        if(!isRunning)return  false;
        game.addPlayer(name);
        return true;
    }

}
