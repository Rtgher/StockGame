package Web;

import java.io.Serializable;

public enum RequestType implements Serializable
{
    MESSAGE,
    NOTIFY,
    START_GAME,
    JOIN_GAME,
    VOTE,
    PLAYER_REQ,
    STATE_REQ,
    TRADE
}