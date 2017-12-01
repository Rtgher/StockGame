package Web;

import GameEngine.Models.Company;
import GameEngine.Models.Mechanics.GameState;
import org.omg.CORBA.Request;

import javax.xml.ws.WebServiceRef;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
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
    public GameConnection game;
    /** The list containing the player names */
    ArrayList<String> playerNames;
    /** The number of players waited for. */
    int nrPlayers = 0;
    int nrBots = 0;
    /** Bool that says whether the server has a game running or not. */
    boolean isRunning = false;
    private List<SocketConnection> clientList;
    private List<Thread> connections = new ArrayList<>();



    /**
     * Initialiser.
     */
    public GameServer() throws IOException
    {

        try{
            serverSocket = new ServerSocket(PORT);
        }catch (BindException be)
        {
            System.out.println("Socket was already bound. Trying port " + (PORT +1));
            serverSocket = new ServerSocket(PORT + 1);
        }
        clientList =  new ArrayList<>();
        System.out.println("Server Started.");
    }

    /**
     * MAIN METHOD
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        new GameServer().play();
    }

    /** Starts the game. */
    public void play() throws IOException, ClassNotFoundException
    {

        while (true)
        {
            Socket client = serverSocket.accept();
            System.out.println("Client connected.");
            SocketConnection gameCon = new SocketConnection(client, this);

            if(game != null)
            {
                //start game;
                System.out.println("Game created in memory.");
            }
            addConn(gameCon);

        }
    }

    public GameConnection getGame(){return game;}

    /** Start a new Thread and add it to the connection list */
    private void addConn(SocketConnection gameCon)
    {
        clientList.add(gameCon);
        Thread newConn = new Thread(gameCon);
        connections.add(newConn);
        newConn.start();
    }
    public void setGame(GameState game)
    {
        this.game = game;
    }
}
