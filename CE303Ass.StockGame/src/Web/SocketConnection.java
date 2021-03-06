package Web;

import GameEngine.Models.Company;
import GameEngine.Models.Mechanics.GameState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class SocketConnection implements Runnable
{
    private ObjectOutputStream toClient;
    private ObjectInputStream fromClient;
    private Socket socket;
    private String name = "";
    /** a reference to the server running. */
    private GameServer server;
    private boolean isRunning;
    /** The number of players waited for. */
    int nrPlayers = 0;
    int nrBots = 0;

    /**
     * Initialiser
     * @param client
     * @param game
     */
    public SocketConnection(Socket client, GameServer game)
    {
        this.server = game;
        this.socket = client;
        isRunning = true;
        try {
            toClient = new ObjectOutputStream(client.getOutputStream());
            fromClient = new ObjectInputStream(client.getInputStream());
        }catch (IOException io)
        {
            System.out.println("Failed to setup connection");
            return;
        }
        System.out.println("Finished setup for new player connection.");
    }

    /**
     * {@inheritDoc}
     */
    public void run()
    {
        while(isRunning) {
            try {
                readRequestsFromClient();
                if(!server.game.isNotFinished())
                    isRunning = false;
                if(socket.isClosed())break;
            } catch (IOException | ClassNotFoundException exc) {
                System.out.println("Caught exception in one of the clients.");
                exc.printStackTrace();
            }
        }//on exit

    }

    /**
     * Starts a new game.
     * */
    public void startNewGame(String myName, int nrPlayers, int nrBots)
    {
        GameState game = new GameState(nrPlayers, nrBots);
        this.nrPlayers = nrPlayers;
        this.nrBots = nrBots;
        game.addPlayer(myName);
        server.setGame(game);

        try
        {
            sendGameStateToClient();
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
        boolean result = server.game.addPlayer(name);
        try
        {
            sendGameStateToClient();
        }catch (IOException io){
            io.printStackTrace();
        }
        if(!result)System.out.println("WARNING: Player was refused connection. Player is : "+name);
        return result;
    }

    /**
     * Looks for differences between the states that players might have done and
     * copies them over to the current gamestate.
     * @param state
     */
    @Deprecated
    private void copyOverState(GameState state)
    {
        if(state==null)return;//stop if gotten a null game.

        System.out.println("%s is doing something.".replace("%s", state.getSocketName()));
        //set any votes the cards might have gotten by iterating through the companies.
        Iterator<Company> serverStateCompaniesIt = server.game.getCompanies().iterator();
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
        server.game.getPlayers().put(playerName, state.getPlayerByName(playerName));
    }

    /**
     * Reads the Gamestate from every client it has.
     */
    private void readRequestsFromClient() throws IOException, ClassNotFoundException
    {
        System.out.println("Reading any input from client: " + name);
        GameRequest request = (GameRequest) fromClient.readObject();

        switch (request.getType()) {
            case START_GAME:
                startNewGame(request.getName(), request.getNrPlayers(), request.getNrBots());
                System.out.println("Game started by "+request.getName());
                name = request.getName();
                break;
            case JOIN_GAME:
                connect(request.getName());
                System.out.println(request.getName() + " has joined.");
                name = request.getName();
                break;
            case VOTE:
                server.game.voteCard(request.getCompanyName(), request.getVote());
                break;
            case NOTIFY:
                if (request.getMessage().equals(PlayerSocketClient.FINISHED))
                    server.game.playerActed(request.getName());
                System.out.println(request.getName() + " has finished his turn.");
                break;
            case TRADE:
                server.game.tradeStock(request.getName(), request.getPlayer());
                System.out.println(request.getName() + " has traded stock.");
                break;
            case MESSAGE:
                System.out.println(request.getMessage());
                break;
            case PLAYER_REQ:
                toClient.writeObject(server.game.getPlayerByName(request.getName()));
                break;
            case STATE_REQ:
                sendGameStateToClient();
                break;
            case GAME_END:
                sendGameStateToClient();
                System.out.println(request.getMessage());
                socket.close();
            default:
                throw new ClassNotFoundException("Invalid type of request.");

        }
        System.out.println("PARSED REQUEST of type "+ request.getType() + " from " + name);
    }

    /**
     * Sends the gamestate to the clients.
     * @throws IOException
     */
    private void sendGameStateToClient() throws IOException
    {
            System.out.println("Sending gamestate to client + " + name);
            toClient.writeObject(server.game);
            System.out.println("Sent game state to client.");
    }

}
