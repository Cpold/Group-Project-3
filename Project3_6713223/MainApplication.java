/*
 * @author Rachapon - 6713247
 * Ratchasin - 6713248
 * Sayklang - 6713250
 * Chayapol - 6713223
 * Zabit - 6713116
 */
package Project3_6713223;

import javax.swing.*;

public class MainApplication extends JFrame{
    
    public static JLabel backgroundLabel;
    private SceneManager sceneManager;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApplication());
    }

    public MainApplication(){
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(MyConstants.WIDTH, MyConstants.HEIGHT);
        setTitle("Pang Ping - Main Menu");
        setResizable(false);
        setLocationRelativeTo(null);
        
        backgroundLabel = new JLabel();
        MyImageIcon backgroundIcon = ImageLoader.loadImageIcon(MyConstants.FILE_START_BG);
        if (backgroundIcon != null) {
            backgroundLabel.setIcon(backgroundIcon.resize(MyConstants.WIDTH, MyConstants.HEIGHT));
        }
        setContentPane(backgroundLabel);
        
        backgroundLabel.setLayout(null); 
        
        sceneManager = new SceneManager(this);
        
        MainMenuScene mainMenu = sceneManager.getMainMenuScene();
        mainMenu.setBounds(0, 0, MyConstants.WIDTH, MyConstants.HEIGHT);
        backgroundLabel.add(mainMenu);

        setVisible(true);
    }
}    