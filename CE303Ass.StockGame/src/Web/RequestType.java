package Web;

import java.io.Serializable;

public enum RequestType implements Serializable
{
    MESSAGE,
    NOTIFY,
    START_GAME,
    JOIN_GAME,
    VOTE,
    TRADE
}