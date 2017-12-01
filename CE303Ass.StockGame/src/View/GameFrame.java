package View;

import GameEngine.Models.Company;
import GameEngine.Models.Player;
import Web.PlayerSocketClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.ConnectException;
import java.rmi.UnexpectedException;
import java.util.*;
import java.util.List;

/**
 *
 */
public class GameFrame extends JFrame implements ActionListener
{
    /** A thread especially for the player to do his thing. */
    Thread playerThread;
    JMenu menu, submenu;
    JButton endTurn;
    JMenuItem startNew;
    JMenuItem joinGame;
    JCheckBoxMenuItem cbMenuItem;
    Map<Company,JButton> plusVoteButtons = new HashMap<>();
    Map<Company,JButton>  negativeVoteButtons =  new HashMap<>();
    Map<Company,JButton>  buyStockButtons = new HashMap<>();
    Map<Company,JButton>  sellStockButtons = new HashMap<>();
    private int gW = 840;
    private int gH = 620;
    private int t = ((int)(gH*0.15));
    private int z = ((int)(gW*0.15));
    List<Company> companies;
    Player player;
    JFrame MainFrame;
    JPanel gamePanel;
    JPanel cardButtonPane;
    List<JPanel> buttonPanels =  new ArrayList<>(5);
    private Map<Company,JLabel> companyLabels =  new HashMap<>();
    private Map<Company,JLabel> cardLabels = new HashMap<>();
    private Map<Company,JLabel> stockUnitNrLabels = new HashMap<>();
    private Map<Company,JLabel> stockValueLabels = new HashMap<>();

    private PlayerSocketClient client;

    public GameFrame(List<Company> companies, Player player)
    {
        this.companies = companies;
        this.player = player;
        MainFrame = new JFrame("Stock Game");
        MainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MainFrame.setSize(100, 50);

        // Create the menu bar.  Make it have a green background.
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(255, 255, 255));
        menuBar.setPreferredSize( new Dimension (25,25) );

        menu = new JMenu("Game");
        menu.setBackground(new Color(255, 255, 255));
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription("The Game menu.");

        menuBar.add(menu);

        //a group of JMenuItems
        startNew = new JMenuItem("Start New Game",
                KeyEvent.VK_T);
        startNew.addActionListener(this);
        startNew.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        startNew.getAccessibleContext().setAccessibleDescription(
                "Starts a new game.");
        startNew.setToolTipText("Start a new Game");
        menu.add(startNew);

        joinGame = new JMenuItem("Join Game");
        joinGame.addActionListener(this);
        joinGame.setToolTipText("Join an existing game.");
        menu.add(joinGame);
        //a group of radio button menu items
        menu.addSeparator();


        //a group of check box menu items
        menu.addSeparator();
        cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        cbMenuItem.addActionListener(this);
        cbMenuItem.setMnemonic(KeyEvent.VK_C);
        menu.add(cbMenuItem);

        cbMenuItem = new JCheckBoxMenuItem("Another one");
        cbMenuItem.setMnemonic(KeyEvent.VK_H);
        menu.add(cbMenuItem);

        //a submenu
        menu.addSeparator();
        submenu = new JMenu("A submenu");
        submenu.setMnemonic(KeyEvent.VK_S);

        menu.add(submenu);

        //Build second menu in the menu bar.
        endTurn = new JButton("END TURN");
        endTurn.setMnemonic(KeyEvent.VK_N);
        endTurn.addActionListener(this);
        endTurn.getAccessibleContext().setAccessibleDescription(
                "Press to finish round");
        endTurn.setToolTipText("Press to finish round.");
        menuBar.add(endTurn);
        cardButtonPane =  new JPanel();
        if(companies!=null)renderGameObjects();

        //Set the menu bar and add the label to the content pane.
        MainFrame.setJMenuBar(menuBar);

