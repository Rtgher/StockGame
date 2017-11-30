package Web;

import GameEngine.Models.Mechanics.GameState;
import GameEngine.Models.Player;

import java.io.Serializable;

/**
 * A game request
 */
public class GameRequest implements Serializable
{
    /** The type of the request */
    public final RequestType type;
    /** The message if is a message */
    private String message;

    private GameState state;
    private Player player;

    private String companyName;
    private int vote;
    private String name;

    private int nrPlayers;
    private int nrBots;

    public GameRequest(RequestType type)
    {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getNrBots() {
        return nrBots;
    }

    public int getNrPlayers() {
        return nrPlayers;
    }

    public int getVote() {
        return vote;
    }

    public RequestType getType() {
        return type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getMessage() {
        return message;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNrBots(int nrBots) {
        this.nrBots = nrBots;
    }

    public void setNrPlayers(int nrPlayers) {
        this.nrPlayers = nrPlayers;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
