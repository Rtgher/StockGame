package View;

import GameEngine.Models.Company;
import GameEngine.Models.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class GameFrame extends JFrame implements ActionListener
{



    /** Size Main Panel. */

    JMenu menu, submenu;
    JMenuItem menuItem;
    JRadioButtonMenuItem rbMenuItem;
    JCheckBoxMenuItem cbMenuItem;
    List<JButton> buttons = new ArrayList<>();
    private int gW = 840;
    private int gH = 620;
    private int t = ((int)(gH*0.15));
    private int z = ((int)(gW*0.15));
    List<Company> companies;
    Player player;


    public GameFrame(List<Company> companies, Player player) {
        this.companies = companies;
        this.player = player;
        JFrame gamePanel = new JFrame("Mihai Patrascanu");
        gamePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       // Create the menu bar.  Make it have a green background.
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setBackground(new Color(255, 255, 255));
        menuBar.setPreferredSize( new Dimension (25,25) );

        menu = new JMenu("A Menu");
        menu.setBackground(new Color(255, 255, 255));

        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");

        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("A text-only menu item",
                KeyEvent.VK_T);
        menuItem.addActionListener(this);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This doesn't really do anything");
        menu.add(menuItem);


        //a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        rbMenuItem.addActionListener(this);
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Another one");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);


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

        menuItem = new JMenuItem("An item in the submenu");
        menuItem.addActionListener(this);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        menu.add(submenu);

        //Build second menu in the menu bar.
        menu = new JMenu("Another Menu");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu does nothing");
        menuBar.add(menu);



        //ad button bar after menu at the north

        JButton plus_1 = new JButton("PLUS");
        plus_1.addActionListener(this);
        plus_1.setBackground(new Color(255, 255, 255));
        plus_1.setMaximumSize(new Dimension(100,100));
        plus_1.setVisible(true);
        buttons.add(plus_1);


        JButton minus_1 = new JButton("MINUS");
        minus_1.addActionListener(this);
        minus_1.setBackground(new Color(255, 255, 255));
        minus_1.setMaximumSize(new Dimension(100,100));
        minus_1.setVisible(true);
        buttons.add(minus_1);


        JLabel  label_1 = new JLabel("APPLE",SwingConstants.CENTER);
        label_1.setMaximumSize(new Dimension(150,0));
        label_1.setPreferredSize(new Dimension(0,50));
        label_1.setBackground(new Color(255,255,255));
        label_1.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        label_1.setVisible(true);




        JButton plus_2 = new JButton("PLUS");
        plus_2.addActionListener(this);
        plus_2.setBackground(new Color(255, 255, 255));
        plus_2.setMaximumSize(new Dimension(100,100));
        plus_2.setVisible(true);



        JButton minus_2 = new JButton("MINUS");
        minus_2.addActionListener(this);
        minus_2.setBackground(new Color(255, 255, 255));
        minus_2.setMaximumSize(new Dimension(100,100));
        minus_2.setVisible(true);


        JLabel  label_2 = new JLabel("BP",SwingConstants.CENTER);
        label_2.setMaximumSize(new Dimension(150,0));
        label_2.setPreferredSize(new Dimension(0,50));
        label_2.setBackground(new Color(255,255,255));
        label_2.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        label_2.setVisible(true);



        JButton plus_3 = new JButton("PLUS");
        plus_3.addActionListener(this);
        plus_3.setBackground(new Color(255, 255, 255));
        plus_3.setMaximumSize(new Dimension(100,100));
        plus_3.setVisible(true);


        JButton minus_3 = new JButton("MINUS");
        minus_3.addActionListener(this);
        minus_3.setBackground(new Color(255, 255, 255));
        minus_3.setMaximumSize(new Dimension(100,100));
        minus_3.setVisible(true);

        JLabel  label_3 = new JLabel("CISCO",SwingConstants.CENTER);
        label_3.setMaximumSize(new Dimension(150,0));
        label_3.setPreferredSize(new Dimension(0,50));
        label_3.setBackground(new Color(255,255,255));
        label_3.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        label_3.setVisible(true);



        JButton plus_4 = new JButton("PLUS");
        plus_4.addActionListener(this);
        plus_4.setBackground(new Color(255, 255, 255));
        plus_4.setMaximumSize(new Dimension(100,100));
        plus_4.setVisible(true);


        JButton minus_4 = new JButton("MINUS");
        minus_4.addActionListener(this);
        minus_4.setBackground(new Color(255, 255, 255));
        minus_4.setMaximumSize(new Dimension(100,100));
        minus_4.setVisible(true);

        JLabel  label_4 = new JLabel("DELL",SwingConstants.CENTER);
        label_4.setMaximumSize(new Dimension(150,0));
        label_4.setPreferredSize(new Dimension(0,50));
        label_4.setBackground(new Color(255,255,255));
        label_4.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        label_4.setVisible(true);



        JButton plus_5 = new JButton("PLUS");
        plus_5.addActionListener(this);
        plus_5.setBackground(new Color(255, 255, 255));
        plus_5.setMaximumSize(new Dimension(100,100));
        plus_5.setVisible(true);


        JButton minus_5 = new JButton("MINUS");
        minus_5.addActionListener(this);
        minus_5.setBackground(new Color(255, 255, 255));
        minus_5.setMaximumSize(new Dimension(100,100));
        minus_5.setVisible(true);

        JLabel  label_5 = new JLabel("ERICSSON",SwingConstants.CENTER);
        label_5.setMaximumSize(new Dimension(150,0));
        label_5.setPreferredSize(new Dimension(0,50));
        label_5.setBackground(new Color(255,255,255));
        label_5.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        label_5.setVisible(true);



        JPanel buttonPane1 = new JPanel();

        JLabel label_11=new JLabel("APPLE",SwingConstants.CENTER);
        label_11.setMaximumSize(new Dimension(150,0));
        label_11.setPreferredSize(new Dimension(0,50));
        label_11.setVisible(true);

        buttonPane1.setLayout(new BoxLayout(buttonPane1, BoxLayout.Y_AXIS));
        buttonPane1.add(Box.createVerticalGlue());
        buttonPane1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPane1.add(label_11);
        buttonPane1.add(plus_1);
        buttonPane1.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane1.add(label_1);
        buttonPane1.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane1.add(minus_1);

        JPanel buttonPane2 = new JPanel();

        JLabel label_22=new JLabel("BP",SwingConstants.CENTER);
        label_22.setMaximumSize(new Dimension(150,0));
        label_22.setPreferredSize(new Dimension(0,50));
        label_22.setVisible(true);

        buttonPane2.setLayout(new BoxLayout(buttonPane2, BoxLayout.Y_AXIS));
        buttonPane2.add(Box.createVerticalGlue());
        buttonPane2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPane2.add(label_22);
        buttonPane2.add(plus_2);
        buttonPane2.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane2.add(label_2);
        buttonPane2.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane2.add(minus_2);


        JPanel buttonPane3 = new JPanel();

        JLabel label_33=new JLabel("CISCO",SwingConstants.CENTER);
        label_33.setMaximumSize(new Dimension(150,0));
        label_33.setPreferredSize(new Dimension(0,50));
        label_33.setVisible(true);

        buttonPane3.setLayout(new BoxLayout(buttonPane3, BoxLayout.Y_AXIS));
        buttonPane3.add(Box.createVerticalGlue());
        buttonPane3.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPane3.add(label_33);
        buttonPane3.add(plus_3);
        buttonPane3.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane3.add(label_3);
        buttonPane3.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane3.add(minus_3);




        JPanel buttonPane4 = new JPanel();

        JLabel label_44=new JLabel("DELL",SwingConstants.CENTER);
        label_44.setMaximumSize(new Dimension(150,0));
        label_44.setPreferredSize(new Dimension(0,50));
        label_44.setVisible(true);

        buttonPane4.setLayout(new BoxLayout(buttonPane4, BoxLayout.Y_AXIS));
        buttonPane4.add(Box.createVerticalGlue());
        buttonPane4.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPane4.add(label_44);
        buttonPane4.add(plus_4);
        buttonPane4.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane4.add(label_4);
        buttonPane4.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane4.add(minus_4);


        JPanel buttonPane5 = new JPanel();

        JLabel label_55=new JLabel("ERICSSON",SwingConstants.CENTER);
        label_55.setMaximumSize(new Dimension(150,0));
        label_55.setPreferredSize(new Dimension(0,50));
        label_55.setVisible(true);

        buttonPane5.setLayout(new BoxLayout(buttonPane5, BoxLayout.Y_AXIS));
        buttonPane5.add(Box.createVerticalGlue());
        buttonPane5.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPane5.add(label_55);
        buttonPane5.add(plus_5);
        buttonPane5.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane5.add(label_5);
        buttonPane5.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane5.add(minus_5);

        JPanel buttonPane = new JPanel();

        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,180 ));
        buttonPane.add(buttonPane1);
        buttonPane.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane.add(buttonPane2);
        buttonPane.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane.add(buttonPane3);
        buttonPane.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane.add(buttonPane4);
        buttonPane.add(Box.createRigidArea(new Dimension(10,10)));
        buttonPane.add(buttonPane5);

         //Create a yellow label to put in the content pane.
        JLabel mainContent = new JLabel();
        mainContent.setOpaque(true);
        mainContent.setBackground(new Color(248, 213, 131));
        mainContent.setPreferredSize(new Dimension(gW, gH));