        //Display the window.
        MainFrame.pack();
        MainFrame.setVisible(true);

    }

    /**
     * A method to set and redraw all the panels,
     * buttons and labels in the game. */
    private void renderGameObjects()
    {
        gamePanel = new JPanel();
        if(player ==null)
            try{
            player=client.askForPlayer();}
            catch (IOException | ClassNotFoundException ex){JOptionPane.showMessageDialog(this, "Error. Could not retrieve new player from server.", "ERROR ", JOptionPane.ERROR_MESSAGE);
            }
        for(Company company: companies)
        {
            JButton voteYesBtn = new JButton("YES");
            voteYesBtn.addActionListener(this);
            voteYesBtn.setBackground(new Color(255, 255, 255));
            voteYesBtn.setMaximumSize(new Dimension(100, 100));
            voteYesBtn.setVisible(true);
            plusVoteButtons.put(company, voteYesBtn);


            JButton voteNoBtn = new JButton("NO");
            voteNoBtn.addActionListener(this);
            voteNoBtn.setBackground(new Color(255, 255, 255));
            voteNoBtn.setMaximumSize(new Dimension(100, 100));
            voteNoBtn.setVisible(true);
            negativeVoteButtons.put(company,voteNoBtn);


            JLabel cardModifierLbl = new JLabel( (company.getTopCard().getModifier() > 0 ? "+" : "") + company.getTopCard().getModifier(),
                    SwingConstants.CENTER);
            cardLabels.put(company,cardModifierLbl);
            cardModifierLbl.setMaximumSize(new Dimension(150, 0));
            cardModifierLbl.setPreferredSize(new Dimension(0, 50));
            cardModifierLbl.setBackground(new Color(255, 255, 255));
            cardModifierLbl.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            cardModifierLbl.setVisible(true);

            JLabel compLabel = new JLabel(company.getName(), SwingConstants.CENTER);
            compLabel.setMaximumSize(new Dimension(150, 0));
            compLabel.setPreferredSize(new Dimension(0, 50));
            compLabel.setVisible(true);
            companyLabels.put(company,compLabel);

            JPanel buttonPane1 = new JPanel();

            buttonPane1.setLayout(new BoxLayout(buttonPane1, BoxLayout.Y_AXIS));
            buttonPane1.add(Box.createVerticalGlue());
            buttonPane1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPane1.add(compLabel);
            buttonPane1.add(voteYesBtn);
            buttonPane1.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane1.add(cardModifierLbl);
            buttonPane1.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane1.add(voteNoBtn);
            buttonPanels.add(buttonPane1);
        }
        for(JPanel panel : buttonPanels)cardButtonPane.add(panel);
        gamePanel.add(cardButtonPane);
        //Create a yellow label to put in the content pane.
        JPanel mainContent = new JPanel();
        mainContent.setOpaque(true);
        mainContent.setBackground(new Color(112, 113, 161));
        mainContent.setPreferredSize(new Dimension(gW, gH));

        for(Company company : companies)
        {
            JButton buyStockButton = new JButton("BUY");
            buyStockButton.addActionListener(this);
            buyStockButton.setBackground(new Color(255, 255, 255));
            buyStockButton.setMaximumSize(new Dimension(100, 100));
            buyStockButton.setVisible(true);
            buyStockButtons.put(company,buyStockButton);

            JButton sellStockButton = new JButton("SELL");
            sellStockButton.addActionListener(this);
            sellStockButton.setBackground(new Color(255, 255, 255));
            sellStockButton.setMaximumSize(new Dimension(100,100));
            sellStockButton.setVisible(true);
            sellStockButtons.put(company,sellStockButton);

            JLabel stockNrUnitLabel = new JLabel(player.getStocks().get(company).toString());
            stockNrUnitLabel.setMaximumSize(new Dimension(150, 0));
            stockNrUnitLabel.setPreferredSize(new Dimension(0, 50));
            stockNrUnitLabel.setBackground(new Color(255, 255, 255));
            stockNrUnitLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            stockNrUnitLabel.setVisible(true);
            stockUnitNrLabels.put(company,stockNrUnitLabel);

            JLabel stockValueLabel = new JLabel(company.getStockValue() +"");
            stockValueLabel.setMaximumSize(new Dimension(150,50));
            stockValueLabel.setBackground(Color.gray);
            stockValueLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            stockValueLabel.setVisible(true);
            stockValueLabels.put(company,stockValueLabel);

            JLabel compLabel = new JLabel(company.getName(), SwingConstants.CENTER);
            compLabel.setMaximumSize(new Dimension(150, 0));
            compLabel.setPreferredSize(new Dimension(0, 50));
            compLabel.setVisible(true);

            JPanel stockButtonsPane1 = new JPanel();

            stockButtonsPane1.setLayout(new BoxLayout(stockButtonsPane1, BoxLayout.Y_AXIS));
            stockButtonsPane1.add(Box.createVerticalGlue());
            stockButtonsPane1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            stockButtonsPane1.add(compLabel);
            stockButtonsPane1.add(buyStockButton);
            stockButtonsPane1.add(Box.createRigidArea(new Dimension(10, 10)));
            stockButtonsPane1.add(stockNrUnitLabel);
            stockButtonsPane1.add(Box.createRigidArea(new Dimension(10, 10)));
            stockButtonsPane1.add(sellStockButton);
            stockButtonsPane1.add(Box.createRigidArea(new Dimension(10, 10)));
            stockButtonsPane1.add(Box.createRigidArea(new Dimension(10, 10)));
            stockButtonsPane1.add(stockValueLabel);
            mainContent.add(stockButtonsPane1);
        }

        MainFrame.getContentPane().add(gamePanel, BorderLayout.NORTH);
        MainFrame.getContentPane().add(mainContent, BorderLayout.CENTER);
        gamePanel.setVisible(true);
        gamePanel.repaint();
        MainFrame.pack();
        MainFrame.setVisible(true);
        MainFrame.repaint();
    }

    /**
     * Sets the data back.
     * Uses the data already stored.
     */
    public void setData()
    {
        client.getGameState();
        companies = client.getCompaniesInPlay();
        player = client.getPlayer();

        //actually set in the data
        if(plusVoteButtons.isEmpty())
                renderGameObjects();
        String cardstates = "";
        if(companies!=null && companies.size()>0)
        {
            for(Company comp: companies)
            {
                companyLabels.get(comp).setText(comp.getName());
                cardLabels.get(comp).setText((comp.getTopCard().getModifier()>0?"+":"")+comp.getTopCard().getModifier());
                cardstates += " [{0}]".replace("{0}", comp.getTopCard().getModifier() + "");
            }
            System.out.println("Printing cards: " +cardstates);
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Error. No game in progress. Please create or join a game.", "ERROR ", JOptionPane.ERROR_MESSAGE);
        }
        if(player!=null)
        {
            for(Company comp : companies)
            {
                stockUnitNrLabels.get(comp).setText(player.getStocks().get(comp).toString());
                stockValueLabels.get(comp).setText(comp.getStockValue()+"");
            }
        }
    }
    /**
     * Getter for the client of the application.
     * @return
     */
    public PlayerSocketClient getClient()
    {
        return client;
    }

    /**
     * Set the client that is connected to the server.
     * @param client
     */
    public void setClient(PlayerSocketClient client)
    {
        this.client = client;
        playerThread = new Thread(client);
        playerThread.start();
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(startNew))
        {
           JFrame newGameframe = new StartGameFrame(this);
           System.out.println("Starting a new game...");
        }else
        if(e.getSource().equals(endTurn))
        {
            if(client != null)
            {
                client.playerActed();
                try{client.gameConn.playerActed(client.getName());}catch(UnexpectedException ue){ue.printStackTrace();};
                setData();
                System.out.println("Player finished turn.");
            }
        }else
        if(e.getSource().equals(joinGame))
        {
            try
            {
                PlayerSocketClient client = new PlayerSocketClient();
                client.joinGame();
                this.client = client;
                setData();
            }catch (ConnectException cn)
            {
                JOptionPane.showMessageDialog(this, "Warning: failed to join game. Try starting a new server instead.", "ERROR ", JOptionPane.ERROR_MESSAGE);
            }
        }
        for (Company comp : companies)
        {
            if(e.getSource().equals(plusVoteButtons.get(comp)))
            {
                getClient().voteCard(comp.getName(),1);
                getClient().gameConn.voteCard(comp.getName(), 1);//mirror behaviour here.
                System.out.println("Voted YES!");
            }
            else
            if (e.getSource().equals(negativeVoteButtons.get(comp)))
            {
                getClient().voteCard(comp.getName(), -1);
                getClient().gameConn.voteCard(comp.getName(), -1);// mirror behaviour here.
                System.out.println("Voted NO!");
            }
            else
            if(e.getSource().equals(buyStockButtons.get(comp)))
            {
                Player player = client.getPlayer();
                player.modifyStock(comp, +1);
                getClient().tradeStock(client.getName(), player);
                getClient().gameConn.tradeStock(client.getName(),player );// mirror behaviour here.
                setData();
                System.out.println("Bought stock for: "+comp.getName());
            }
            else if (e.getSource().equals(sellStockButtons.get(comp)))
            {
                Player player = client.getPlayer();
                player.modifyStock(comp, -1);
                getClient().tradeStock(client.getName(), player);
                getClient().gameConn.tradeStock(client.getName(),player );// mirror behaviour here.
                setData();
                System.out.println("Sold stock for: "+ comp.getName());
            }
        }

    }

    public static void main(String[] args)
    {
        GameFrame frame = new GameFrame(null, null);
        while(true)
            if(frame.getClient()!=null)
            {
                if (frame.getClient().newStateReceived)
                {
                    //frame.setData();
                    frame.repaint();
                    frame.getClient().newStateReceived = false;
                }
            }
    }



}