package View;

import GameEngine.Models.Company;
import GameEngine.Models.Player;
import Web.PlayerSocketClient;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.ConnectException;
import java.util.*;
import java.util.List;

/**
 *
 */
public class GameFrame extends JFrame implements ActionListener
{

    JMenu menu, submenu;
    JButton endTurn;
    JMenuItem startNew;
    JMenuItem joinGame;
    JCheckBoxMenuItem cbMenuItem;
    List<JButton> plusVoteButtons = new ArrayList<>();
    List<JButton> negativeVoteButtons =  new ArrayList();
    private int gW = 840;
    private int gH = 620;
    private int t = ((int)(gH*0.15));
    private int z = ((int)(gW*0.15));
    List<Company> companies;
    Player player;
    JFrame gamePanel;
    JPanel cardButtonPane;
    List<JPanel> buttonPanels =  new ArrayList<>(5);
    private List<JLabel> companyLabels =  new ArrayList<>();
    private List<JLabel> cardLabels = new ArrayList<>();

    private PlayerSocketClient client;

    public GameFrame(List<Company> companies, Player player)
    {
        this.companies = companies;
        this.player = player;
        gamePanel = new JFrame("Stock Game");
        gamePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        //Create a yellow label to put in the content pane.
        JLabel mainContent = new JLabel();
        mainContent.setOpaque(true);
        mainContent.setBackground(new Color(248, 213, 131));
        mainContent.setPreferredSize(new Dimension(gW, gH));

        //Set the menu bar and add the label to the content pane.
        gamePanel.setJMenuBar(menuBar);

        gamePanel.getContentPane().add(cardButtonPane, BorderLayout.NORTH);
        gamePanel.getContentPane().add(mainContent, BorderLayout.CENTER);

        //Display the window.
        gamePanel.pack();
        gamePanel.setVisible(true);

    }

    /**
     * A method to set and redraw all the panels,
     * buttons and labels in the game. */
    private void renderGameObjects()
    {
        for(Company company: companies)
        {
            JButton voteYesBtn = new JButton("YES");
            voteYesBtn.addActionListener(this);
            voteYesBtn.setBackground(new Color(255, 255, 255));
            voteYesBtn.setMaximumSize(new Dimension(100, 100));
            voteYesBtn.setVisible(true);
            plusVoteButtons.add(voteYesBtn);


            JButton voteNoBtn = new JButton("NO");
            voteNoBtn.addActionListener(this);
            voteNoBtn.setBackground(new Color(255, 255, 255));
            voteNoBtn.setMaximumSize(new Dimension(100, 100));
            voteNoBtn.setVisible(true);
            negativeVoteButtons.add(voteNoBtn);


            JLabel cardModifierLbl = new JLabel( (company.getTopCard().getModifier() > 0 ? "+" : "") + company.getTopCard().getModifier(),
                    SwingConstants.CENTER);
            cardLabels.add(cardModifierLbl);
            cardModifierLbl.setMaximumSize(new Dimension(150, 0));
            cardModifierLbl.setPreferredSize(new Dimension(0, 50));
            cardModifierLbl.setBackground(new Color(255, 255, 255));
            cardModifierLbl.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            cardModifierLbl.setVisible(true);

            JLabel compLabel = new JLabel(company.getName(), SwingConstants.CENTER);
            compLabel.setMaximumSize(new Dimension(150, 0));
            compLabel.setPreferredSize(new Dimension(0, 50));
            compLabel.setVisible(true);
            companyLabels.add(compLabel);

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
        cardButtonPane.repaint();
    }

    /**
     * Updates companies and player and then resets data.
     * @param companies
     * @param player
     */
    public void setData(List<Company> companies, Player player)
    {
        while(companies==null ){// || player == null
            client.getGameState();
            companies = client.getCompaniesInPlay();
            player = client.getPlayer();
        }
        this.companies = companies;
        this.player = player;
        setData();
    }
    /**
     * Sets the data back.
     * Uses the data already stored.
     */
    public void setData()
    {
        if(companies!=null && companies.size()>0)
        {
            for(int i=0; i<companies.size();i++)
            {
                companyLabels.get(i).setText(companies.get(i).getName());
                cardLabels.get(i).setText((companies.get(i).getTopCard().getModifier()>0?"+":"")+companies.get(i).getTopCard().getModifier());
            }
        }
        else
        {
            new Alert(Alert.AlertType.ERROR, "Error. No game in progress. Please create or join a game.");
        }
        renderGameObjects();
        repaint();
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
    public void setClient(PlayerSocketClient client) {
        this.client = client;
    }

    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(startNew))
        {
           JFrame newGameframe = new StartGameFrame(gamePanel);
           System.out.println("Starting a new game...");
        }else
        if(e.getSource().equals(endTurn))
        {
            if(client != null)
            {
                client.playerActed();
                System.out.println("Player finished turn.");
            }
        }else
        if(e.getSource().equals(joinGame))
        {
            try
            {
                client = new PlayerSocketClient();
                client.joinGame();
                setData(client.getCompaniesInPlay(), client.getPlayer());//TODO: repair this. player is null
            }catch (ConnectException cn)
            {
                new Alert(Alert.AlertType.ERROR, "Warning: failed to join game. Try starting a new server instead.");
            }
        }
        for (int i=0;i<plusVoteButtons.size();i++)
        {
            if(e.getSource().equals(plusVoteButtons.get(i)))
            {
                getClient().voteCard(companies.get(i).getName(),1);
                System.out.println("Voted YES!");
            }
            else if (e.getSource().equals(negativeVoteButtons.get(i)))
            {
                getClient().voteCard(companies.get(i).getName(), -1);
                System.out.println("Voted NO!");
            }
        }

    }

    public static void main(String[] args)
    {
        new GameFrame(null, null);
    }



}