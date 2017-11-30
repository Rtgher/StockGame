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
        JPanel buttonPane = new JPanel();


        if(companies!=null) {
            //ad button bar after menu at the north

            JButton plus_1 = new JButton("PLUS");
            plus_1.addActionListener(this);
            plus_1.setBackground(new Color(255, 255, 255));
            plus_1.setMaximumSize(new Dimension(100, 100));
            plus_1.setVisible(true);
            plusVoteButtons.add(plus_1);


            JButton minus_1 = new JButton("MINUS");
            minus_1.addActionListener(this);
            minus_1.setBackground(new Color(255, 255, 255));
            minus_1.setMaximumSize(new Dimension(100, 100));
            minus_1.setVisible(true);
            negativeVoteButtons.add(minus_1);


            JLabel label_1 = new JLabel("APPLE", SwingConstants.CENTER);
            label_1.setText((companies.get(0).getTopCard().getModifier()>0?"+":"") +companies.get(0).getTopCard().getModifier());
            cardLabels.add(label_1);
            label_1.setMaximumSize(new Dimension(150, 0));
            label_1.setPreferredSize(new Dimension(0, 50));
            label_1.setBackground(new Color(255, 255, 255));
            label_1.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            label_1.setVisible(true);


            JButton plus_2 = new JButton("PLUS");
            plus_2.addActionListener(this);
            plus_2.setBackground(new Color(255, 255, 255));
            plus_2.setMaximumSize(new Dimension(100, 100));
            plus_2.setVisible(true);
            plusVoteButtons.add(plus_2);

            JButton minus_2 = new JButton("MINUS");
            minus_2.addActionListener(this);
            minus_2.setBackground(new Color(255, 255, 255));
            minus_2.setMaximumSize(new Dimension(100, 100));
            minus_2.setVisible(true);
            negativeVoteButtons.add(minus_2);


            JLabel label_2 = new JLabel("BP", SwingConstants.CENTER);
            label_2.setText((companies.get(1).getTopCard().getModifier()>0?"+":"")+companies.get(1).getTopCard().getModifier());
            cardLabels.add(label_2);
            label_2.setMaximumSize(new Dimension(150, 0));
            label_2.setPreferredSize(new Dimension(0, 50));
            label_2.setBackground(new Color(255, 255, 255));
            label_2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            label_2.setVisible(true);


            JButton plus_3 = new JButton("PLUS");
            plus_3.addActionListener(this);
            plus_3.setBackground(new Color(255, 255, 255));
            plus_3.setMaximumSize(new Dimension(100, 100));
            plus_3.setVisible(true);
            plusVoteButtons.add(plus_3);


            JButton minus_3 = new JButton("MINUS");
            minus_3.addActionListener(this);
            minus_3.setBackground(new Color(255, 255, 255));
            minus_3.setMaximumSize(new Dimension(100, 100));
            minus_3.setVisible(true);
            negativeVoteButtons.add(minus_3);

            JLabel label_3 = new JLabel("CISCO", SwingConstants.CENTER);
            label_3.setText((companies.get(2).getTopCard().getModifier()>0?"+":"")+companies.get(2).getTopCard().getModifier());
            cardLabels.add(label_3);
            label_3.setMaximumSize(new Dimension(150, 0));
            label_3.setPreferredSize(new Dimension(0, 50));
            label_3.setBackground(new Color(255, 255, 255));
            label_3.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            label_3.setVisible(true);


            JButton plus_4 = new JButton("PLUS");
            plus_4.addActionListener(this);
            plus_4.setBackground(new Color(255, 255, 255));
            plus_4.setMaximumSize(new Dimension(100, 100));
            plus_4.setVisible(true);
            plusVoteButtons.add(plus_4);


            JButton minus_4 = new JButton("MINUS");
            minus_4.addActionListener(this);
            minus_4.setBackground(new Color(255, 255, 255));
            minus_4.setMaximumSize(new Dimension(100, 100));
            minus_4.setVisible(true);
            negativeVoteButtons.add(minus_4);

            JLabel label_4 = new JLabel("DELL", SwingConstants.CENTER);
            label_4.setText((companies.get(3).getTopCard().getModifier()>0?"+":"")+companies.get(3).getTopCard().getModifier());
            cardLabels.add(label_4);
            label_4.setMaximumSize(new Dimension(150, 0));
            label_4.setPreferredSize(new Dimension(0, 50));
            label_4.setBackground(new Color(255, 255, 255));
            label_4.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            label_4.setVisible(true);


            JButton plus_5 = new JButton("PLUS");
            plus_5.addActionListener(this);
            plus_5.setBackground(new Color(255, 255, 255));
            plus_5.setMaximumSize(new Dimension(100, 100));
            plus_5.setVisible(true);
            plusVoteButtons.add(plus_5);


            JButton minus_5 = new JButton("MINUS");
            minus_5.addActionListener(this);
            minus_5.setBackground(new Color(255, 255, 255));
            minus_5.setMaximumSize(new Dimension(100, 100));
            minus_5.setVisible(true);
            negativeVoteButtons.add(minus_5);

            JLabel label_5 = new JLabel("ERICSSON", SwingConstants.CENTER);
            label_5.setText((companies.get(4).getTopCard().getModifier()>0?"+":"")+companies.get(4).getTopCard().getModifier());
            cardLabels.add(label_5);
            label_5.setMaximumSize(new Dimension(150, 0));
            label_5.setPreferredSize(new Dimension(0, 50));
            label_5.setBackground(new Color(255, 255, 255));
            label_5.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            label_5.setVisible(true);


            JPanel buttonPane1 = new JPanel();

            JLabel label_11 = new JLabel("APPLE", SwingConstants.CENTER);
            label_11.setText(companies.get(0).getName());
            label_11.setMaximumSize(new Dimension(150, 0));
            label_11.setPreferredSize(new Dimension(0, 50));
            label_11.setVisible(true);
            companyLabels.add(label_11);

            buttonPane1.setLayout(new BoxLayout(buttonPane1, BoxLayout.Y_AXIS));
            buttonPane1.add(Box.createVerticalGlue());
            buttonPane1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPane1.add(label_11);
            buttonPane1.add(plus_1);
            buttonPane1.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane1.add(label_1);
            buttonPane1.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane1.add(minus_1);

            JPanel buttonPane2 = new JPanel();

            JLabel label_22 = new JLabel("BP", SwingConstants.CENTER);
            label_22.setText(companies.get(1).getName());
            label_22.setMaximumSize(new Dimension(150, 0));
            label_22.setPreferredSize(new Dimension(0, 50));
            label_22.setVisible(true);
            companyLabels.add(label_22);

            buttonPane2.setLayout(new BoxLayout(buttonPane2, BoxLayout.Y_AXIS));
            buttonPane2.add(Box.createVerticalGlue());
            buttonPane2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPane2.add(label_22);
            buttonPane2.add(plus_2);
            buttonPane2.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane2.add(label_2);
            buttonPane2.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane2.add(minus_2);


            JPanel buttonPane3 = new JPanel();

            JLabel label_33 = new JLabel("CISCO", SwingConstants.CENTER);
            label_33.setText(companies.get(2).getName());
            label_33.setMaximumSize(new Dimension(150, 0));
            label_33.setPreferredSize(new Dimension(0, 50));
            label_33.setVisible(true);
            companyLabels.add(label_33);

            buttonPane3.setLayout(new BoxLayout(buttonPane3, BoxLayout.Y_AXIS));
            buttonPane3.add(Box.createVerticalGlue());
            buttonPane3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPane3.add(label_33);
            buttonPane3.add(plus_3);
            buttonPane3.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane3.add(label_3);
            buttonPane3.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane3.add(minus_3);


            JPanel buttonPane4 = new JPanel();

            JLabel label_44 = new JLabel("DELL", SwingConstants.CENTER);
            label_44.setText(companies.get(3).getName());
            label_44.setMaximumSize(new Dimension(150, 0));
            label_44.setPreferredSize(new Dimension(0, 50));
            label_44.setVisible(true);
            companyLabels.add(label_44);

            buttonPane4.setLayout(new BoxLayout(buttonPane4, BoxLayout.Y_AXIS));
            buttonPane4.add(Box.createVerticalGlue());
            buttonPane4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPane4.add(label_44);
            buttonPane4.add(plus_4);
            buttonPane4.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane4.add(label_4);
            buttonPane4.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane4.add(minus_4);


            JPanel buttonPane5 = new JPanel();

            JLabel label_55 = new JLabel("ERICSSON", SwingConstants.CENTER);
            label_55.setText(companies.get(4).getName());
            label_55.setMaximumSize(new Dimension(150, 0));
            label_55.setPreferredSize(new Dimension(0, 50));
            label_55.setVisible(true);
            companyLabels.add(label_55);

            buttonPane5.setLayout(new BoxLayout(buttonPane5, BoxLayout.Y_AXIS));
            buttonPane5.add(Box.createVerticalGlue());
            buttonPane5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            buttonPane5.add(label_55);
            buttonPane5.add(plus_5);
            buttonPane5.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane5.add(label_5);
            buttonPane5.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane5.add(minus_5);


            buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
            buttonPane.add(Box.createHorizontalGlue());
            buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 180));
            buttonPane.add(buttonPane1);
            buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane.add(buttonPane2);
            buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane.add(buttonPane3);
            buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane.add(buttonPane4);
            buttonPane.add(Box.createRigidArea(new Dimension(10, 10)));
            buttonPane.add(buttonPane5);
        }
        //Create a yellow label to put in the content pane.
        JLabel mainContent = new JLabel();
        mainContent.setOpaque(true);
        mainContent.setBackground(new Color(248, 213, 131));
        mainContent.setPreferredSize(new Dimension(gW, gH));

        //Set the menu bar and add the label to the content pane.
        gamePanel.setJMenuBar(menuBar);

        gamePanel.getContentPane().add(buttonPane, BorderLayout.NORTH);
        gamePanel.getContentPane().add(mainContent, BorderLayout.CENTER);

        //Display the window.
        gamePanel.pack();
        gamePanel.setVisible(true);

    }

    /**
     * Updates companies and player and then resets data.
     * @param companies
     * @param player
     */
    public void setData(List<Company> companies, Player player)
    {
        while(companies==null || player == null){
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
        if(companies!=null)
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