package Web;

import GameEngine.Models.Company;
import GameEngine.Models.Mechanics.GameState;
import GameEngine.Models.Player;

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
    GameConnection game;
    /** The list containing the player names */
    ArrayList<String> playerNames;
    /** The number of players waited for. */
    int nrPlayers = 0;
    int nrBots = 0;
    /** Bool that says whether the server has a game running or not. */
    boolean isRunning = false;
    private List<Socket> clientList;
    private ObjectOutputStream toClient;
    private ObjectInputStream fromClient;

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
            toClient = new ObjectOutputStream(client.getOutputStream());
            toClient.writeObject(game);
            fromClient = new ObjectInputStream(client.getInputStream());
            if(!clientList.contains(client))
                clientList.add(client);

            if(game != null && game.isFull())
            {
                //start game;
                System.out.println("Game Started.");
                for(Socket player : clientList)
                {
                    fromClient = new ObjectInputStream(player.getInputStream());
                    GameState playerState = (GameState) fromClient.readObject();
                    copyOverState(playerState);
                }
            }else
                if(game == null)
                    game = (GameState) fromClient.readObject();
        }
    }
    /**
     * Starts a new game.
     * */
    public void startNewGame(String myName, int nrPlayers, int nrBots)
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
        }catch ( ClassNotFoundException cnfe)
        {
            cnfe.printStackTrace();
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

    /**
     * Looks for differences between the states that players might have done and
     * copies them over to the current gamestate.
     * @param state
     */
    private void copyOverState(GameState state)
    {
        System.out.println("%s is doing something.".replace("%s", state.getSocketName()));
        //set any votes the cards might have gotten by iterating through the companies.
        Iterator<Company> serverStateCompaniesIt = game.getCompanies().iterator();
        Iterator<Company> clientStateCompaniesIt = state.getCompanies().iterator();

        while(serverStateCompaniesIt.hasNext()
                && clientStateCompaniesIt.hasNext())
        {
            Company serverSideCompany = serverStateCompaniesIt.next();
            Company clientSideCompany = clientStateCompaniesIt.next();
            serverSideCompany.getTopCard().votes(clientSideCompany.getTopCard().getVotes());
        }
        String playerName = state.getSocketName();
        //Change own player details.
        game.getPlayers().put(playerName, state.getPlayerByName(playerName));
    }


}
