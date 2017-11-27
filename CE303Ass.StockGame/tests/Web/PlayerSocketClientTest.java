package Web;

import org.junit.Assert;
import org.junit.Test;

import java.net.ConnectException;

import static org.junit.Assert.fail;

public class PlayerSocketClientTest
{
    @Test
    public void testPlayerConnects()
    {
        Object var = new PlayerSocketClient();
        //if errors out will fail.
        Assert.assertNotNull(var);
    }

    @Test
    public void testPlayerCreateNewGame()
    {
        PlayerSocketClient client = new PlayerSocketClient();
        client.setName("John");
        client.createGame(1,0);
        Assert.assertTrue(true);//just to make sure it gets here with no errors.
    }

    @Test
    public void testWaitForSecondPlayerToStart_AssertFromConsoleOutput()
    {
        PlayerSocketClient client1 = new PlayerSocketClient();
        client1.setName("John");
        System.out.println("John is Creating game.");
        client1.createGame(2,0);

        PlayerSocketClient client2 = new PlayerSocketClient();
        client2.setName("Steve");
        try
        {
            System.out.println("Steve Joining.");
            client2.joinGame();
        }catch (ConnectException ce)
        {
            ce.printStackTrace();
            fail();
        }

    }
}
