/*
 * @author Rachapon - 6713247
 *         Ratchasin - 6713247
 *         Sayklang - 6713250
 *         Chayapol - 6713223
 *         Zabit - 6713116
 */

package Project3_6713223;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainApplication extends JFrame{
    
    private JLabel             ContentPane;
    private JButton            StartButton;
    private MainApplication    CurrentFrame;
    
    public static void main(String[] args) {
        new MainApplication();
    }

    public MainApplication(){
        CurrentFrame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setTitle("Restaurant Game");
        setVisible(true);
        
        setContentPane(ContentPane = new JLabel());
        ImageIcon background = new ImageIcon(MyConstants.FILE_START_BG);
        ContentPane.setIcon(background);
        
        StartButton = new JButton("Start");
        StartButton.setSize(256,128);
        setLayout( new GridBagLayout() );
        add(StartButton, new GridBagConstraints());
        validate();

                /*setTitle("Bakery Toaster Game");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        
        gameBoard = new GameBoard();
        add(gameBoard);
        
        setSize(MyConstants.WIDTH, MyConstants.HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);*/
    }
}
