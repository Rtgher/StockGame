package View;

import Web.PlayerSocketClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.NumberFormat;


/**
 * Used to start a new game.
 */
public class StartGameFrame extends JFrame
{
    JFrame frame;
    int nrP = 2;
    int nrB = 0;
    Frame parent;

    /**
     * The initialiser
     */
    public StartGameFrame(GameFrame parent)
    {

        frame = new JFrame("Start a new Game");
        JPanel panel = new JPanel();
        Format numbersFormat = NumberFormat.getNumberInstance();
        JFormattedTextField nrPlayers = new JFormattedTextField(numbersFormat);
        JLabel playersLabel =  new JLabel("NR Players:");
        JFormattedTextField nrBots = new JFormattedTextField(numbersFormat);
        JLabel botsLabel = new JLabel("NR Bots:");
        nrPlayers.setBorder(BorderFactory.createBevelBorder(1));
        nrPlayers.setToolTipText("The wanted number of players for the game.");
        nrPlayers.setColumns(5);
        nrBots.setBorder(BorderFactory.createBevelBorder(1));
        nrBots.setToolTipText("The wanted number of bots.");
        nrBots.setColumns(5);

        JButton start = new JButton("START");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Sending: P:" + nrP + " B:" + nrB);
                PlayerSocketClient player = new PlayerSocketClient();
                player.createGame(nrP,nrB);
                while(player.getGameState()==null)try{Thread.sleep(100);}catch (InterruptedException ie){}
                parent.setClient(player);
                parent.setData(player.getCompaniesInPlay(),player.getPlayer());
                parent.setFocusable(true);
                frame.setVisible(false);
            }
        });

        nrPlayers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nrP = Integer.parseInt((String)(nrPlayers.getText()));
            }
        });
        nrBots.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nrB = Integer.parseInt((String)(nrBots.getText()));
            }
        });
        panel.add(playersLabel);
        panel.add(nrPlayers);
        panel.add(botsLabel);
        panel.add(nrBots);
        panel.add(start,BorderLayout.SOUTH);
        frame.add(panel);
        Dimension size = new Dimension(300, 200);
        frame.setPreferredSize(size);
        frame.setSize(size);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    /** Getter for the nr of players */
    public int getNrPlayers(){return nrP;}
    /** Getter for the nr of bots */
    public int getNrBots() {
        return nrB;
    }
}