/*



        JLabel north = new JLabel();
        north.setOpaque(true);
        north.setBackground(new Color(0, 100, 127));
        north.setPreferredSize(new Dimension(gW, t));

        JLabel east = new JLabel();
        east.setOpaque(true);
        east.setBackground(new Color(255, 0, 0));
        east.setPreferredSize(new Dimension(z, gH));

        JLabel west = new JLabel();
        west.setOpaque(true);
        west.setBackground(new Color(255, 255, 0));
        west.setPreferredSize(new Dimension(z, gH));

        JLabel south = new JLabel();
        south.setOpaque(true);
        south.setBackground(new Color(154, 165, 127));
        south.setPreferredSize(new Dimension(gW, t));

*/

        //Set the menu bar and add the label to the content pane.
             gamePanel.setJMenuBar(menuBar);

             gamePanel.getContentPane().add(buttonPane, BorderLayout.NORTH);
             gamePanel.getContentPane().add(mainContent, BorderLayout.CENTER);



           //  gamePanel.getContentPane().add(south, BorderLayout.SOUTH);
//             gamePanel.getContentPane().add(north, BorderLayout.NORTH);
         //    gamePanel.getContentPane().add(east, BorderLayout.EAST);
          //   gamePanel.getContentPane().add(west, BorderLayout.WEST);







        //Display the window.
        gamePanel.pack();
        gamePanel.setVisible(true);

    }


    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == buttons.get(0)){
        System.out.println("The plus_1 Works!");
        }

        else if(e.getSource() == buttons.get(1)){
        System.out.println("The minus_1 Works!");
        }
    }




}
